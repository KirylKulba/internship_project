package pl.internship.antologic.project.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.internship.antologic.common.error.ExistsException;
import pl.internship.antologic.common.error.NoRightsException;
import pl.internship.antologic.common.error.NotFoundException;
import pl.internship.antologic.common.utils.DateUtils;
import pl.internship.antologic.common.utils.ProjectUtils;
import pl.internship.antologic.project.dto.ProjectDto;
import pl.internship.antologic.project.entity.Project;
import pl.internship.antologic.project.filter.FilterCriteria;
import pl.internship.antologic.project.filter.ProjectSpecification;
import pl.internship.antologic.project.form.CreateProjectForm;
import pl.internship.antologic.project.form.UpdateProjectForm;
import pl.internship.antologic.project.form.UpdateProjectOperation;
import pl.internship.antologic.project.form.UsersForm;
import pl.internship.antologic.project.mapper.ProjectMapper;
import pl.internship.antologic.project.repository.ProjectRepository;
import pl.internship.antologic.user.entity.User;
import pl.internship.antologic.user.entity.UserRole;
import pl.internship.antologic.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public List<ProjectDto> findAll(final UUID currentUserUuid) {
        checkIfUserHasPermissions(currentUserUuid, UserRole.projectRequiredRoles);

        return projectRepository.findAll().stream()
                .map(ProjectMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ProjectDto> findAllByFilters(final UUID currentUserUuid, final FilterCriteria filter) {
        checkIfUserHasPermissions(currentUserUuid, UserRole.projectRequiredRoles);

        final ProjectSpecification specification = new ProjectSpecification(filter);

        final List<Project> projects = projectRepository.findAll(specification);

        return projects
                .stream()
                .map(ProjectMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectDto findByUuid(final UUID currentUserUuid, final UUID projectUuid) {
        checkIfUserHasPermissions(currentUserUuid, UserRole.projectRequiredRoles);

        final Project projectEntity = findProjectByUuid(projectUuid);

        return ProjectMapper.toDto(projectEntity);
    }

    @Override
    @Transactional
    public ProjectDto save(final UUID currentUserUuid, final CreateProjectForm createProjectForm) {
        checkIfUserHasPermissions(currentUserUuid, UserRole.projectRequiredRoles);

        if(DateUtils.validateIfEndIsAfterBegin(createProjectForm.getBeginDate(),createProjectForm.getEndDate())){
            throw new IllegalArgumentException("End date must be after begin date");
        }

        if(projectRepository.existsByName(createProjectForm.getName())){
            throw new ExistsException("Name is already taken");
        }

        final Project projectEntity = ProjectMapper.toEntity(createProjectForm);

        handleProjectUsers(
                createProjectForm.getUsers(),
                projectEntity,
                UpdateProjectOperation.ADD_USERS);

        final Project newProjectEntity = projectRepository.save(projectEntity);

        return ProjectMapper.toDto(newProjectEntity);
    }

    @Override
    @Transactional
    public ProjectDto update(final UUID currentUserUuid, final UUID uuidForUpdating, final UpdateProjectForm updateProjectForm) {
        checkIfUserHasPermissions(currentUserUuid, UserRole.projectRequiredRoles);

        if(DateUtils.validateIfEndIsAfterBegin(updateProjectForm.getBeginDate(),updateProjectForm.getEndDate())){
            throw new IllegalArgumentException("End date must be after begin date");
        }

        final Project projectEntity = findProjectByUuid(uuidForUpdating);

        if(projectRepository.existsByNameAndUuidNot(updateProjectForm.getName(),projectEntity.getUuid())){
            throw new ExistsException("Name is already taken");
        }

        final Project projectFromForm = ProjectMapper.toEntity(updateProjectForm, uuidForUpdating);

        ProjectUtils.update(projectEntity, projectFromForm);

        final Project updatedProject = projectRepository.save(projectEntity);

        return ProjectMapper.toDto(updatedProject);
    }

    @Override
    @Transactional
    public void deleteByUuid(final UUID currentUserUuid, final UUID projectUuid) {
        checkIfUserHasPermissions(currentUserUuid, UserRole.projectRequiredRoles);

        if(projectRepository.existsByUuid(projectUuid)){
            projectRepository.deleteByUuid(projectUuid);
        }
    }

    @Override
    @Transactional
    public void updateProjectUsers(final UUID currentUserUuid, final UUID uuidForUpdating, final UsersForm usersForm) {
        checkIfUserHasPermissions(currentUserUuid, UserRole.projectRequiredRoles);

        final List<UUID> users = usersForm.getUsersUUID();
        final UpdateProjectOperation operation = usersForm.getOperation();
        final Project project = findProjectByUuid(uuidForUpdating);

        handleProjectUsers(users,project,operation);

        projectRepository.save(project);
    }

    private void handleProjectUsers(final List<UUID> users,final Project project, final UpdateProjectOperation operation){
        if(users.isEmpty()){
            throw new IllegalArgumentException("You should send at least one user");
        }

        checkUsersExist(users);
        final Set<User> usersSet = getUsersByUuid(users);

        switch (operation) {
            case ADD_USERS -> usersSet.forEach(project::addUser);
            case REMOVE_USERS -> usersSet.forEach(project::removeUser);
        }
    }

    private void checkUsersExist(final List<UUID> users){
        if(users.stream().noneMatch(userRepository::existsByUuid)){
            throw new NotFoundException("User not found");
        }
    }

    private Set<User> getUsersByUuid(final List<UUID> users){
        return users.stream()
                .map(this::findUserByUuid)
                .collect(Collectors.toSet());
    }

    private Project findProjectByUuid(final UUID uuid){
        return projectRepository
                .findProjectByUuid(uuid)
                .orElseThrow(()-> new NotFoundException("Project not found"));
    }

    private User findUserByUuid(final UUID uuid){
        return userRepository
                .findUserByUuid(uuid)
                .orElseThrow(()-> new NotFoundException("User not found"));
    }

    private void checkIfUserHasPermissions(final UUID userUuid, final Set<UserRole> requiredRoles){
        final User user = findUserByUuid(userUuid);
        final UserRole userRole =  user.getUserRole();

        if(!requiredRoles.contains(userRole)) {throw new NoRightsException("User has no permissions");}
    }

}

package pl.internship.antologic.project.service;

import pl.internship.antologic.project.dto.ProjectDto;
import pl.internship.antologic.project.filter.FilterCriteria;
import pl.internship.antologic.project.form.CreateProjectForm;
import pl.internship.antologic.project.form.UpdateProjectForm;
import pl.internship.antologic.project.form.UsersForm;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    List<ProjectDto> findAll(final UUID currentUserUuid);
    List<ProjectDto> findAllByFilters(final UUID currentUserUuid, final FilterCriteria filter);
    ProjectDto findByUuid(final UUID currentUserUuid, final UUID projectUuid);
    ProjectDto save(final UUID currentUserUuid, final CreateProjectForm userForm);
    ProjectDto update(final UUID currentUserUuid, final UUID uuidForUpdating, final UpdateProjectForm userForm);
    void deleteByUuid(final UUID currentUserUuid, final UUID projectUuid);
    void updateProjectUsers(final UUID currentUserUuid, final UUID uuidForUpdating, final UsersForm usersForm);
}

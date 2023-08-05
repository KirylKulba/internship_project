package pl.internship.antologic.project.mapper;

import pl.internship.antologic.project.dto.ProjectDto;
import pl.internship.antologic.project.entity.Project;
import pl.internship.antologic.project.form.CreateProjectForm;
import pl.internship.antologic.project.form.UpdateProjectForm;
import pl.internship.antologic.user.mapper.UserMapper;

import java.util.UUID;

public class ProjectMapper {
    public static ProjectDto toDto(final Project project){
        return ProjectDto.builder()
                .uuid(project.getUuid())
                .name(project.getName())
                .description(project.getDescription())
                .beginDate(project.getBeginDate())
                .endDate(project.getEndDate())
                .budget(project.getBudget())
                .budgetUse(project.getBudgetUse())
                .users(UserMapper.toDto(project.getUsers()))
                .build();
    }

    public static Project toEntity(final CreateProjectForm createProjectForm){
        return Project.builder()
                .name(createProjectForm.getName())
                .description(createProjectForm.getDescription())
                .beginDate(createProjectForm.getBeginDate())
                .endDate(createProjectForm.getEndDate())
                .budget(createProjectForm.getBudget())
                .build();
    }

    public static Project toEntity(final UpdateProjectForm updateProjectForm, final UUID uuid){
        return Project.builder()
                .uuid(uuid)
                .name(updateProjectForm.getName())
                .description(updateProjectForm.getDescription())
                .beginDate(updateProjectForm.getBeginDate())
                .endDate(updateProjectForm.getEndDate())
                .budget(updateProjectForm.getBudget())
                .build();
    }

}

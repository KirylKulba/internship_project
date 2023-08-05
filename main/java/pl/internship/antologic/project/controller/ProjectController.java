package pl.internship.antologic.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.internship.antologic.project.dto.ProjectDto;
import pl.internship.antologic.project.filter.FilterCriteria;
import pl.internship.antologic.project.form.CreateProjectForm;
import pl.internship.antologic.project.form.UpdateProjectForm;
import pl.internship.antologic.project.form.UsersForm;
import pl.internship.antologic.project.service.ProjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public List<ProjectDto> getProjects(@RequestParam final UUID currentUserUuid){
        return projectService.findAll(currentUserUuid);
    }

    @GetMapping("/{uuid}")
    public ProjectDto getProject(@RequestParam final UUID currentUserUuid, @PathVariable final UUID uuid){
        return projectService.findByUuid(currentUserUuid, uuid);
    }

    @GetMapping("/filtered")
    public List<ProjectDto> getProjects(@RequestParam final UUID currentUserUuid, @RequestBody final FilterCriteria filter){
        return projectService.findAllByFilters(currentUserUuid, filter);
    }

    @PostMapping
    public ProjectDto createProject(@RequestParam final UUID currentUserUuid, @Valid @RequestBody final CreateProjectForm projectForm){
        return projectService.save(currentUserUuid, projectForm);
    }

    @PutMapping("/{uuidForUpdating}")
    public ProjectDto updateProject(@RequestParam final UUID currentUserUuid, @PathVariable final UUID uuidForUpdating, @Valid @RequestBody final UpdateProjectForm projectForm){
        return projectService.update(currentUserUuid, uuidForUpdating, projectForm);
    }

    @PutMapping("/{uuidForUpdating}/users")
    public void updateProjectUsers(@RequestParam final UUID currentUserUuid, @PathVariable final UUID uuidForUpdating,
                                    @RequestBody final UsersForm usersForm){
        projectService.updateProjectUsers(currentUserUuid, uuidForUpdating, usersForm);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void deleteProject(@RequestParam final UUID currentUserUuid,@PathVariable final UUID uuid){
        projectService.deleteByUuid(currentUserUuid, uuid);
    }
}

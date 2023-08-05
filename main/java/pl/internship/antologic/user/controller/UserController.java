package pl.internship.antologic.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.internship.antologic.user.dto.CreateUserForm;
import pl.internship.antologic.user.dto.UpdateUserForm;
import pl.internship.antologic.user.dto.UserDto;
import pl.internship.antologic.user.filter.FilterCriteria;
import pl.internship.antologic.user.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam final UUID currentUserUuid){
        return userService.findAll(currentUserUuid);
    }

    @GetMapping("/filtered")
    public List<UserDto> getFilteredUsers(@RequestParam final UUID currentUserUuid, @RequestBody final FilterCriteria searchForm){
        return userService.findAllByFilters(currentUserUuid, searchForm);
    }

    @GetMapping("/{uuid}")
    public UserDto getUser(@RequestParam final UUID currentUserUuid, @PathVariable final UUID uuid){
        return userService.findByUuid(currentUserUuid, uuid);
    }

    @PostMapping
    public UserDto createUser(@RequestParam final UUID currentUserUuid, @Valid @RequestBody final CreateUserForm userForm){
        return userService.save(currentUserUuid, userForm);
    }

    @PutMapping("/{uuidForUpdating}")
    public UserDto updateUser(@RequestParam final UUID currentUserUuid, @PathVariable final UUID uuidForUpdating, @Valid @RequestBody final UpdateUserForm userForm){
        return userService.update(currentUserUuid, uuidForUpdating, userForm);
    }

    @DeleteMapping("/{uuid}")
    public void deleteUser(@RequestParam final UUID currentUserUuid,@PathVariable final UUID uuid){
        userService.deleteByUuid(currentUserUuid, uuid);
    }

}

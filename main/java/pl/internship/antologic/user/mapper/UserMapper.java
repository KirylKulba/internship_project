package pl.internship.antologic.user.mapper;

import pl.internship.antologic.user.dto.CreateUserForm;
import pl.internship.antologic.user.dto.UpdateUserForm;
import pl.internship.antologic.user.dto.UserDto;
import pl.internship.antologic.user.entity.User;
import pl.internship.antologic.user.entity.UserRole;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toDto(final User user){
        return UserDto.builder()
                .uuid(user.getUuid())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .login(user.getLogin())
                .userRole(user.getUserRole())
                .cost(user.getCost())
                .password(user.getPassword())
                .build();
    }

    public static Set<UserDto> toDto(final Set<User> users){
        return users.stream().map(UserMapper::toDto).collect(Collectors.toSet());
    }

    public static User toEntity(final CreateUserForm createUserForm){
        return User.builder()
                .firstName(createUserForm.getFirstName())
                .lastName(createUserForm.getLastName())
                .email(createUserForm.getEmail())
                .login(createUserForm.getLogin())
                .userRole(UserRole.valueOf(createUserForm.getUserRole()))
                .cost(createUserForm.getCost())
                .password(createUserForm.getPassword())
                .build();
    }

    public static User toEntity(final UpdateUserForm updateUserForm, UUID uuid){
        return User.builder()
                .uuid(uuid)
                .firstName(updateUserForm.getFirstName())
                .lastName(updateUserForm.getLastName())
                .email(updateUserForm.getEmail())
                .login(updateUserForm.getLogin())
                .userRole(UserRole.valueOf(updateUserForm.getUserRole()))
                .cost(updateUserForm.getCost())
                .password(updateUserForm.getPassword())
                .build();
    }

    public static UpdateUserForm toUpdateUserForm(final User user){
        return UpdateUserForm.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .login(user.getLogin())
                .userRole(user.getUserRole().toString())
                .cost(user.getCost())
                .password(user.getPassword())
                .build();
    }

    public static CreateUserForm toCreateUserForm(final User user){
        return CreateUserForm.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .login(user.getLogin())
                .userRole(user.getUserRole().toString())
                .cost(user.getCost())
                .password(user.getPassword())
                .build();
    }

    public static UserDto toDto(final UpdateUserForm user){
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .login(user.getLogin())
                .userRole(UserRole.valueOf(user.getUserRole()))
                .cost(user.getCost())
                .password(user.getPassword())
                .build();
    }

}

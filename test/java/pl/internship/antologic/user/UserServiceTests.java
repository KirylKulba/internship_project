package pl.internship.antologic.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.internship.antologic.common.error.ExistsException;
import pl.internship.antologic.common.error.NoRightsException;
import pl.internship.antologic.common.error.NotFoundException;
import pl.internship.antologic.user.dto.CreateUserForm;
import pl.internship.antologic.user.dto.UpdateUserForm;
import pl.internship.antologic.user.dto.UserDto;
import pl.internship.antologic.user.entity.User;
import pl.internship.antologic.user.entity.UserRole;
import pl.internship.antologic.user.filter.FilterCriteria;
import pl.internship.antologic.user.filter.UserSpecification;
import pl.internship.antologic.user.mapper.UserMapper;
import pl.internship.antologic.user.repository.UserRepository;
import pl.internship.antologic.user.service.UserServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests<AbstractEntity> {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User RECORD_1;
    private User RECORD_2;
    private User RECORD_3;

    @BeforeEach
    void init() {
         RECORD_1 = new User(0L, UUID.randomUUID(),"admin", "Kiryl",
                "Lol","user","bulbik@mail.com", new BigDecimal(20.50), UserRole.ADMIN, new HashSet<>(), new ArrayList<>());
         RECORD_2 = new User( 0L,UUID.randomUUID(),"user21", "Krzysztof",
                "Bulbik","user","jack@gmail.com", new BigDecimal(10.50), UserRole.EMPLOYEE, new HashSet<>(), new ArrayList<>());
         RECORD_3 = new User(0L,UUID.randomUUID(),"user2", "Kiryl",
                "Kulbachynski","user","bulbik@mail.com", new BigDecimal(5.50), UserRole.EMPLOYEE, new HashSet<>(), new ArrayList<>());
    }

    @Test
    public void shouldThrowNoRightsException() {
        //given
        final UUID employeeUuid = RECORD_2.getUuid();
        when(userRepository.findUserByUuid(any(UUID.class))).thenReturn(Optional.of(RECORD_2));
        //when //then
        Assertions.assertThatThrownBy(()->{
            userService.findAll(employeeUuid);
        }).isInstanceOf(NoRightsException.class);
    }

    @Test
    public void shouldFindAllUsers() {
        //given
        final List<User> users = List.of(RECORD_1, RECORD_2, RECORD_3);
        final UUID adminUuid = RECORD_1.getUuid();

        when(userRepository.findUserByUuid(any(UUID.class))).thenReturn(Optional.of(RECORD_1));
        when(userRepository.findAll()).thenReturn(users);
        //when
        final List<UserDto> usersDto = userService.findAll(adminUuid);
        //then
        Assertions.assertThatList(usersDto).containsExactlyInAnyOrder(
                UserMapper.toDto(RECORD_1), UserMapper.toDto(RECORD_2), UserMapper.toDto(RECORD_3) );
    }

    @Test
    public void shouldFindAllEmployees() {
        //given
        final List<User> users = List.of(RECORD_1, RECORD_2, RECORD_3);
        final UUID adminUuid = RECORD_1.getUuid();

        final List<User> employees = users.stream().filter(user->user.getUserRole().equals(UserRole.EMPLOYEE)).collect(Collectors.toList());
        final FilterCriteria employeeFilter = new FilterCriteria();
        employeeFilter.setUserRole(UserRole.EMPLOYEE.toString());

        final UserSpecification userSpec = new UserSpecification(employeeFilter);

        when(userRepository.findUserByUuid(any(UUID.class))).thenReturn(Optional.of(RECORD_1));
        when(userRepository.findAll(any(UserSpecification.class))).thenReturn(employees);
        //when
        final List<UserDto> usersDto = userService.findAllByFilters(adminUuid, employeeFilter);
        //then
        Assertions.assertThat(usersDto.size()).isEqualTo(2);
        Assertions.assertThat(usersDto)
                .extracting(UserDto::getUserRole)
                .allSatisfy(userRole-> Assertions.assertThat(userRole).isEqualTo(UserRole.EMPLOYEE));
    }

    @Test
    public void shouldThrowUserNotFound() {
        //given
        final UUID adminUuid = RECORD_1.getUuid();
        when(userRepository.findUserByUuid(any(UUID.class))).thenReturn(Optional.empty());
        //when //then
        Assertions.assertThatThrownBy(()->{
            userService.findByUuid(adminUuid, any());
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void shouldFindUserByUuid() {
        //given
        final UUID adminUuid = RECORD_1.getUuid();
        final UUID employeeUuid = RECORD_2.getUuid();

        when(userRepository.findUserByUuid(any(UUID.class))).thenReturn(Optional.of(RECORD_1));
        when(userRepository.findUserByUuid(employeeUuid)).thenReturn(Optional.of(RECORD_2));

        final UserDto expected = UserMapper.toDto(RECORD_2);
        //when
        final UserDto actual = userService.findByUuid(adminUuid, employeeUuid);
        // then
        Assertions.assertThatObject(actual).isEqualTo(expected);
    }

    @Test
    public void shouldThrowLoginIsTaken() {
        //given
        final UUID adminUuid = RECORD_1.getUuid();

        final CreateUserForm createUserForm = UserMapper.toCreateUserForm(RECORD_2);

        when(userRepository.findUserByUuid(any(UUID.class))).thenReturn(Optional.of(RECORD_1));
        when(userRepository.existsByLogin(createUserForm.getLogin())).thenReturn(true);

        //when // then
        Assertions.assertThatThrownBy(()->{
            userService.save(adminUuid, createUserForm);
        }).isInstanceOf(ExistsException.class);
    }

    @Test
    public void shouldSaveUser() {
        //given
        final UUID adminUuid = RECORD_1.getUuid();

        final CreateUserForm createUserForm = UserMapper.toCreateUserForm(RECORD_2);

        createUserForm.setLogin("New Login");

        final User newUser = UserMapper.toEntity(createUserForm);
        newUser.setUuid( UUID.randomUUID() );

        when(userRepository.findUserByUuid(any(UUID.class))).thenReturn(Optional.of(RECORD_1));
        when(userRepository.existsByLogin(any(String.class))).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        //when
        final UserDto newUserDto = userService.save(adminUuid, createUserForm);
        // then
        Assertions.assertThat(newUserDto).isNotNull();
    }

    @Test
    public void shouldThrowLoginIsTakenIfNotTheSameUser() {
        //given
        final UUID adminUuid = RECORD_1.getUuid();

        final UpdateUserForm updateUserForm = UserMapper.toUpdateUserForm(RECORD_2);

        updateUserForm.setLogin(RECORD_3.getLogin());

        when(userRepository.findUserByUuid(any(UUID.class))).thenReturn(Optional.of(RECORD_1));
        when(userRepository.findUserByUuid(RECORD_2.getUuid())).thenReturn(Optional.of(RECORD_2));
        when(userRepository.existsByLoginAndUuidNot(any(String.class), any(UUID.class))).thenReturn(true);

        //when // then
        Assertions.assertThatThrownBy(()->{
            userService.update(adminUuid, RECORD_2.getUuid() , updateUserForm);
        }).isInstanceOf(ExistsException.class);
    }

    @Test
    public void shouldUpdateUser() {
        //given
        final UUID adminUuid = RECORD_1.getUuid();
        final UpdateUserForm updateUserForm = UserMapper.toUpdateUserForm(RECORD_2);

        updateUserForm.setLogin("Unique login");
        updateUserForm.setCost(new BigDecimal("5.00"));

        when(userRepository.findUserByUuid(any(UUID.class))).thenReturn(Optional.of(RECORD_1));
        when(userRepository.findUserByUuid(RECORD_2.getUuid())).thenReturn(Optional.of(RECORD_2));
        when(userRepository.existsByLoginAndUuidNot(any(String.class),any(UUID.class))).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(UserMapper.toEntity(updateUserForm, RECORD_2.getUuid()));

        //when
        final UserDto userDto = userService.update(adminUuid, RECORD_2.getUuid(), updateUserForm);
        //then
        Assertions.assertThat(userDto)
                .usingRecursiveComparison()
                .ignoringOverriddenEqualsForTypes(User.class, UserDto.class)
                .isNotEqualTo(UserMapper.toUpdateUserForm(RECORD_2));

    }

    @Test
    public void shouldUpdateUserIfLoginIsTheSame() {
        //given
        final UUID adminUuid = RECORD_1.getUuid();
        final UpdateUserForm updateUserForm = UserMapper.toUpdateUserForm(RECORD_2);

        updateUserForm.setCost(new BigDecimal("5.00"));

        when(userRepository.findUserByUuid(any(UUID.class))).thenReturn(Optional.of(RECORD_1));
        when(userRepository.findUserByUuid(RECORD_2.getUuid())).thenReturn(Optional.of(RECORD_2));
        when(userRepository.existsByLoginAndUuidNot(any(String.class),any(UUID.class))).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(RECORD_2);

        //when
        final UserDto userDto = userService.update(adminUuid, RECORD_2.getUuid(), updateUserForm);
        //then
        Assertions.assertThat(userDto)
                .usingRecursiveComparison()
                .ignoringOverriddenEqualsForTypes(User.class, UserDto.class)
                .isNotEqualTo(UserMapper.toUpdateUserForm(RECORD_2));

    }

    @Test
    public void shouldDeleteUser() {
        //given
        final UUID adminUuid = RECORD_1.getUuid();
        final UUID userToBeDeletedUuid = RECORD_2.getUuid();

        when(userRepository.findUserByUuid(any(UUID.class))).thenReturn(Optional.of(RECORD_1));
        when(userRepository.findUserByUuid(userToBeDeletedUuid)).thenReturn(Optional.empty());

        //when
        userService.deleteByUuid(adminUuid, userToBeDeletedUuid);
        //then
        Assertions.assertThatThrownBy(()->{
            userService.findByUuid(adminUuid,userToBeDeletedUuid);
        }).isInstanceOf(NotFoundException.class);
        Mockito.verify(userRepository, Mockito.times(1)).deleteUserByUuid(userToBeDeletedUuid);
    }

    @Test
    public void shouldThrowNoRightsExceptionWhenDeleteUserByUuid() {
        //given
        final UUID employee2Uuid = RECORD_2.getUuid();
        final UUID employee3Uuid = RECORD_3.getUuid();

        when(userRepository.findUserByUuid(any(UUID.class))).thenReturn(Optional.of(RECORD_2));
        //when //then
        Assertions.assertThatThrownBy(()->{
            userService.deleteByUuid(employee2Uuid, employee3Uuid);
        }).isInstanceOf(NoRightsException.class);
    }

}

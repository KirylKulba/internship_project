package pl.internship.antologic.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public List<UserDto> findAll(final UUID currentUserUuid) {

        checkIfUserHasPermissions(currentUserUuid, UserRole.userRequiredRoles);

        final List<User> usersEntities = userRepository.findAll();

        return usersEntities.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public UserDto findByUuid(final UUID currentUserUuid, final UUID uuid) {

        checkIfUserHasPermissions(currentUserUuid, UserRole.userRequiredRoles);

        final User userEntity = findUserByUuid(uuid);

        return UserMapper.toDto(userEntity);
    }

    @Override
    @Transactional
    public List<UserDto> findAllByFilters(final UUID currentUserUuid, final FilterCriteria searchForm) {
        checkIfUserHasPermissions(currentUserUuid, UserRole.userRequiredRoles);

        final UserSpecification specification = new UserSpecification(searchForm);

        return userRepository.findAll(specification).stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto save(final UUID currentUserUuid, final CreateUserForm createUserForm) {

        checkIfUserHasPermissions(currentUserUuid, UserRole.userRequiredRoles);

        if (userRepository.existsByLogin(createUserForm.getLogin())) {
            throw new ExistsException("Login is already taken");
        }

        final User userEntity = UserMapper.toEntity(createUserForm);
        userEntity.setUuid(UUID.randomUUID());

        final User newUserEntity = userRepository.save(userEntity);

        return UserMapper.toDto(newUserEntity);
    }

    @Override
    @Transactional
    public UserDto update(final UUID currentUserUuid, final UUID uuidForUpdating, final UpdateUserForm updateUserForm) {

        checkIfUserHasPermissions(currentUserUuid, UserRole.userRequiredRoles);

        final User userEntity = findUserByUuid(uuidForUpdating);

        if (userRepository.existsByLoginAndUuidNot(updateUserForm.getLogin(), uuidForUpdating)) {
            throw new ExistsException("Login is already taken");
        }

        final User userFromForm = UserMapper.toEntity(updateUserForm, uuidForUpdating);
        userEntity.copy(userFromForm);

        return UserMapper.toDto(userRepository.save(userEntity));
    }


    @Override
    @Transactional
    public void deleteByUuid(final UUID currentUserUuid, final UUID uuid) {
        checkIfUserHasPermissions(currentUserUuid, UserRole.userRequiredRoles);
        userRepository.deleteUserByUuid(uuid);
    }

    private void checkIfUserHasPermissions(final UUID userUuid, final Set<UserRole> requiredRoles) {
        final User user = findUserByUuid(userUuid);
        final UserRole userRole = user.getUserRole();

        if (!requiredRoles.contains(userRole)) {
            throw new NoRightsException("User has no permissions");
        }
    }

    private User findUserByUuid(final UUID uuid) {
        return userRepository.
                findUserByUuid(uuid).
                orElseThrow(() -> new NotFoundException("User not found"));
    }

}

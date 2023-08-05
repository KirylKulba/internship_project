package pl.internship.antologic.user.service;


import pl.internship.antologic.user.dto.CreateUserForm;
import pl.internship.antologic.user.dto.UpdateUserForm;
import pl.internship.antologic.user.dto.UserDto;
import pl.internship.antologic.user.filter.FilterCriteria;

import java.util.List;
import java.util.UUID;

public interface UserService {

    public List<UserDto> findAll(final UUID currentUserUuid);
    public UserDto findByUuid(final UUID currentUserUuid, final UUID uuid);
    public List<UserDto> findAllByFilters(final UUID currentUserUuid, final FilterCriteria searchForm);
    public UserDto save(final UUID currentUserUuid, final CreateUserForm userForm);
    public UserDto update(final UUID currentUserUuid, final UUID uuidForUpdating, final UpdateUserForm userForm);
    public void deleteByUuid(final UUID currentUserUuid, final UUID uuid);
}

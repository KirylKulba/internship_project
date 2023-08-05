package pl.internship.antologic.user.entity;

import java.util.EnumSet;
import java.util.Set;

public enum UserRole {
    ADMIN,
    MANAGER,
    EMPLOYEE;

    public static Set<UserRole> userRequiredRoles = EnumSet.of(UserRole.ADMIN);
    public static Set<UserRole> projectRequiredRoles = EnumSet.of(UserRole.ADMIN, UserRole.MANAGER);
    public static Set<UserRole> recordRequiredRoles = EnumSet.of(UserRole.ADMIN, UserRole.MANAGER, UserRole.EMPLOYEE);
}

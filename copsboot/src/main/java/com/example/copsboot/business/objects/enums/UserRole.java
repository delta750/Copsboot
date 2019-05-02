package com.example.copsboot.business.objects.enums;

import lombok.Getter;

public enum UserRole {
    OFFICER("Officer"),
    CAPTAIN("Captain"),
    ADMIN("Admin");

    @Getter
    private String role;

    UserRole(final String role) {this.role = role;}

    public static UserRole fromValue(String value) {
        if (value != null) {
            for (UserRole role : values()) {
                if (role.getRole().equals(value.trim())) {
                    return role;
                }
            }
        }
        return null;
    }
}

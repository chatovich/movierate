package com.chatovich.movie.type;

/**
 * Class that represents roles which an application user may have
 */
public enum Role {
    USER ("user"),
    ADMIN ("admin");

    Role(String roleName) {
        this.roleName = roleName;
    }

    private String roleName;

    public String getRoleName() {
        return roleName;
    }
}

package com.movierate.movie.type;

/**
 * Created by Yultos_ on 27.11.2016
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

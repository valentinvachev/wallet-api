package bg.wallet.www.project.models.view;

import bg.wallet.www.project.models.Role;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserViewInfoModel {
    private Long id;
    private String username;
    private Set<String> roles;
    private String email;

    public String getEmail() {
        return email;
    }

    public UserViewInfoModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<String> getRoles() {
        return roles.stream().collect(Collectors.toUnmodifiableSet());
    }

    public UserViewInfoModel setRoles(Set<String> roles) {
        this.roles = roles;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserViewInfoModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserViewInfoModel setUsername(String username) {
        this.username = username;
        return this;
    }
}

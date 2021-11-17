package bg.wallet.www.project.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String password;
    @Column(unique = true,nullable = false)
    @Email
    private String email;
    @Column(nullable = false)
    @Size(min = 2,message = "Username should be at least 2 symbols")
    private String username;
    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Set<Role> authorities = new HashSet<>();

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public User setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
}

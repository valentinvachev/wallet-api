package bg.wallet.www.project.models;

import bg.wallet.www.project.enums.UserRole;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole authority;

    public Role() {
    }

    public Role(UserRole authority) {
        this.authority = authority;
    }

    public UserRole getAuthority() {
        return authority;
    }

    public Role setAuthority(UserRole authority) {
        this.authority = authority;
        return this;
    }
}

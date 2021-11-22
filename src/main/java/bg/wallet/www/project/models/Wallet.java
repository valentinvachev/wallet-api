package bg.wallet.www.project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
public class Wallet extends BaseEntity {

    @Column(nullable = false)
    @Size(min = 2,message = "Name should be at least 2 symbols")
    private String name;
    @Column(nullable = false)
    private BigDecimal balance;
    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public Wallet setUser(User user) {
        this.user = user;
        return this;
    }

    public String getName() {
        return name;
    }

    public Wallet setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Wallet setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
}

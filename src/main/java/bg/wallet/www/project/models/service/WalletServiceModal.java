package bg.wallet.www.project.models.service;
import java.math.BigDecimal;

public class WalletServiceModal {

    private String name;
    private BigDecimal balance;

    public String getName() {
        return name;
    }

    public WalletServiceModal setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public WalletServiceModal setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
}

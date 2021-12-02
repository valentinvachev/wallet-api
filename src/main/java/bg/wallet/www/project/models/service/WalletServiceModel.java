package bg.wallet.www.project.models.service;
import java.math.BigDecimal;

public class WalletServiceModel {

    private String name;
    private BigDecimal balance;

    public String getName() {
        return name;
    }

    public WalletServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public WalletServiceModel setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
}

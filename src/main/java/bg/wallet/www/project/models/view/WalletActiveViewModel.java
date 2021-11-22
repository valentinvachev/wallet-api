package bg.wallet.www.project.models.view;

import java.math.BigDecimal;

public class WalletActiveViewModel {
    private Long id;
    private String name;
    private BigDecimal balance;


    public String getName() {
        return name;
    }

    public WalletActiveViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public Long getId() {
        return id;
    }

    public WalletActiveViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public WalletActiveViewModel setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
}

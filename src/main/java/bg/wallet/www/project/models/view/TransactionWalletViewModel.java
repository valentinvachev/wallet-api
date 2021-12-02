package bg.wallet.www.project.models.view;

import bg.wallet.www.project.enums.TransactionType;

import java.math.BigDecimal;

public class TransactionWalletViewModel {
    private Long id;
    private String name;
    private BigDecimal amount;
    private String createdAt;
    private String type;

    public String getType() {
        return type;
    }

    public TransactionWalletViewModel setType(String type) {
        this.type = type;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public TransactionWalletViewModel setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Long getId() {
        return id;
    }

    public TransactionWalletViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TransactionWalletViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionWalletViewModel setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}

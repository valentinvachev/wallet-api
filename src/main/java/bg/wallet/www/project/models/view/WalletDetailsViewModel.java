package bg.wallet.www.project.models.view;

import java.math.BigDecimal;
import java.util.List;

public class WalletDetailsViewModel {
    private Long id;
    private String name;
    private BigDecimal balance;
    private List<TransactionWalletViewModel> transactionWalletViewModelList;

    public Long getId() {
        return id;
    }

    public WalletDetailsViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public WalletDetailsViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public WalletDetailsViewModel setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public List<TransactionWalletViewModel> getTransactionWalletViewModelList() {
        return transactionWalletViewModelList;
    }

    public WalletDetailsViewModel setTransactionWalletViewModelList(List<TransactionWalletViewModel> transactionWalletViewModelList) {
        this.transactionWalletViewModelList = transactionWalletViewModelList;
        return this;
    }
}

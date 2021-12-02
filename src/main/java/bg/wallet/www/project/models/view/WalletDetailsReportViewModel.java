package bg.wallet.www.project.models.view;

import java.math.BigDecimal;
import java.util.List;

public class WalletDetailsReportViewModel {
    BigDecimal totalAmount;
    List<TransactionWalletViewModel> transactionWalletViewModelList;

    public List<TransactionWalletViewModel> getTransactionWalletViewModelList() {
        return transactionWalletViewModelList;
    }

    public WalletDetailsReportViewModel setTransactionWalletViewModelList(List<TransactionWalletViewModel> transactionWalletViewModelList) {
        this.transactionWalletViewModelList = transactionWalletViewModelList;
        return this;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public WalletDetailsReportViewModel setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }
}

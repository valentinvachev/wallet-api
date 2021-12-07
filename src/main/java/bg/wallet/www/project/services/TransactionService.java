package bg.wallet.www.project.services;

import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.Transaction;
import bg.wallet.www.project.models.binding.TransactionBindingModel;
import bg.wallet.www.project.models.view.TransactionLastViewModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    Long save(TransactionBindingModel transactionBindingModel,String userEmail) throws InvalidInputException;
    void deleteTransactionsByCategoryId(Long categoryId);
    void deleteTransactionsByWalletId(Long walletId);
    List<TransactionLastViewModel> findLastFiveTransactionsOfUser(String userEmail);
    List<Transaction> findAllTransactionsByWallet(Long walletId);
    List<Transaction> findAllTransactionsBetweenDatesByWallet(Long walletId, LocalDateTime startDate, LocalDateTime endDate);
}

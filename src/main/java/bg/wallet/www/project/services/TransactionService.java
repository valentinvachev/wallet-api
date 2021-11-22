package bg.wallet.www.project.services;

import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.binding.TransactionBindingModel;
import bg.wallet.www.project.models.view.TransactionLastViewModel;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    Long save(TransactionBindingModel transactionBindingModel,String userEmail) throws InvalidInputException;
    List<TransactionLastViewModel> findLastFiveTransactionsOfUser(String userEmail);
}

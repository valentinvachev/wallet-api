package bg.wallet.www.project.services;

import bg.wallet.www.project.enums.TransactionType;
import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.exceptions.NotAuthorizedException;
import bg.wallet.www.project.models.Wallet;
import bg.wallet.www.project.models.binding.WalletBindingModel;
import bg.wallet.www.project.models.binding.WalletEditBindingModel;
import bg.wallet.www.project.models.view.WalletActiveViewModel;
import bg.wallet.www.project.models.view.WalletDetailsReportViewModel;
import bg.wallet.www.project.models.view.WalletDetailsViewModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface WalletService {
    Long save(WalletBindingModel walletBindingModel,String userEmail) throws DuplicateEntityException, InvalidInputException;
    Long editName(Long id,WalletEditBindingModel walletEditBindingModel, String userEmail) throws InvalidInputException, EntityNotFoundException, NotAuthorizedException;
    Wallet findById(Long id);
    List<WalletActiveViewModel> findAll(String userEmail);
    void editBalance(Long walletId, BigDecimal amount, TransactionType type);
    WalletDetailsViewModel getWalletById(Long id,String userEmail) throws EntityNotFoundException, NotAuthorizedException;
    WalletDetailsReportViewModel getWalletReportById(Long id, LocalDateTime startDate, LocalDateTime endDate, String userEmail) throws InvalidInputException, EntityNotFoundException, NotAuthorizedException;
    BigDecimal findTotal(String name);
    void deleteWallet(Long id, String userEmail) throws NotAuthorizedException, EntityNotFoundException;
}

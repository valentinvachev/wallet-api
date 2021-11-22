package bg.wallet.www.project.services;

import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.Wallet;
import bg.wallet.www.project.models.binding.WalletBindingModel;
import bg.wallet.www.project.models.view.WalletActiveViewModel;

import java.util.List;

public interface WalletService {
    Long save(WalletBindingModel walletBindingModel,String userEmail) throws DuplicateEntityException, InvalidInputException;
    Wallet findById(Long id);
    List<WalletActiveViewModel> findAll();
}

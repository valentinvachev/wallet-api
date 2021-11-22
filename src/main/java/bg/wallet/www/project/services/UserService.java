package bg.wallet.www.project.services;

import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.binding.UserRegisterBindingModel;
import bg.wallet.www.project.models.view.WalletActiveViewModel;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    void save(@Valid UserRegisterBindingModel userRegisterBindingModel) throws DuplicateEntityException;
    User findByEmail(String email);
}

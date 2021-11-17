package bg.wallet.www.project.services;

import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.binding.UserRegisterBindingModel;

import javax.validation.Valid;

public interface UserService {
    void save(@Valid UserRegisterBindingModel userRegisterBindingModel);
    User findByEmail(String email);
}

package bg.wallet.www.project.services.impl;

import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.Wallet;
import bg.wallet.www.project.models.binding.WalletBindingModel;
import bg.wallet.www.project.models.service.WalletServiceModal;
import bg.wallet.www.project.models.view.WalletActiveViewModel;
import bg.wallet.www.project.repositories.WalletRepository;
import bg.wallet.www.project.services.UserService;
import bg.wallet.www.project.services.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository, ModelMapper modelMapper, UserService userService) {
        this.walletRepository = walletRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public Long save(WalletBindingModel walletBindingModel,String userEmail) throws DuplicateEntityException, InvalidInputException {
        Wallet walletDb = this.walletRepository.findByName(walletBindingModel.getName());
        User user = this.userService.findByEmail(userEmail);

        if (user == null) {
            throw new InvalidInputException("User does not exist");
        }

        if (walletDb == null) {
            WalletServiceModal walletServiceModal = this.modelMapper.map(walletBindingModel,WalletServiceModal.class);
            Wallet wallet = this.modelMapper.map(walletServiceModal,Wallet.class);
            wallet.setUser(user);

           return this.walletRepository.save(wallet).getId();
        } else {
            throw new DuplicateEntityException("Wallet with this name already exists");
        }
    }

    @Override
    public List<WalletActiveViewModel> findAll() {
        return this.walletRepository.findAll().stream()
                .map(w->this.modelMapper.map(w,WalletActiveViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Wallet findById(Long id) {
        return this.walletRepository.findById(id).orElse(null);
    }
}

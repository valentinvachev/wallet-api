package bg.wallet.www.project.services.impl;

import bg.wallet.www.project.enums.TransactionType;
import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.Wallet;
import bg.wallet.www.project.models.binding.WalletBindingModel;
import bg.wallet.www.project.models.binding.WalletEditBindingModel;
import bg.wallet.www.project.models.service.WalletServiceModel;
import bg.wallet.www.project.models.view.TransactionWalletViewModel;
import bg.wallet.www.project.models.view.WalletActiveViewModel;
import bg.wallet.www.project.models.view.WalletDetailsReportViewModel;
import bg.wallet.www.project.models.view.WalletDetailsViewModel;
import bg.wallet.www.project.repositories.WalletRepository;
import bg.wallet.www.project.services.TransactionService;
import bg.wallet.www.project.services.UserService;
import bg.wallet.www.project.services.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final TransactionService transactionService;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository, ModelMapper modelMapper, UserService userService, @Lazy TransactionService transactionService) {
        this.walletRepository = walletRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @Override
    public Long save(WalletBindingModel walletBindingModel,String userEmail) throws DuplicateEntityException, InvalidInputException {
        Wallet walletDb = this.walletRepository.findByName(walletBindingModel.getName());
        User user = this.userService.findByEmail(userEmail);

        if (user == null) {
            throw new InvalidInputException("User does not exist");
        }

        if (walletDb == null) {
            WalletServiceModel walletServiceModel = this.modelMapper.map(walletBindingModel, WalletServiceModel.class);
            Wallet wallet = this.modelMapper.map(walletServiceModel,Wallet.class);
            wallet.setUser(user);

           return this.walletRepository.save(wallet).getId();
        } else {
            throw new DuplicateEntityException("Wallet with this name already exists");
        }
    }

    @Override
    public void editBalance(Long walletId, BigDecimal amount, TransactionType type) {
        Wallet wallet = this.walletRepository.getById(walletId);

        if (wallet != null) {
            BigDecimal currentBalance = wallet.getBalance();

            if (type.equals(TransactionType.INCOME)) {
                wallet.setBalance(currentBalance.add(amount));
            } else {
                wallet.setBalance(currentBalance.subtract(amount));
            }

            this.walletRepository.save(wallet);
        }
    }

    @Override
    public Long editName(Long id, WalletEditBindingModel walletEditBindingModel, String userEmail) throws InvalidInputException, EntityNotFoundException {
        Wallet walletDb = this.walletRepository.getById(id);
        User user = this.userService.findByEmail(userEmail);

        if (user == null) {
            throw new InvalidInputException("User does not exist");
        }

        if (walletDb != null) {
            walletDb.setName(walletEditBindingModel.getName());
            this.walletRepository.save(walletDb);
            return walletDb.getId();
        } else {
            throw new EntityNotFoundException("Wallet with this name does not exists");
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
        return this.walletRepository.getById(id);
    }

    @Override
    public WalletDetailsViewModel getWalletById(Long id) throws EntityNotFoundException {

        Wallet wallet = this.walletRepository.getById(id);

        if (wallet == null) {
            throw new EntityNotFoundException("Wallet with this id does not exists");
        }

        WalletDetailsViewModel walletDetailsViewModel = this.modelMapper.map(wallet,WalletDetailsViewModel.class);
        walletDetailsViewModel.setTransactionWalletViewModelList(this.transactionService.findAllTransactionsByWallet(wallet.getId()).stream()
                .map(t->{
                    TransactionWalletViewModel walletViewModel = this.modelMapper.map(t, TransactionWalletViewModel.class);
                    walletViewModel.setType(t.getCategory().getType().name());
                    return  walletViewModel;
                }).collect(Collectors.toList()));

        return  walletDetailsViewModel;
    }


    @Override
    public WalletDetailsReportViewModel getWalletReportById(Long id, LocalDateTime startDate, LocalDateTime endDate) throws InvalidInputException, EntityNotFoundException {
        Wallet wallet = this.walletRepository.getById(id);

        if (wallet == null) {
            throw new EntityNotFoundException("Wallet with this id does not exists");
        }

        if (startDate.isAfter(endDate)) {
            throw new InvalidInputException("Start date should not be after end date");
        }

        WalletDetailsReportViewModel walletDetailsReportViewModel = this.modelMapper.map(wallet,WalletDetailsReportViewModel.class);

        walletDetailsReportViewModel.setTotalAmount(BigDecimal.valueOf(0));

        List<TransactionWalletViewModel> transactionWalletViewModelList =
                this.transactionService.findAllTransactionsBetweenDatesByWallet(id,startDate,endDate).stream().map(t->{
                    TransactionWalletViewModel walletViewModel = this.modelMapper.map(t,TransactionWalletViewModel.class);
                    walletViewModel.setType(t.getCategory().getType().name());

                    if (t.getCategory().getType().equals(TransactionType.EXPENSE)) {
                        walletDetailsReportViewModel.setTotalAmount(walletDetailsReportViewModel.getTotalAmount().subtract(t.getAmount()));
                    } else {
                        walletDetailsReportViewModel.setTotalAmount(walletDetailsReportViewModel.getTotalAmount().add(t.getAmount()));
                    }

                    return walletViewModel;
                }).collect(Collectors.toList());

        walletDetailsReportViewModel.setTransactionWalletViewModelList(transactionWalletViewModelList);

        return walletDetailsReportViewModel;
    }

    @Override
    public BigDecimal findTotal() {
        return this.walletRepository.findSumOfAllWallets();
    }
}

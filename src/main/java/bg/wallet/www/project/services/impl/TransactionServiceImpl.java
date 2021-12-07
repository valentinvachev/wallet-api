package bg.wallet.www.project.services.impl;

import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.*;
import bg.wallet.www.project.models.binding.TransactionBindingModel;
import bg.wallet.www.project.models.service.TransactionServiceModel;
import bg.wallet.www.project.models.view.TransactionLastViewModel;
import bg.wallet.www.project.repositories.TransactionRepository;
import bg.wallet.www.project.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final WalletService walletService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, ModelMapper modelMapper, UserService userService,
                                  CategoryService categoryService, EventService eventService, WalletService walletService) {


        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService = categoryService;
        this.eventService = eventService;
        this.walletService = walletService;
    }

    @Override
    public Long save(TransactionBindingModel transactionBindingModel,String userEmail) throws InvalidInputException {

        User user = this.userService.findByEmail(userEmail);
        Category category = this.categoryService.findById(transactionBindingModel.getCategory());
        Event event = null;
        Wallet wallet = this.walletService.findById(transactionBindingModel.getWallet());

        if (transactionBindingModel.getEvent() != null) {
            event = this.eventService.findById(transactionBindingModel.getEvent());
        }

        if (user == null) {
            throw new InvalidInputException("User does not exist");
        } else if (category == null) {
            throw new InvalidInputException("Category does not exist");
        } else if (transactionBindingModel.getEvent() != null && event == null) {
            throw new InvalidInputException("Event does not exist");
        } else if (wallet == null) {
            throw new InvalidInputException("Wallet does not exist");
        }

        TransactionServiceModel transactionServiceModel = this.modelMapper.map(transactionBindingModel,TransactionServiceModel.class);
        transactionServiceModel
                .setCategory(category)
                .setUser(user)
                .setWallet(wallet)
                .setEvent(event)
                .setCreatedAt(LocalDateTime.now());

        Transaction transaction = this.modelMapper.map(transactionServiceModel,Transaction.class);

        Long id = this.transactionRepository.save(transaction).getId();

        this.walletService.editBalance(wallet.getId(),transactionBindingModel.getAmount(),category.getType());

        return id;
    }

    @Override
    public List<TransactionLastViewModel> findLastFiveTransactionsOfUser(String userEmail) {
        User user = this.userService.findByEmail(userEmail);
        List<Transaction> transactions = this.transactionRepository.findTop5ByUser_IdOrderByCreatedAtDesc(user.getId());

        return transactions
                .stream()
                .map(t-> {
                    TransactionLastViewModel lastViewModel = this.modelMapper.map(t, TransactionLastViewModel.class);
                    lastViewModel
                            .setCategory(t.getCategory().getName())
                            .setCategoryType(t.getCategory().getType().name())
                            .setWallet(t.getWallet().getName());
                    return lastViewModel;
                }).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findAllTransactionsByWallet(Long walletId) {
        return this.transactionRepository.findAllByWallet_IdOrderByCreatedAtDesc(walletId);
    }

    @Override
    public List<Transaction> findAllTransactionsBetweenDatesByWallet(Long walletId, LocalDateTime startDate, LocalDateTime endDate) {
        return this.transactionRepository.findTransactionByWallet_IdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualOrderByCreatedAtDesc(walletId,startDate,endDate);
    }

    @Override
    public void deleteTransactionsByCategoryId(Long categoryId) {
        List<Transaction> transactionsDb = this.transactionRepository.findAllByCategoryId(categoryId);
        this.transactionRepository.deleteAll(transactionsDb);
    }

    @Override
    public void deleteTransactionsByWalletId(Long walletId) {
            List<Transaction> transactionsDb = this.transactionRepository.findAllByWalletId(walletId);
            this.transactionRepository.deleteAll(transactionsDb);
    }
}

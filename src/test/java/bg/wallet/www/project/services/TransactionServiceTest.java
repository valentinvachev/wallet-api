package bg.wallet.www.project.services;

import bg.wallet.www.project.enums.UserRole;
import bg.wallet.www.project.models.*;
import bg.wallet.www.project.repositories.EventRepository;
import bg.wallet.www.project.repositories.TransactionRepository;
import bg.wallet.www.project.services.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {

    private Transaction testTrasaction;
    private TransactionRepository transactionRepository;
    private ModelMapper modelMapper;
    private UserService userService;
    private CategoryService categoryService;
    private EventService eventService;
    private WalletService walletService;

    private final String TRANSACTION_NAME = "TEST NAME";

    @BeforeEach
    public void init() {


        this.testTrasaction = (Transaction) new Transaction()
                .setCreatedAt(LocalDateTime.now())
                .setWallet(new Wallet())
                .setEvent(new Event())
                .setUser(new User())
                .setCategory(new Category())
                .setName(TRANSACTION_NAME)
                .setAmount(BigDecimal.valueOf(100))
                .setId(1L);


        this.transactionRepository = mock(TransactionRepository.class);
        this.userService = mock(UserService.class);
        this.categoryService = mock(CategoryService.class);
        this.eventService = mock(EventService.class);
        this.walletService = mock(WalletService.class);
        this.modelMapper = new ModelMapper();
    }
}

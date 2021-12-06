package bg.wallet.www.project.services;
import bg.wallet.www.project.enums.TransactionType;
import bg.wallet.www.project.enums.UserRole;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.Role;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.Wallet;
import bg.wallet.www.project.models.binding.WalletEditBindingModel;
import bg.wallet.www.project.models.view.WalletDetailsViewModel;
import bg.wallet.www.project.repositories.UserRepository;
import bg.wallet.www.project.repositories.WalletRepository;
import bg.wallet.www.project.services.impl.WalletServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WalletServiceTest {

    private Wallet testWallet;
    private WalletRepository walletRepository;
    private UserService userService;
    private TransactionService transactionService;
    private ModelMapper modelMapper;

    private final String WALLET_NAME = "TEST_WALLET";

    @BeforeEach
    public void init() {

        this.testWallet = (Wallet) new Wallet()
               .setName(WALLET_NAME)
                .setBalance(BigDecimal.valueOf(0))
                .setUser(new User()).setId(1L);


        this.userService = mock(UserService.class);
        this.transactionService = mock(TransactionService.class);
        this.walletRepository = mock(WalletRepository.class);
        this.modelMapper = new ModelMapper();

    }

    @Test
    public void editBalanceValidIncome() {

        when(this.walletRepository.getById(1L))
                .thenReturn(this.testWallet);

        assertEquals(BigDecimal.valueOf(0),this.testWallet.getBalance());

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        walletService.editBalance(1L,BigDecimal.valueOf(10), TransactionType.INCOME);

        assertEquals(BigDecimal.valueOf(10),this.testWallet.getBalance());
    }

    @Test
    public void editBalanceValidExpense() {

        when(this.walletRepository.getById(1L))
                .thenReturn(this.testWallet);

        assertEquals(BigDecimal.valueOf(0),this.testWallet.getBalance());

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        walletService.editBalance(1L,BigDecimal.valueOf(10), TransactionType.EXPENSE);

        assertEquals(BigDecimal.valueOf(-10),this.testWallet.getBalance());
    }

    @Test
    public void editBalanceInvalid() {

        when(this.walletRepository.getById(1L))
                .thenReturn(null);

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        walletService.editBalance(1L,BigDecimal.valueOf(10), TransactionType.EXPENSE);

        assertEquals(BigDecimal.valueOf(0),this.testWallet.getBalance());
    }

    @Test
    public void editNameValid() throws InvalidInputException, EntityNotFoundException {

        when(this.walletRepository.getById(any(Long.class)))
                .thenReturn(this.testWallet);

        when(this.userService.findByEmail(any(String.class)))
                .thenReturn(new User());

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        WalletEditBindingModel walletEditBindingModel = new WalletEditBindingModel();
        walletEditBindingModel.setName("WALLET NEW NAME");

        assertEquals(WALLET_NAME,this.testWallet.getName());
        walletService.editName(1L,walletEditBindingModel,"test@abv.bg");
        assertEquals("WALLET NEW NAME",this.testWallet.getName());
        assertEquals(1L,    walletService.editName(1L,walletEditBindingModel,"test@abv.bg"));
    }

    @Test
    public void editNameInvalidUser() throws InvalidInputException, EntityNotFoundException {

        when(this.walletRepository.getById(any(Long.class)))
                .thenReturn(this.testWallet);

        when(this.userService.findByEmail(any(String.class)))
                .thenReturn(null);

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        WalletEditBindingModel walletEditBindingModel = new WalletEditBindingModel();
        walletEditBindingModel.setName("WALLET NEW NAME");



        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () ->   walletService.editName(1L,walletEditBindingModel,"test@abv.bg")
        );

        assertTrue(thrown.getMessage().contains("User does not exist"));
        assertEquals(thrown.getClass(),InvalidInputException.class);
    }

    @Test
    public void editNameInvalidWallet() throws InvalidInputException, EntityNotFoundException {

        when(this.walletRepository.getById(any(Long.class)))
                .thenReturn(null);

        when(this.userService.findByEmail(any(String.class)))
                .thenReturn(new User());

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        WalletEditBindingModel walletEditBindingModel = new WalletEditBindingModel();
        walletEditBindingModel.setName("WALLET NEW NAME");



        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () ->   walletService.editName(1L,walletEditBindingModel,"test@abv.bg")
        );

        assertTrue(thrown.getMessage().contains("Wallet with this name does not exists"));
        assertEquals(thrown.getClass(),EntityNotFoundException.class);
    }

    @Test
    public void findAllValid() {
        List<Wallet> wallets = new ArrayList<>();
        wallets.add(this.testWallet);

        when(this.walletRepository.findAll())
                .thenReturn(wallets);

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        assertEquals(wallets.size(),    walletService.findAll().size());
    }

    @Test
    public void findById() {
        when(this.walletRepository.getById(any(Long.class)))
                .thenReturn(this.testWallet);

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        assertEquals(this.testWallet, walletService.findById(1L));
    }

    @Test
    public void getWalletById() throws EntityNotFoundException {
        when(this.walletRepository.getById(any(Long.class)))
                .thenReturn(this.testWallet);

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        assertEquals(this.testWallet.getId(), walletService.getWalletById(1L).getId());
    }

    @Test
    public void getWalletByIdInvalid() throws EntityNotFoundException {
        when(this.walletRepository.getById(any(Long.class)))
                .thenReturn(null);

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () ->   walletService.getWalletById(1L)
        );

        assertTrue(thrown.getMessage().contains("Wallet with this id does not exists"));
        assertEquals(thrown.getClass(),EntityNotFoundException.class);
    }

    @Test
    public void findTotal(){
        this.testWallet.setBalance(BigDecimal.valueOf(100));

        when(this.walletRepository.findSumOfAllWallets())
                .thenReturn(this.testWallet.getBalance());

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        assertEquals(BigDecimal.valueOf(100),walletService.findTotal());
    }
}

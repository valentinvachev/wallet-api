package bg.wallet.www.project.services;
import bg.wallet.www.project.enums.TransactionType;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.exceptions.NotAuthorizedException;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.Wallet;
import bg.wallet.www.project.models.binding.WalletEditBindingModel;
import bg.wallet.www.project.repositories.WalletRepository;
import bg.wallet.www.project.services.impl.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WalletServiceTest {

    private Wallet testWallet;
    private WalletRepository walletRepository;
    private UserService userService;
    private TransactionService transactionService;
    private ModelMapper modelMapper;

    private final String WALLET_NAME = "TEST_WALLET";
    private final Long WALLET_ID = 1L;

    @BeforeEach
    public void init() {

        this.testWallet = (Wallet) new Wallet()
               .setName(WALLET_NAME)
                .setBalance(BigDecimal.valueOf(0))
                .setId(WALLET_ID);

        User user = new User();
        user.setEmail("test@abv.bg").setId(2L);

        testWallet.setUser(user);

        this.userService = mock(UserService.class);
        this.transactionService = mock(TransactionService.class);
        this.walletRepository = mock(WalletRepository.class);
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void editBalanceValidIncome() {

        when(this.walletRepository.getWalletById(anyLong()))
                .thenReturn(this.testWallet);

        assertEquals(BigDecimal.valueOf(0),this.testWallet.getBalance());

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        walletService.editBalance(1L,BigDecimal.valueOf(10), TransactionType.INCOME);

        assertEquals(BigDecimal.valueOf(10),this.testWallet.getBalance());
    }

    @Test
    public void editBalanceValidExpense() {

        when(this.walletRepository.getWalletById(anyLong()))
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
    public void editNameValid() throws InvalidInputException, EntityNotFoundException, NotAuthorizedException {

        when(this.walletRepository.getWalletById(anyLong()))
                .thenReturn(this.testWallet);

        User user = new User();
        user.setId(2L);

        when(this.userService.findByEmail(anyString()))
                .thenReturn(user);

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        WalletEditBindingModel walletEditBindingModel = new WalletEditBindingModel();
        walletEditBindingModel.setName(WALLET_NAME);

        assertEquals(WALLET_NAME,this.testWallet.getName());
        walletService.editName(1L,walletEditBindingModel,"test@abv.bg");
        assertEquals(WALLET_NAME,this.testWallet.getName());
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

        assertTrue(thrown.getMessage().contains("Wallet with this id does not exists"));
        assertEquals(thrown.getClass(),EntityNotFoundException.class);
    }

    @Test
    public void findAllValid() {
        List<Wallet> wallets = new ArrayList<>();
        wallets.add(this.testWallet);

        when(this.walletRepository.findAllByUserEmail(anyString()))
                .thenReturn(wallets);

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);
        assertEquals(wallets.size(),    walletService.findAll("userEmail@abv.bg").size());
    }

    @Test
    public void findById() {
        when(this.walletRepository.getWalletById(any(Long.class)))
                .thenReturn(this.testWallet);

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        assertEquals(this.testWallet, walletService.findById(WALLET_ID));
    }

    @Test
    public void getWalletById() throws EntityNotFoundException, NotAuthorizedException {
        when(this.walletRepository.getWalletById(any(Long.class)))
                .thenReturn(this.testWallet);

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        assertEquals(this.testWallet.getId(), walletService.getWalletById(WALLET_ID,"test@abv.bg").getId());
    }

    @Test
    public void getWalletByIdInvalid() throws EntityNotFoundException {
        when(this.walletRepository.getById(any(Long.class)))
                .thenReturn(null);

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () ->   walletService.getWalletById(1L,"test@abv.bg")
        );

        assertTrue(thrown.getMessage().contains("Wallet with this id does not exists"));
        assertEquals(thrown.getClass(),EntityNotFoundException.class);
    }

    @Test
    public void findTotal(){
        this.testWallet.setBalance(BigDecimal.valueOf(100));

        when(this.walletRepository.findSumOfAllWallets("test@abv.bg"))
                .thenReturn(this.testWallet.getBalance());

        WalletService walletService = new WalletServiceImpl(this.walletRepository,this.modelMapper,this.userService,this.transactionService);

        assertEquals(BigDecimal.valueOf(100),walletService.findTotal("test@abv.bg"));
    }
}

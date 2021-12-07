package bg.wallet.www.project.services;
import bg.wallet.www.project.models.*;
import bg.wallet.www.project.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.mockito.Mockito.mock;


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

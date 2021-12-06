package bg.wallet.www.project.web;

import bg.wallet.www.project.enums.TransactionType;
import bg.wallet.www.project.enums.UserRole;
import bg.wallet.www.project.models.*;
import bg.wallet.www.project.models.binding.TransactionBindingModel;
import bg.wallet.www.project.repositories.*;
import bg.wallet.www.project.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static bg.wallet.www.project.web.Utils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    @Test
    @WithMockUser(username = "test_transaction@abv.bg", roles = {"USER", "ADMIN"})
    public void createTransaction() throws Exception {

        Category category = new Category();
        category.setName("TEST CATEGORY NAME TRANSACTION").setType(TransactionType.INCOME);
        Category categoryDb = this.categoryRepository.save(category);

        User user = new User();
        User userDb;
        Role role;
        if (this.roleRepository.count() == 0) {
            role = new Role(UserRole.ADMIN);
            this.roleRepository.save(role);
        } else {
            role = this.roleRepository.findAll().get(0);
        }

        Set<Role> roleList = new HashSet<>();
        roleList.add(role);

        user.setUsername("TEST USERNAME TRANSACTION")
                .setEmail("test_transaction@abv.bg")
                .setPassword("TEST PASS TRANSACTION")
                .setAuthorities(roleList);

        userDb = this.userRepository.save(user);


        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(100))
                .setUser(user)
                .setName("TEST WALLET NAME TRANSACTION");

        Wallet walletDb = this.walletRepository.save(wallet);

        Event event = new Event();
        event.setName("TEST EVENT NAME TRANSACTION")
                .setStartDate(LocalDate.now())
                .setEndDate(LocalDate.now().plusDays(2));

        Event eventDb = this.eventRepository.save(event);

        TransactionBindingModel transactionBindingModel = new TransactionBindingModel();
        transactionBindingModel.setName("TEST TRANSACTION NAME")
                .setCategory(categoryDb.getId())
                .setWallet(walletDb.getId())
                .setEvent(eventDb.getId())
                .setAmount(BigDecimal.valueOf(10));

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userDb.getAuthorities().forEach(a -> authorities.add(new SimpleGrantedAuthority(a.getAuthority().name())));

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("test_transaction@abv.bg", null, authorities);

        this.mockMvc.perform(post("/api/transactions").principal(authenticationToken)
                .content(asJsonString(transactionBindingModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }
}

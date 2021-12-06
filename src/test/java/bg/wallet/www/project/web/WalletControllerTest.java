package bg.wallet.www.project.web;

import bg.wallet.www.project.enums.UserRole;
import bg.wallet.www.project.models.Role;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.binding.WalletBindingModel;
import bg.wallet.www.project.repositories.RoleRepository;
import bg.wallet.www.project.repositories.UserRepository;
import bg.wallet.www.project.services.WalletService;
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
import java.util.*;

import static bg.wallet.www.project.web.Utils.asJsonString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    WalletService walletService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    @WithMockUser(username = "test@abv.bg",roles = {"USER","ADMIN"})
    public void createWalletUserDoesNotExists() throws Exception {
        WalletBindingModel walletBindingModel = new WalletBindingModel();
        walletBindingModel
                .setBalance(BigDecimal.valueOf(100))
                .setName("TEST WALLET");

        this.mockMvc.perform(post("/api/wallets")
                .content(asJsonString(walletBindingModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "test@abv.bg",roles = {"USER","ADMIN"})
    public void createWalletUser() throws Exception {
        WalletBindingModel walletBindingModel = new WalletBindingModel();
        walletBindingModel
                .setBalance(BigDecimal.valueOf(100))
                .setName("TEST WALLET");

        User user = new User();
        Set<Role> roles = new HashSet<>();
        Role r = new Role(UserRole.USER);
        this.roleRepository.save(r);
        user.setAuthorities(roles)
                .setEmail("test@abv.bg")
                .setUsername("admin")
                .setPassword("pass");

        this.userRepository.save(user);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(a->authorities.add(new SimpleGrantedAuthority(a.getAuthority().name())));

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("test@abv.bg",null,authorities);

        this.mockMvc.perform(post("/api/wallets").principal(authenticationToken)
                .content(asJsonString(walletBindingModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}

package bg.wallet.www.project.web;

import bg.wallet.www.project.enums.TransactionType;
import bg.wallet.www.project.enums.UserRole;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.Role;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.repositories.CategoryRepository;
import bg.wallet.www.project.repositories.RoleRepository;
import bg.wallet.www.project.repositories.TransactionRepository;
import bg.wallet.www.project.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private String TEST_CATEGORY1_NAME = "FOOD";
    private String TEST_CATEGORY2_NAME = "SALARY";
    private String USER_EMAIL = "test@abv.bg";


    @Test
    public void getAllCategoriesNoUserAuth() throws Exception {
        this.mockMvc.perform(get("/api/categories")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test@abv.bg",roles = {"USER","ADMIN"})
    public void getAllCategories() throws Exception {

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        authorities.add(new SimpleGrantedAuthority("USER"));

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(USER_EMAIL,null,authorities);

        this.mockMvc.perform(get("/api/categories").principal(authenticationToken))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@abv.bg",roles = {"USER","ADMIN"})
    public void getAllCategoriesAmountEmptyCategories() throws Exception {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        authorities.add(new SimpleGrantedAuthority("USER"));

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(USER_EMAIL,null,authorities);

        this.mockMvc.perform(get("/api/categories")
                .param("groupBy","true")
                .principal(authenticationToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));;

    }
}

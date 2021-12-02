package bg.wallet.www.project.web;

import bg.wallet.www.project.enums.TransactionType;
import bg.wallet.www.project.enums.UserRole;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.Role;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.repositories.CategoryRepository;
import bg.wallet.www.project.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.HashSet;
import java.util.Set;

import static net.bytebuddy.matcher.ElementMatchers.is;
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
    private UserRepository userRepository;

    private String TEST_CATEGORY1_NAME = "FOOD";
    private String TEST_CATEGORY2_NAME = "SALARY";

    @BeforeEach
    public void setUp() {
        Category category1 = new Category().setName(TEST_CATEGORY1_NAME).setType(TransactionType.EXPENSE);
        Category category2 = new Category().setName(TEST_CATEGORY2_NAME).setType(TransactionType.INCOME);

        this.categoryRepository.save(category1);
        this.categoryRepository.save(category2);
    }

    @Test
    public void getAllCategoriesNoUserAuth() throws Exception {
        this.mockMvc.perform(get("/api/categories")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin@abv.bg",roles = {"USER","ADMIN"})
    public void getAllCategories() throws Exception {
        this.mockMvc.perform(get("/api/categories")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@abv.bg",roles = {"USER","ADMIN"})
    public void getAllCategoriesAmountEmptyCategories() throws Exception {
        this.mockMvc.perform(get("/api/categories").param("groupBy","true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}

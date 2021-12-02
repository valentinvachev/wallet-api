package bg.wallet.www.project.web;

import bg.wallet.www.project.enums.TransactionType;
import bg.wallet.www.project.enums.UserRole;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.Role;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.binding.UserRegisterBindingModel;
import bg.wallet.www.project.repositories.CategoryRepository;
import bg.wallet.www.project.repositories.RoleRepository;
import bg.wallet.www.project.repositories.UserRepository;
import bg.wallet.www.project.services.CategoryService;
import bg.wallet.www.project.services.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private String TEST_USER1_USERNAME = "admin";
    private String TEST_USER1_EMAIL = "admin@abv.bg";
    private String TEST_USER1_PASS = "parola123";


    @Test
    @WithMockUser(username = "admin@abv.bg",roles = {"USER","ADMIN"})
    public void createUserValid() throws Exception {

        UserRegisterBindingModel userRegisterBindingModel = new UserRegisterBindingModel()
                .setUsername(TEST_USER1_USERNAME)
                .setEmail(TEST_USER1_EMAIL)
                .setPassword(TEST_USER1_PASS)
                .setConfirmPassword(TEST_USER1_PASS);

        this.mockMvc.perform(post("/api/users/register").content(asJsonString(userRegisterBindingModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin@abv.bg",roles = {"USER","ADMIN"})
    public void createUserInvalidUsername() throws Exception {

        UserRegisterBindingModel userRegisterBindingModel = new UserRegisterBindingModel()
                .setUsername("")
                .setEmail(TEST_USER1_EMAIL)
                .setPassword(TEST_USER1_PASS)
                .setConfirmPassword(TEST_USER1_PASS);

        this.mockMvc.perform(post("/api/users/register").content(asJsonString(userRegisterBindingModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
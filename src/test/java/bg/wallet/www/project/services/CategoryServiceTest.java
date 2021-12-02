package bg.wallet.www.project.services;

import bg.wallet.www.project.enums.TransactionType;
import bg.wallet.www.project.enums.UserRole;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.Role;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.view.UserViewInfoModel;
import bg.wallet.www.project.repositories.CategoryRepository;
import bg.wallet.www.project.repositories.UserRepository;
import bg.wallet.www.project.services.impl.CategoryServiceImpl;
import bg.wallet.www.project.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CategoryServiceTest {

    private Category testCategory;
    private UserService userService;
    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;

    private String TEST_CATEGORY1_NAME = "FOOD";

    @BeforeEach
    public void init() {

        this.testCategory = new Category().setName(TEST_CATEGORY1_NAME).setType(TransactionType.EXPENSE);

        this.userService = Mockito.mock(UserService.class);
        this.categoryRepository = Mockito.mock(CategoryRepository.class);
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void findByIdValid() {
        Mockito.when(this.categoryRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(this.testCategory));

        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService);

        Category actual = categoryService.findById(1L);

        assertEquals(this.testCategory.getName(),actual.getName());
    }
//
//    @Test
//    public void findByIdValidNull() {
//        Mockito.when(this.categoryRepository.findById(1L))
//                .thenReturn(null);
//
//        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService);
//
//        assertNull(categoryService.findById(1L));
//    }
}

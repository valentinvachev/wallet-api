package bg.wallet.www.project.services;

import bg.wallet.www.project.enums.TransactionType;
import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.exceptions.NotAuthorizedException;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.binding.CategoryBindingModel;
import bg.wallet.www.project.models.binding.CategoryEditBindingModel;
import bg.wallet.www.project.repositories.CategoryRepository;
import bg.wallet.www.project.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


public class CategoryServiceTest {

    private Category testCategory;
    private UserService userService;
    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;
    private TransactionService transactionService;

    private String TEST_CATEGORY1_NAME = "FOOD";
    private Long TEST_CATEGORY1_ID = 1L;

    @BeforeEach
    public void init() {

        this.testCategory = (Category) new Category()
                .setName(TEST_CATEGORY1_NAME)
                .setType(TransactionType.EXPENSE)
                .setId(TEST_CATEGORY1_ID);

        User user = new User();
        user.setId(2L);

        this.testCategory.setUser(user);

        this.userService = mock(UserService.class);
        this.transactionService = mock(TransactionService.class);
        this.categoryRepository = mock(CategoryRepository.class);
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void findByIdValid() {
        when(this.categoryRepository.getCategoryById(anyLong()))
                .thenReturn(this.testCategory);

        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService, transactionService);

        Category actual = categoryService.findById(TEST_CATEGORY1_ID);

        assertEquals(this.testCategory.getName(),actual.getName());
    }

    @Test
    public void findByIdValidNull() {
        when(this.categoryRepository.getCategoryById(anyLong()))
                .thenReturn(null);

        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService, transactionService);

        Category category = categoryService.findById(TEST_CATEGORY1_ID);

        assertNull(category);
    }

    @Test
    public void saveValid() throws DuplicateEntityException, InvalidInputException {
        when(this.categoryRepository.findAllByUserEmail(anyString()))
                .thenReturn(null);

        when(this.userService.findByEmail(anyString()))
                .thenReturn(new User());

        Category category = new Category();
        category.setName(TEST_CATEGORY1_NAME);
        category.setType(TransactionType.EXPENSE);
        category.setId(TEST_CATEGORY1_ID);

        when(this.categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService, transactionService);

        CategoryBindingModel categoryBindingModel = new CategoryBindingModel() {{
            setName(TEST_CATEGORY1_NAME);
            setType("EXPENSE");
        }};

        long resultService = categoryService.save(categoryBindingModel, "test@abv.bg");
        assertEquals(TEST_CATEGORY1_ID,resultService);
    }


    @Test
    public void saveThrows() throws DuplicateEntityException {
        when(this.categoryRepository.findByNameAndUserEmail(anyString(),anyString()))
                .thenReturn(this.testCategory);

        when(this.userService.findByEmail(anyString()))
                .thenReturn(new User());


        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService, transactionService);

        CategoryBindingModel categoryBindingModel = new CategoryBindingModel().setName(TEST_CATEGORY1_NAME).setType("EXPENSE");

        DuplicateEntityException thrown = assertThrows(
                DuplicateEntityException.class,
                () -> categoryService.save(categoryBindingModel, "test@abv.bg")
        );

        assertTrue(thrown.getMessage().contains("Category already exists"));
        assertEquals(thrown.getClass(),DuplicateEntityException.class);
    }


    @Test
    public void findAllAmount(){
        List<Map<String, String>> result = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("total","2000");
        result.add(map);

        when(this.categoryRepository.findAmountsByCategories(anyString()))
                .thenReturn(result);

        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService, transactionService);

        assertEquals(result,categoryService.findAllAmount("test@abv.bg"));
    }

    @Test
    public void editNameValid() throws InvalidInputException, EntityNotFoundException, NotAuthorizedException {
        when(this.categoryRepository.getCategoryById(anyLong()))
                .thenReturn(this.testCategory);

        when(this.categoryRepository.save(any(Category.class)))
                .thenReturn(this.testCategory);

        User user = new User();
        user.setId(2L);

        when(this.userService.findByEmail(anyString()))
                .thenReturn(user);

        CategoryEditBindingModel categoryBindingModel = new CategoryEditBindingModel();
        categoryBindingModel.setName(TEST_CATEGORY1_NAME);

        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService, transactionService);

        assertEquals(1L,categoryService.editName(1L,categoryBindingModel,"test@abv.bg"));
    }

    @Test
    public void editNameInvalidUser() {
        when(this.categoryRepository.getCategoryById(1L))
                .thenReturn(this.testCategory);

        when(this.userService.findByEmail("test@abv.bg"))
                .thenReturn(null);

        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService, transactionService);

        CategoryEditBindingModel categoryBindingModel = new CategoryEditBindingModel();
        categoryBindingModel.setName("NEW FOOD");

        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> categoryService.editName(1L,categoryBindingModel,"test@abv.bg")
        );

        assertTrue(thrown.getMessage().contains("User does not exist"));
        assertEquals(thrown.getClass(),InvalidInputException.class);
    }

    @Test
    public void editNameNotFoundCategory() {

        when(this.categoryRepository.getCategoryById(anyLong()))
                .thenReturn(null);

        when(this.userService.findByEmail("test@abv.bg"))
                .thenReturn(new User());

        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService, transactionService);

        CategoryEditBindingModel categoryBindingModel = new CategoryEditBindingModel();
        categoryBindingModel.setName("NEW FOOD");

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.editName(1L,categoryBindingModel,"test@abv.bg")
        );

        assertTrue(thrown.getMessage().contains("Category does not exists"));
        assertEquals(thrown.getClass(),EntityNotFoundException.class);
    }
}

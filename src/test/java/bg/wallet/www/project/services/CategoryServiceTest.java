package bg.wallet.www.project.services;

import bg.wallet.www.project.enums.TransactionType;
import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.binding.CategoryBindingModel;
import bg.wallet.www.project.models.binding.CategoryEditBindingModel;
import bg.wallet.www.project.repositories.CategoryRepository;
import bg.wallet.www.project.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
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

    private String TEST_CATEGORY1_NAME = "FOOD";

    @BeforeEach
    public void init() {

        this.testCategory = (Category) new Category().setName(TEST_CATEGORY1_NAME).setType(TransactionType.EXPENSE).setId(1l);

        this.userService = mock(UserService.class);
        this.categoryRepository = mock(CategoryRepository.class);
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void findByIdValid() {
        when(this.categoryRepository.getById(1L))
                .thenReturn(this.testCategory);

        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService);

        Category actual = categoryService.findById(1L);

        assertEquals(this.testCategory.getName(),actual.getName());
    }

    @Test
    public void findByIdValidNull() {
        when(this.categoryRepository.getById(1L))
                .thenReturn(null);

        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService);

        Category category = categoryService.findById(1L);

        assertNull(category);
    }

    @Test
    public void saveValid() throws DuplicateEntityException {
        when(this.categoryRepository.findByName(TEST_CATEGORY1_NAME))
                .thenReturn(null);

        Category category = new Category();
        category.setName(TEST_CATEGORY1_NAME);
        category.setType(TransactionType.EXPENSE);
        category.setId(1L);

        when(this.categoryRepository.save(any(Category.class))).thenReturn(category);


        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService);

        CategoryBindingModel categoryBindingModel = new CategoryBindingModel() {{
            setName(TEST_CATEGORY1_NAME);
            setType("EXPENSE");
        }};

        long resultService = categoryService.save(categoryBindingModel);
        assertEquals(1L,resultService);
    }


    @Test
    public void saveThrows() throws DuplicateEntityException {
        when(this.categoryRepository.findByName(TEST_CATEGORY1_NAME))
                .thenReturn(this.testCategory);


        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService);

        CategoryBindingModel categoryBindingModel = new CategoryBindingModel().setName(TEST_CATEGORY1_NAME).setType("EXPENSE");

        DuplicateEntityException thrown = assertThrows(
                DuplicateEntityException.class,
                () -> categoryService.save(categoryBindingModel)
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

        when(this.categoryRepository.findAmountsByCategories())
                .thenReturn(result);


        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService);

        assertEquals(result,categoryService.findAllAmount());
    }

    @Test
    public void editNameValid() throws InvalidInputException, EntityNotFoundException {
        when(this.categoryRepository.getById(1L))
                .thenReturn(this.testCategory);

        when(this.categoryRepository.save(this.testCategory))
                .thenReturn(this.testCategory);

        User user = new User();

        when(this.userService.findByEmail("test@abv.bg"))
                .thenReturn(user);

        CategoryEditBindingModel categoryBindingModel = new CategoryEditBindingModel();
        categoryBindingModel.setName("NEW FOOD");

        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService);

        assertEquals(1L,categoryService.editName(1L,categoryBindingModel,"test@abv.bg"));
    }

    @Test
    public void editNameInvalidUser() {
        when(this.categoryRepository.getById(1L))
                .thenReturn(this.testCategory);

        when(this.userService.findByEmail("test@abv.bg"))
                .thenReturn(null);

        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService);

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

        when(this.categoryRepository.getById(1L))
                .thenReturn(null);

        when(this.userService.findByEmail("test@abv.bg"))
                .thenReturn(new User());

        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository,this.modelMapper,this.userService);

        CategoryEditBindingModel categoryBindingModel = new CategoryEditBindingModel();
        categoryBindingModel.setName("NEW FOOD");

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.editName(1L,categoryBindingModel,"test@abv.bg")
        );

        assertTrue(thrown.getMessage().contains("Category with this name does not exists"));
        assertEquals(thrown.getClass(),EntityNotFoundException.class);
    }
}

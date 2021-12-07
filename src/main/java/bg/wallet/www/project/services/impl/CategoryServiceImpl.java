package bg.wallet.www.project.services.impl;

import bg.wallet.www.project.enums.TransactionType;
import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.exceptions.NotAuthorizedException;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.binding.CategoryBindingModel;
import bg.wallet.www.project.models.binding.CategoryEditBindingModel;
import bg.wallet.www.project.models.service.CategoryServiceModel;
import bg.wallet.www.project.models.view.CategoryViewModel;
import bg.wallet.www.project.repositories.CategoryRepository;
import bg.wallet.www.project.services.CategoryService;
import bg.wallet.www.project.services.TransactionService;
import bg.wallet.www.project.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final TransactionService transactionService;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, UserService userService, @Lazy TransactionService transactionService) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @Override
    public Long save(CategoryBindingModel categoryBindingModel, String userEmail) throws DuplicateEntityException, InvalidInputException {
        User user = this.userService.findByEmail(userEmail);

        if (user == null) {
            throw new InvalidInputException("User does not exist");
        }

        Category categoryDb = this.categoryRepository.findByNameAndUserEmail(categoryBindingModel.getName(),userEmail);

        if (categoryDb == null) {

            CategoryServiceModel categoryServiceModel = this.modelMapper.map(categoryBindingModel,CategoryServiceModel.class);
            Category category = this.modelMapper.map(categoryServiceModel,Category.class);
            category.setUser(user);
            Category categorySaved = this.categoryRepository.save(category);
            return categorySaved.getId();

        } else {
            throw new DuplicateEntityException("Category already exists");
        }
    }

    @Override
    public List<Map<String, String>> findAllAmount(String userEmail) {
        List<Map<String, String>> result  = this.categoryRepository.findAmountsByCategories(userEmail);
        return result;
    }

    @Override
    public Map<String,List<CategoryViewModel>> findAll(String userEmail) {
        Map<String,List<CategoryViewModel>> map = new HashMap<>();
        List<CategoryViewModel> mapExpense = new ArrayList<>();
        List<CategoryViewModel> mapIncome = new ArrayList<>();

        this.categoryRepository.findAllByUserEmail(userEmail).forEach(c->{
            if (c.getType().equals(TransactionType.EXPENSE)) {
                mapExpense.add(this.modelMapper.map(c,CategoryViewModel.class));
            } else {
                mapIncome.add(this.modelMapper.map(c,CategoryViewModel.class));
            }
        });

        map.put("INCOME",mapIncome);
        map.put("EXPENSE",mapExpense);

        return map;
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.getCategoryById(id);
    }

    @Override
    public Long editName(Long id, CategoryEditBindingModel categoryEditBindingModel, String userEmail) throws InvalidInputException, EntityNotFoundException, NotAuthorizedException {
        User user = this.userService.findByEmail(userEmail);

        if (user == null) {
            throw new InvalidInputException("User does not exist");
        }

        Category categoryDb = this.categoryRepository.getCategoryById(id);

        if (categoryDb != null) {

            if (!categoryDb.getUser().getId().equals(user.getId())) {
                throw new NotAuthorizedException("User not authorized");
            }

            categoryDb.setName(categoryEditBindingModel.getName());
            this.categoryRepository.save(categoryDb);
            return categoryDb.getId();
        } else {
            throw new EntityNotFoundException("Category does not exists");
        }
    }

    @Override
    public void deleteCategory(Long id, String userEmail) throws NotAuthorizedException, EntityNotFoundException {
        Category categoryDb = this.categoryRepository.getCategoryById(id);

        if (categoryDb != null) {

            if (!categoryDb.getUser().getEmail().equals(userEmail)) {
                throw new NotAuthorizedException("User not authorized");
            }

            this.transactionService.deleteTransactionsByCategoryId(categoryDb.getId());
            this.categoryRepository.delete(categoryDb);

        } else {
            throw new EntityNotFoundException("Category does not exists");
        }
    }
}

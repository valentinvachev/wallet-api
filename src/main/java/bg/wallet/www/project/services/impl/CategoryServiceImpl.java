package bg.wallet.www.project.services.impl;

import bg.wallet.www.project.enums.TransactionType;
import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.binding.CategoryBindingModel;
import bg.wallet.www.project.models.binding.CategoryEditBindingModel;
import bg.wallet.www.project.models.service.CategoryServiceModel;
import bg.wallet.www.project.models.view.CategoryViewModel;
import bg.wallet.www.project.repositories.CategoryRepository;
import bg.wallet.www.project.services.CategoryService;
import bg.wallet.www.project.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }
//
//    public List<Category> findAll(String name) {
//        return categoryRepository.findAll(new CategorySearchSpecification(name));
//    }


    @Override
    public Map<String,List<CategoryViewModel>> findAll() {
        Map<String,List<CategoryViewModel>> map = new HashMap<>();
        List<CategoryViewModel> mapExpense = new ArrayList<>();
        List<CategoryViewModel> mapIncome = new ArrayList<>();

        this.categoryRepository.findAll().forEach(c->{
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
        return this.categoryRepository.getById(id);
    }

    @Override
    public Long save(CategoryBindingModel categoryBindingModel) throws DuplicateEntityException {
        Category categoryDb = this.categoryRepository.findByName(categoryBindingModel.getName());

        if (categoryDb == null) {
            CategoryServiceModel categoryServiceModel = this.modelMapper.map(categoryBindingModel,CategoryServiceModel.class);
            Category category = this.modelMapper.map(categoryServiceModel,Category.class);

            Category categorySaved = this.categoryRepository.save(category);
            return categorySaved.getId();
        } else {
            throw new DuplicateEntityException("Category already exists");
        }
    }


    @Override
    public Long editName(Long id, CategoryEditBindingModel categoryEditBindingModel, String userEmail) throws InvalidInputException, EntityNotFoundException {
        Category categoryDb = this.categoryRepository.getById(id);
        User user = this.userService.findByEmail(userEmail);

        if (user == null) {
            throw new InvalidInputException("User does not exist");
        }

        if (categoryDb != null) {
            categoryDb.setName(categoryEditBindingModel.getName());
            this.categoryRepository.save(categoryDb);
            return categoryDb.getId();
        } else {
            throw new EntityNotFoundException("Category with this name does not exists");
        }
    }

    @Override
    public List<Map<String, String>> findAllAmount() {
        List<Map<String, String>> result  = this.categoryRepository.findAmountsByCategories();
        return result;
    }
}

package bg.wallet.www.project.services.impl;

import bg.wallet.www.project.enums.TransactionType;
import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.binding.CategoryBindingModel;
import bg.wallet.www.project.models.service.CategoryServiceModel;
import bg.wallet.www.project.models.view.CategoryViewModel;
import bg.wallet.www.project.repositories.CategoryRepository;
import bg.wallet.www.project.repositories.CategorySearchSpecification;
import bg.wallet.www.project.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
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
        return this.categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Long save(CategoryBindingModel categoryBindingModel) throws DuplicateEntityException {
        Category categoryDb = this.categoryRepository.findByName(categoryBindingModel.getName());

        if (categoryDb == null) {
            CategoryServiceModel categoryServiceModel = this.modelMapper.map(categoryBindingModel,CategoryServiceModel.class);
            Category category = this.modelMapper.map(categoryServiceModel,Category.class);

            return this.categoryRepository.save(category).getId();
        } else {
            throw new DuplicateEntityException("Category already exists");
        }
    }
}

package bg.wallet.www.project.services.impl;

import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.binding.CategoryBindingModel;
import bg.wallet.www.project.models.service.CategoryServiceModel;
import bg.wallet.www.project.repositories.CategoryRepository;
import bg.wallet.www.project.repositories.CategorySearchSpecification;
import bg.wallet.www.project.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<Category> findAll(String name) {
        return categoryRepository.findAll(new CategorySearchSpecification(name));
    }

    @Override
    public void save(CategoryBindingModel categoryBindingModel) {
        Category categoryDb = this.categoryRepository.findByName(categoryBindingModel.getName());

        if (categoryDb == null) {
            CategoryServiceModel categoryServiceModel = this.modelMapper.map(categoryBindingModel,CategoryServiceModel.class);
            Category category = this.modelMapper.map(categoryServiceModel,Category.class);

            this.categoryRepository.save(category);
        }
    }
}

package bg.wallet.www.project.services;
import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.binding.CategoryBindingModel;
import bg.wallet.www.project.models.view.CategoryViewModel;

import java.util.List;
import java.util.Map;

public interface CategoryService {
//    List<Category> findAll(String name);
    Category findById(Long id);
    Map<String,List<CategoryViewModel>> findAll();
    Long save(CategoryBindingModel categoryBindingModel) throws DuplicateEntityException;
}

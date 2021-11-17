package bg.wallet.www.project.services;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.binding.CategoryBindingModel;

import java.util.List;

public interface CategoryService {
    List<Category> findAll(String name);
    void save(CategoryBindingModel categoryBindingModel);
}

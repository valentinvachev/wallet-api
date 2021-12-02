package bg.wallet.www.project.services;
import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.binding.CategoryBindingModel;
import bg.wallet.www.project.models.binding.CategoryEditBindingModel;
import bg.wallet.www.project.models.binding.WalletEditBindingModel;
import bg.wallet.www.project.models.view.CategoryViewModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CategoryService {
//    List<Category> findAll(String name);
    Category findById(Long id);
    Map<String,List<CategoryViewModel>> findAll();
    Long save(CategoryBindingModel categoryBindingModel) throws DuplicateEntityException;
    Long editName(Long id, CategoryEditBindingModel categoryEditBindingModel, String userEmail) throws InvalidInputException, EntityNotFoundException;
    List<Map<String, String>>findAllAmount();
}

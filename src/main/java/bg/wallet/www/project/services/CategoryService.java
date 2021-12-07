package bg.wallet.www.project.services;
import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.exceptions.NotAuthorizedException;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.binding.CategoryBindingModel;
import bg.wallet.www.project.models.binding.CategoryEditBindingModel;
import bg.wallet.www.project.models.view.CategoryViewModel;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    Category findById(Long id);
    Map<String,List<CategoryViewModel>> findAll(String userEmail);
    Long save(CategoryBindingModel categoryBindingModel, String userEmail) throws DuplicateEntityException, InvalidInputException;
    Long editName(Long id, CategoryEditBindingModel categoryEditBindingModel, String userEmail) throws InvalidInputException, EntityNotFoundException, NotAuthorizedException;
    List<Map<String, String>>findAllAmount(String userEmail);
    void deleteCategory(Long id, String userEmail) throws NotAuthorizedException, EntityNotFoundException;
}

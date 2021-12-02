package bg.wallet.www.project.controllers;

import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.binding.CategoryBindingModel;
import bg.wallet.www.project.models.binding.CategoryEditBindingModel;
import bg.wallet.www.project.models.binding.WalletEditBindingModel;
import bg.wallet.www.project.services.CategoryService;
import bg.wallet.www.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
    }

    @PostMapping("")
    public ResponseEntity<?> createCategory(HttpServletRequest request, @Valid @RequestBody CategoryBindingModel categoryBindingModel) throws URISyntaxException, DuplicateEntityException {

        Map<String,String> bodyResponse = new HashMap<>();

        bodyResponse.put("created",String.valueOf( this.categoryService.save(categoryBindingModel)));

        return ResponseEntity.created(new URI(request.getServletPath())).body(bodyResponse);
    }

    @GetMapping("")
    public ResponseEntity<?> getCategories (@RequestParam(required = false) String groupBy) {

        if ("true".equals(groupBy)) {
            return ResponseEntity.ok().body(this.categoryService.findAllAmount());
        }

        return ResponseEntity.ok().body(this.categoryService.findAll());
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> editWallet(HttpServletRequest request, @PathVariable Long id, @Valid @RequestBody CategoryEditBindingModel categoryEditBindingModel) throws InvalidInputException, EntityNotFoundException {

        Map<String,String> bodyResponse = new HashMap<>();

        //TODO if user that edits owns the category

        bodyResponse.put("edited",String.valueOf(this.categoryService.editName(id,categoryEditBindingModel,"admin@abv.bg")));

        return ResponseEntity.ok().body(bodyResponse);
    }

//    @GetMapping("")
//    public List<Category> findSpecificCategory(@RequestParam(required = false) String name) {
//        return categoryService.findAll(name);
//    }
}

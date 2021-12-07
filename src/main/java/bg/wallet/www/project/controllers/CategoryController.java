package bg.wallet.www.project.controllers;

import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.exceptions.NotAuthorizedException;
import bg.wallet.www.project.models.binding.CategoryBindingModel;
import bg.wallet.www.project.models.binding.CategoryEditBindingModel;
import bg.wallet.www.project.services.CategoryService;
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
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("")
    public ResponseEntity<?> createCategory(HttpServletRequest request, @Valid @RequestBody CategoryBindingModel categoryBindingModel) throws URISyntaxException, DuplicateEntityException, InvalidInputException {

        String userEmail = request.getUserPrincipal().getName();
        Map<String,String> bodyResponse = new HashMap<>();

        bodyResponse.put("created",String.valueOf( this.categoryService.save(categoryBindingModel,userEmail)));

        return ResponseEntity.created(new URI(request.getServletPath())).body(bodyResponse);
    }

    @GetMapping("")
    public ResponseEntity<?> getCategories (HttpServletRequest request,@RequestParam(required = false) String groupBy) {

        String userEmail = request.getUserPrincipal().getName();

        if ("true".equals(groupBy)) {
            return ResponseEntity.ok().body(this.categoryService.findAllAmount(userEmail));
        }

        return ResponseEntity.ok().body(this.categoryService.findAll(userEmail));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> editWallet(HttpServletRequest request, @PathVariable Long id, @Valid @RequestBody CategoryEditBindingModel categoryEditBindingModel) throws InvalidInputException, EntityNotFoundException, NotAuthorizedException {

        String userEmail = request.getUserPrincipal().getName();
        Map<String,String> bodyResponse = new HashMap<>();

        bodyResponse.put("edited",String.valueOf(this.categoryService.editName(id,categoryEditBindingModel,userEmail)));

        return ResponseEntity.ok().body(bodyResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(HttpServletRequest request, @PathVariable Long id) throws EntityNotFoundException, NotAuthorizedException {

        String userEmail = request.getUserPrincipal().getName();
        Map<String,String> bodyResponse = new HashMap<>();

        this.categoryService.deleteCategory(id,userEmail);

        bodyResponse.put("deleted",String.valueOf(id));

        return ResponseEntity.ok().body(bodyResponse);
    }
}

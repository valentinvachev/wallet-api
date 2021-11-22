package bg.wallet.www.project.controllers;

import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.binding.CategoryBindingModel;
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
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
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
    public ResponseEntity<?> getCategories (@RequestParam(required = false) String name) {
        return ResponseEntity.ok().body(this.categoryService.findAll());
    }

//    @GetMapping("")
//    public List<Category> findSpecificCategory(@RequestParam(required = false) String name) {
//        return categoryService.findAll(name);
//    }
}

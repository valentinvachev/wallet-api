package bg.wallet.www.project.controllers;

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
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body("Welcome to out site");
    }

    @PostMapping("")
    public ResponseEntity<?> createCategory(HttpServletRequest request, @Valid @RequestBody CategoryBindingModel categoryBindingModel) throws URISyntaxException {

        Map<String,String> bodyResponse = new HashMap<>();
        //TODO handle errors
        this.categoryService.save(categoryBindingModel);

        bodyResponse.put("created",categoryBindingModel.getName());

        return ResponseEntity.created(new URI(request.getServletPath())).body(bodyResponse);
    }

    @GetMapping("")
    public List<Category> findSpecificCategory(@RequestParam(required = false) String name) {
        return categoryService.findAll(name);
    }
}

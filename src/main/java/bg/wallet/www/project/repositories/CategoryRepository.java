package bg.wallet.www.project.repositories;

import bg.wallet.www.project.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>, JpaSpecificationExecutor<Category> {
    Category findByName(String name);
    @Query("SELECT c.name AS name,SUM(t.amount) as total, c.type as type FROM Transaction t JOIN Category c on c.id = t.category.id GROUP BY c.id ORDER BY c.type, SUM(t.amount) DESC")
    List<Map<String,String>> findAmountsByCategories();
    Category getById(Long id);
}

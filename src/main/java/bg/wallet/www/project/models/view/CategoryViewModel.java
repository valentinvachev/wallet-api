package bg.wallet.www.project.models.view;

public class CategoryViewModel {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public CategoryViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryViewModel setName(String name) {
        this.name = name;
        return this;
    }
}

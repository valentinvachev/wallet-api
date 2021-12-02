package bg.wallet.www.project.models.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryEditBindingModel {
    @NotNull
    @Size(min = 2,message = "Name should be at least 2 symbols")
    private String name;

    public String getName() {
        return name;
    }

    public CategoryEditBindingModel setName(String name) {
        this.name = name;
        return this;
    }
}

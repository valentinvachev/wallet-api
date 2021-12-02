package bg.wallet.www.project.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "quotes")
public class Quote extends BaseEntity {
    private String text;
    private Boolean main;
    private String author;

    public String getAuthor() {
        return author;
    }

    public Quote setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Boolean getMain() {
        return main;
    }

    public Quote setMain(Boolean main) {
        this.main = main;
        return this;
    }

    public String getText() {
        return text;
    }

    public Quote setText(String text) {
        this.text = text;
        return this;
    }
}

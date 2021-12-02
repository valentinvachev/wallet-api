package bg.wallet.www.project.models.view;

public class QuoteViewModel {
    private String text;
    private String author;

    public String getAuthor() {
        return author;
    }

    public QuoteViewModel setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getText() {
        return text;
    }

    public QuoteViewModel setText(String text) {
        this.text = text;
        return this;
    }
}

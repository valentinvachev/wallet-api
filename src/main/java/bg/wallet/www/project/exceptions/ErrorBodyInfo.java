package bg.wallet.www.project.exceptions;

public class ErrorBodyInfo {
    private final String url;
    private final String message;

    public ErrorBodyInfo(String url, String message) {
        this.url = url;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }
}

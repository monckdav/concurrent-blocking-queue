package net.akcegaraz.example;

public class Mail {
    private String message;

    public Mail(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "message='" + message + '\'' +
                '}';
    }
}

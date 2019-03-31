package domain.validators;

public class ClientsException extends RuntimeException{

    public ClientsException(String message) {
        super(message);
    }

    public ClientsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientsException(Throwable cause) {
        super(cause);
    }
}
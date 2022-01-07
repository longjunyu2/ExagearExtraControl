package net.junyulong.ecc.core.errors;

public class EecException extends RuntimeException {

    public EecException() {
        super();
    }

    public EecException(String message) {
        super(message);
    }

    public EecException(String message, Throwable cause) {
        super(message, cause);
    }

    public EecException(Throwable cause) {
        super(cause);
    }

}

package id.ifg.life.handler;

import java.io.Serializable;

public class GeneralErrorException extends RuntimeException implements Serializable {

    public GeneralErrorException() {
    }

    public GeneralErrorException(String message) {
        super(message);
    }

    public GeneralErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralErrorException(Throwable cause) {
        super(cause);
    }

    public GeneralErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

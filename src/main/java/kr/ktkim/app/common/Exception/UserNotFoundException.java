package kr.ktkim.app.common.Exception;

/**
 * @author Keumtae Kim
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}

package exeception;

public class UserNotFindException extends RuntimeException {

        public UserNotFindException(String message) {
            super(message);
        }

        public UserNotFindException(String message, Throwable cause) {
            super(message, cause);
        }

    }


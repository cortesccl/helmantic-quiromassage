package es.chiromassage.helmantic.users.services.exceptions;

public class UserNotCreatedException extends Exception {
    public UserNotCreatedException() {
        super();
    }

    public UserNotCreatedException(String message) {
        super(message);
    }

}

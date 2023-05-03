package es.chiromassage.helmantic.users.services.exceptions;

public class UserDetailsAlreadyExistsException extends Exception {
    public UserDetailsAlreadyExistsException() {
        super();
    }

    public UserDetailsAlreadyExistsException(String message) {
        super(message);
    }
    
}

package es.chiromassage.helmantic.users.services.exceptions;

public class UserDetailsNotFoundException extends Exception {
    public UserDetailsNotFoundException() {
        super();
    }

    public UserDetailsNotFoundException(String message) {
        super(message);
    }

}

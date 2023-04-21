package pt.ipvc.exceptions;

public class EmailAlreadyInUseException extends Exception {

    public EmailAlreadyInUseException() {
        super("Email is already in use");
    }
}

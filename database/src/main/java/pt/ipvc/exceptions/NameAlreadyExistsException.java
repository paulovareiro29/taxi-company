package pt.ipvc.exceptions;

public class NameAlreadyExistsException extends Exception{

    public NameAlreadyExistsException() {
        super("Name already exists");
    }
}

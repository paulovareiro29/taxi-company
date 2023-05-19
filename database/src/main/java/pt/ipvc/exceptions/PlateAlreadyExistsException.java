package pt.ipvc.exceptions;

public class PlateAlreadyExistsException extends Exception{

    public PlateAlreadyExistsException() {
        super("License plate already exists");
    }
}

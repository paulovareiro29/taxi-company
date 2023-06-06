package pt.ipvc.exceptions;

public class FeedbackAlreadyExistsException extends Exception {
    public FeedbackAlreadyExistsException() {
        super("Feedback already exists");
    }

}

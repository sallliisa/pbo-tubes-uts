package exceptions;

public class InvalidTimesheetStateException extends DomainException {
    public InvalidTimesheetStateException(String message) {
        super(message);
    }
}

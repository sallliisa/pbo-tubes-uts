package exceptions;

public class InvalidContractStateException extends DomainException {
    public InvalidContractStateException(String message) {
        super(message);
    }
}

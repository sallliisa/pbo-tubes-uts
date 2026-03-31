package exceptions;

public class InvalidInvoiceStateException extends DomainException {
    public InvalidInvoiceStateException(String message) {
        super(message);
    }
}

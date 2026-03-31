import java.math.BigDecimal;
import java.time.LocalDate;

import exceptions.InvalidInvoiceStateException;

public class Invoice implements Signable {
    private final int invoiceId;
    private final String title;
    private final LocalDate invoiceDate;
    private InvoiceStatus status;
    private String notes;
    private BigDecimal amount;
    private boolean signed;
    private String signedBy;
    private LocalDate signedAt;

    public Invoice(int invoiceId, String title, LocalDate invoiceDate, String notes, BigDecimal amount) {
        this.invoiceId = invoiceId;
        this.title = Validation.requireNonBlank(title, "title");
        this.invoiceDate = Validation.requireNonNull(invoiceDate, "invoiceDate");
        this.status = InvoiceStatus.Draft;
        this.notes = Validation.requireNonBlank(notes, "notes");
        this.amount = Validation.requireNonNegative(amount, "amount");
        this.signed = false;
    }

    public void generateFromTimesheet(Timesheet timesheet, BigDecimal rate) {
        if (status != InvoiceStatus.Draft) {
            throw new InvalidInvoiceStateException("Invoice can only be generated from a draft.");
        }
        Validation.requireNonNull(timesheet, "timesheet");

        amount = timesheet.getBillableHours().multiply(Validation.requireNonNegative(rate, "rate"));
        status = InvoiceStatus.Generated;
    }

    @Override
    public void sign(String signer) {
        if (status != InvoiceStatus.Generated) {
            throw new InvalidInvoiceStateException("Only a generated invoice can be signed.");
        }
        if (signed) {
            throw new InvalidInvoiceStateException("Invoice has already been signed.");
        }

        signed = true;
        signedBy = Validation.requireNonBlank(signer, "signer");
        signedAt = LocalDate.now();
    }

    @Override
    public boolean isSigned() {
        return signed;
    }

    public void markSent() {
        if (status != InvoiceStatus.Generated) {
            throw new InvalidInvoiceStateException("Only a generated invoice can be marked as sent.");
        }
        if (!signed) {
            throw new InvalidInvoiceStateException("Invoice must be signed before it can be sent.");
        }
        status = InvoiceStatus.Sent;
    }

    public void markPaid() {
        if (status != InvoiceStatus.Sent) {
            throw new InvalidInvoiceStateException("Only a sent invoice can be marked as paid.");
        }
        status = InvoiceStatus.Paid;
    }

    public void cancel(String notes) {
        if (status == InvoiceStatus.Paid) {
            throw new InvalidInvoiceStateException("A paid invoice cannot be cancelled.");
        }
        status = InvoiceStatus.Cancelled;
        this.notes = Validation.requireNonBlank(notes, "notes");
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void printInfo() {
        System.out.println("Invoice ID: " + invoiceId);
        System.out.println("Title: " + title);
        System.out.println("Invoice Date: " + invoiceDate);
        System.out.println("Status: " + status);
        System.out.println("Amount: " + amount);
        System.out.println("Notes: " + notes);
        System.out.println("Signed: " + signed);
        System.out.println("Signed By: " + (signedBy != null ? signedBy : "-"));
        System.out.println("Signed At: " + (signedAt != null ? signedAt : "-"));
    }
}

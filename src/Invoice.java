import java.math.BigDecimal;
import java.time.LocalDate;

import exceptions.InvalidInvoiceStateException;

public class Invoice {
    private final int invoiceId;
    private final String title;
    private final LocalDate invoiceDate;
    private InvoiceStatus status;
    private String notes;
    private BigDecimal amount;

    public Invoice(int invoiceId, String title, LocalDate invoiceDate, String notes, BigDecimal amount) {
        this.invoiceId = invoiceId;
        this.title = Validation.requireNonBlank(title, "title");
        this.invoiceDate = Validation.requireNonNull(invoiceDate, "invoiceDate");
        this.status = InvoiceStatus.Draft;
        this.notes = Validation.requireNonBlank(notes, "notes");
        this.amount = Validation.requireNonNegative(amount, "amount");
    }

    public void generateFromTimesheet(Timesheet timesheet, BigDecimal rate) {
        if (status != InvoiceStatus.Draft) {
            throw new InvalidInvoiceStateException("Invoice can only be generated from a draft.");
        }
        Validation.requireNonNull(timesheet, "timesheet");

        // WARN: Timesheet not expose getStatus() method, so we cannot check if it's approved before generating invoice.
        // if (timesheet.getStatus() != TimesheetStatus.Approved) {
        //     throw new InvalidInvoiceStateException("Cannot generate invoice from unapproved timesheet.");
        // }

        amount = timesheet.getBillableHours().multiply(Validation.requireNonNegative(rate, "rate"));
        status = InvoiceStatus.Generated;
    }

    public void markSent() {
        if (status != InvoiceStatus.Generated) {
            throw new InvalidInvoiceStateException("Only a generated invoice can be marked as sent.");
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

    public void printInfo() {
        System.out.println("Invoice ID: " + invoiceId);
        System.out.println("Title: " + title);
        System.out.println("Invoice Date: " + invoiceDate);
        System.out.println("Status: " + status);
        System.out.println("Amount: " + amount);
        System.out.println("Notes: " + notes);
    }
}

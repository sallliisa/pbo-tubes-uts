import java.math.BigDecimal;
import java.time.LocalDate;

public class Invoice {
    private final int invoiceId;
    private final String title;
    private final LocalDate invoiceDate;
    private String status;
    private String notes;
    private BigDecimal amount;

    public Invoice(int invoiceId, String title, LocalDate invoiceDate, String status, String notes, BigDecimal amount) {
        this.invoiceId = invoiceId;
        this.title = Validation.requireNonBlank(title, "title");
        this.invoiceDate = Validation.requireNonNull(invoiceDate, "invoiceDate");
        this.status = Validation.requireNonBlank(status, "status");
        this.notes = Validation.requireNonBlank(notes, "notes");
        this.amount = Validation.requireNonNegative(amount, "amount");
    }

    public void generateFromTimesheet(Timesheet timesheet, BigDecimal rate) {
        Validation.requireNonNull(timesheet, "timesheet");
        amount = timesheet.getBillableHours().multiply(Validation.requireNonNegative(rate, "rate"));
        status = "Generated";
    }

    public void markSent() {
        status = "Sent";
    }

    public void markPaid() {
        status = "Paid";
    }

    public void cancel(String notes) {
        status = "Cancelled";
        this.notes = Validation.requireNonBlank(notes, "notes");
    }

    public BigDecimal getAmount() {
        return amount;
    }
}

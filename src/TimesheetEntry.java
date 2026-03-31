import java.math.BigDecimal;
import java.time.LocalDate;

public class TimesheetEntry {
    private final int entryId;
    private final LocalDate workDate;
    private BigDecimal hours;
    private boolean billable;
    private String notes;

    public TimesheetEntry(int entryId, LocalDate workDate, BigDecimal hours, boolean billable, String notes) {
        this.entryId = entryId;
        this.workDate = Validation.requireNonNull(workDate, "workDate");
        this.hours = Validation.requireNonNegative(hours, "hours");
        this.billable = billable;
        this.notes = Validation.requireNonBlank(notes, "notes");
    }

    public void updateHours(BigDecimal hours) {
        this.hours = Validation.requireNonNegative(hours, "hours");
    }

    public void markBillable() {
        billable = true;
    }

    public void markNonBillable() {
        billable = false;
    }

    public BigDecimal getHours() {
        return hours;
    }

    public int getEntryId() {
        return entryId;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public boolean isBillable() {
        return billable;
    }

    public String getNotes() {
        return notes;
    }
}

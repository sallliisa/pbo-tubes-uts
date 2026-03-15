import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Timesheet {
    private final int timesheetId;
    private final LocalDate periodStart;
    private final LocalDate periodEnd;
    private TimesheetStatus status;
    private final List<TimesheetEntry> entries = new ArrayList<>();
    private String rejectionReason;
    private Employee employee;
    private Project project;

    public Timesheet(int timesheetId, LocalDate periodStart, LocalDate periodEnd, TimesheetStatus status) {
        this.timesheetId = timesheetId;
        Validation.requireDateOrder(periodStart, periodEnd, "timesheet period");
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.status = Validation.requireNonNull(status, "status");
    }

    public void addEntry(TimesheetEntry entry) {
        Validation.requireNonNull(entry, "entry");
        entries.add(entry);
    }

    public void removeEntry(int entryId) {
        entries.removeIf(entry -> entry.getEntryId() == entryId);
    }

    public void submit() {
        if (entries.isEmpty()) {
            throw new IllegalStateException("Cannot submit an empty timesheet.");
        }
        status = TimesheetStatus.Submitted;
        rejectionReason = null;
    }

    public void approve() {
        status = TimesheetStatus.Approved;
        rejectionReason = null;
    }

    public void reject(String reason) {
        status = TimesheetStatus.Rejected;
        rejectionReason = Validation.requireNonBlank(reason, "reason");
    }

    public BigDecimal getTotalHours() {
        return entries.stream()
            .map(TimesheetEntry::getHours)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getBillableHours() {
        return entries.stream()
            .filter(TimesheetEntry::isBillable)
            .map(TimesheetEntry::getHours)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    void attachOwner(Employee employee, Project project) {
        this.employee = Validation.requireNonNull(employee, "employee");
        this.project = Validation.requireNonNull(project, "project");
    }
}

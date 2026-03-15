import java.math.BigDecimal;
import java.time.LocalDate;

public class Contract {
    private final int contractId;
    private final String title;
    private final LocalDate contractDate;
    private String status;
    private String notes;
    private final String contractNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal value;
    private String terms;

    public Contract(
        int contractId,
        String title,
        LocalDate contractDate,
        String status,
        String notes,
        String contractNumber,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal value,
        String terms
    ) {
        this.contractId = contractId;
        this.title = Validation.requireNonBlank(title, "title");
        this.contractDate = Validation.requireNonNull(contractDate, "contractDate");
        this.status = Validation.requireNonBlank(status, "status");
        this.notes = Validation.requireNonBlank(notes, "notes");
        this.contractNumber = Validation.requireNonBlank(contractNumber, "contractNumber");
        Validation.requireDateOrder(startDate, endDate, "contract dates");
        this.startDate = startDate;
        this.endDate = endDate;
        this.value = Validation.requireNonNegative(value, "value");
        this.terms = Validation.requireNonBlank(terms, "terms");
    }

    public void activate() {
        status = "Active";
    }

    public void terminate(String notes) {
        this.status = "Terminated";
        this.notes = Validation.requireNonBlank(notes, "notes");
    }

    public void renew(LocalDate newEndDate, BigDecimal newValue) {
        Validation.requireNonNull(newEndDate, "newEndDate");
        if (newEndDate.isBefore(endDate)) {
            throw new IllegalArgumentException("New end date must not be earlier than current end date.");
        }
        endDate = newEndDate;
        value = Validation.requireNonNegative(newValue, "newValue");
        status = "Renewed";
    }

    public boolean isActive(LocalDate onDate) {
        Validation.requireNonNull(onDate, "onDate");
        return "Active".equalsIgnoreCase(status)
            && !onDate.isBefore(startDate)
            && !onDate.isAfter(endDate);
    }

    public BigDecimal getContractValue() {
        return value;
    }
}

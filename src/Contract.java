import java.math.BigDecimal;
import java.time.LocalDate;

import exceptions.InvalidContractStateException;

public class Contract implements Signable {
    private final int contractId;
    private final String title;
    private final LocalDate contractDate;
    private ContractStatus status;
    private String notes;
    private final String contractNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal value;
    private String terms;
    private boolean signed;
    private String signedBy;
    private LocalDate signedAt;

    public Contract(
        int contractId,
        String title,
        LocalDate contractDate,
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
        this.status = ContractStatus.Draft;
        this.notes = Validation.requireNonBlank(notes, "notes");
        this.contractNumber = Validation.requireNonBlank(contractNumber, "contractNumber");
        Validation.requireDateOrder(startDate, endDate, "contract dates");
        this.startDate = startDate;
        this.endDate = endDate;
        this.value = Validation.requireNonNegative(value, "value");
        this.terms = Validation.requireNonBlank(terms, "terms");
        this.signed = false;
    }

    @Override
    public void sign(String signer) {
        if (status != ContractStatus.Draft) {
            throw new InvalidContractStateException("Only a draft contract can be signed.");
        }

        if (signed) {
            throw new InvalidContractStateException("Contract has already been signed.");
        }

        signed = true;
        signedBy = Validation.requireNonBlank(signer, "signer");
        signedAt = LocalDate.now();
        status = ContractStatus.Active;
    }

    @Override
    public boolean isSigned() {
        return signed;
    }

    public void terminate(String notes) {
        if (status != ContractStatus.Active) {
            throw new InvalidContractStateException("Only an active contract can be terminated.");
        }

        this.status = ContractStatus.Terminated;
        this.notes = Validation.requireNonBlank(notes, "notes");
    }

    public void renew(LocalDate newEndDate, BigDecimal newValue) {
        if (status != ContractStatus.Active) {
            throw new InvalidContractStateException("Only an active contract can be renewed.");
        }
        Validation.requireNonNull(newEndDate, "newEndDate");
        if (newEndDate.isBefore(endDate)) {
            throw new IllegalArgumentException("New end date must not be earlier than current end date.");
        }

        endDate = newEndDate;
        value = Validation.requireNonNegative(newValue, "newValue");
        status = ContractStatus.Renewed;
    }

    public void renew(LocalDate newEndDate) {
        renew(newEndDate, value);
    }

    public boolean isActive(LocalDate onDate) {
        Validation.requireNonNull(onDate, "onDate");
        return status == ContractStatus.Active
            && !onDate.isBefore(startDate)
            && !onDate.isAfter(endDate);
    }

    public BigDecimal getContractValue() {
        return value;
    }

    public void printInfo() {
        System.out.println("Contract ID: " + contractId);
        System.out.println("Title: " + title);
        System.out.println("Contract Date: " + contractDate);
        System.out.println("Status: " + status);
        System.out.println("Contract Number: " + contractNumber);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
        System.out.println("Value: " + value);
        System.out.println("Terms: " + terms);
        System.out.println("Notes: " + notes);
        System.out.println("Signed: " + signed);
        System.out.println("Signed By: " + (signedBy != null ? signedBy : "-"));
        System.out.println("Signed At: " + (signedAt != null ? signedAt : "-"));
    }
}

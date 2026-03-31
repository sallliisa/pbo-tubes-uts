import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ContractEmployee extends Employee {
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;

    public ContractEmployee(
        int employeeId,
        String firstName,
        String lastName,
        String email,
        LocalDate hireDate,
        BigDecimal salary,
        LocalDate contractStartDate,
        LocalDate contractEndDate
    ) {
        super(employeeId, firstName, lastName, email, hireDate, salary);
        this.contractStartDate = Validation.requireNonNull(contractStartDate, "contractStartDate");
        this.contractEndDate = Validation.requireNonNull(contractEndDate, "contractEndDate");
        Validation.requireDateOrder(contractStartDate, contractEndDate, "contract dates");
    }

    public int getContractDuration() {
        return (int) ChronoUnit.DAYS.between(contractStartDate, contractEndDate);
    }

    public void extendContract(LocalDate newEndDate) {
        Validation.requireNonNull(newEndDate, "newEndDate");
        if (newEndDate.isBefore(contractEndDate)) {
            throw new IllegalArgumentException("New end date must not be earlier than current end date.");
        }
        contractEndDate = newEndDate;
    }

    public boolean isContractActive(LocalDate onDate) {
        Validation.requireNonNull(onDate, "onDate");
        return !onDate.isBefore(contractStartDate) && !onDate.isAfter(contractEndDate);
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Contract Start Date: " + contractStartDate);
        System.out.println("Contract End Date: " + contractEndDate);
    }

    @Override
    public String getEmployeeType() {
        return "Contract Employee";
    }

    @Override
    public BigDecimal calculateCompensation() {
        return getSalary();
    }
}

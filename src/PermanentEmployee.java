import java.math.BigDecimal;
import java.time.LocalDate;

public class PermanentEmployee extends Employee {
    private String benefitPlan;
    private int annualLeaveQuota;

    public PermanentEmployee(
        int employeeId,
        String firstName,
        String lastName,
        String email,
        LocalDate hireDate,
        BigDecimal salary,
        String benefitPlan,
        int annualLeaveQuota
    ) {
        super(employeeId, firstName, lastName, email, hireDate, salary);
        this.benefitPlan = Validation.requireNonBlank(benefitPlan, "benefitPlan");
        this.annualLeaveQuota = Validation.requireNonNegative(annualLeaveQuota, "annualLeaveQuota");
    }

    public String getBenefitPlan() {
        return benefitPlan;
    }

    public void updateBenefitPlan(String benefitPlan) {
        this.benefitPlan = Validation.requireNonBlank(benefitPlan, "benefitPlan");
    }

    public int getAnnualLeaveQuota() {
        return annualLeaveQuota;
    }

    public boolean requestLeave(int days) {
        Validation.requireNonNegative(days, "days");
        if (days > annualLeaveQuota) {
            return false;
        }
        annualLeaveQuota -= days;
        return true;
    }

    @Override
    public BigDecimal calculateCompensation() {
        return getSalary();
    }
}

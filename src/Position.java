import java.math.BigDecimal;

public class Position {
    private final int positionId;
    private final String title;
    private final String level;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String description;

    public Position(
        int positionId,
        String title,
        String level,
        BigDecimal minSalary,
        BigDecimal maxSalary,
        String description
    ) {
        this.positionId = positionId;
        this.title = Validation.requireNonBlank(title, "title");
        this.level = Validation.requireNonBlank(level, "level");
        this.minSalary = Validation.requireNonNegative(minSalary, "minSalary");
        this.maxSalary = Validation.requireNonNegative(maxSalary, "maxSalary");
        Validation.requireSalaryRange(this.minSalary, this.maxSalary);
        this.description = Validation.requireNonBlank(description, "description");
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = Validation.requireNonBlank(description, "description");
    }

    public boolean isSalaryInRange(BigDecimal salary) {
        BigDecimal checkedSalary = Validation.requireNonNegative(salary, "salary");
        return checkedSalary.compareTo(minSalary) >= 0 && checkedSalary.compareTo(maxSalary) <= 0;
    }

    public void updateSalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        BigDecimal checkedMinSalary = Validation.requireNonNegative(minSalary, "minSalary");
        BigDecimal checkedMaxSalary = Validation.requireNonNegative(maxSalary, "maxSalary");
        Validation.requireSalaryRange(checkedMinSalary, checkedMaxSalary);
        this.minSalary = checkedMinSalary;
        this.maxSalary = checkedMaxSalary;
    }

    public void printInfo() {
        System.out.println("Position ID: " + positionId);
        System.out.println("Title: " + title);
        System.out.println("Level: " + level);
        System.out.println("Min Salary: " + minSalary);
        System.out.println("Max Salary: " + maxSalary);
        System.out.println("Description: " + description);
    }
}

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Employee {
    private static final AtomicInteger TIMESHEET_SEQUENCE = new AtomicInteger(1);

    private final int employeeId;
    private final String firstName;
    private final String lastName;
    private String email;
    private final LocalDate hireDate;
    private BigDecimal salary;
    private Position position;
    private Department department;
    private final List<ProjectAssignment> assignments = new ArrayList<>();
    private final List<Timesheet> timesheets = new ArrayList<>();

    public Employee(
        int employeeId,
        String firstName,
        String lastName,
        String email,
        LocalDate hireDate,
        BigDecimal salary
    ) {
        this.employeeId = employeeId;
        this.firstName = Validation.requireNonBlank(firstName, "firstName");
        this.lastName = Validation.requireNonBlank(lastName, "lastName");
        this.email = Validation.requireNonBlank(email, "email");
        this.hireDate = Validation.requireNonNull(hireDate, "hireDate");
        this.salary = Validation.requireNonNegative(salary, "salary");
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Validation.requireNonBlank(email, "email");
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void updateSalary(BigDecimal newSalary) {
        if (position != null && !position.isSalaryInRange(newSalary)) {
            throw new IllegalArgumentException(
                "New salary is outside the assigned position range"
            );
        }

        this.salary = newSalary;
    }

    public void assignPosition(Position position) {
        Validation.requireNonNull(position, "position");
        if (!position.isSalaryInRange(salary)) {
            throw new IllegalArgumentException("Salary is outside the position range.");
        }
        this.position = position;
    }

    public Timesheet createTimesheet(Project project, LocalDate periodStart, LocalDate periodEnd) {
        Validation.requireNonNull(project, "project");
        Timesheet timesheet = new Timesheet(
            TIMESHEET_SEQUENCE.getAndIncrement(),
            periodStart,
            periodEnd,
            TimesheetStatus.Draft
        );
        timesheet.attachOwner(this, project);
        timesheets.add(timesheet);
        project.addTimesheetRecord(timesheet);
        return timesheet;
    }

    public void printInfo() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + getFullName());
        System.out.println("Email: " + email);
        System.out.println("Hire Date: " + hireDate);
        System.out.println("Salary: " + salary);
        System.out.println("Position: " + (position != null ? position.getTitle() : "-"));
        System.out.println("Department: " + (department != null ? department.getName() : "-"));
        System.out.println("Assignments: " + assignments.size());
        System.out.println("Timesheets: " + timesheets.size());
    }

    public abstract String getEmployeeType();

    public abstract BigDecimal calculateCompensation();

    void addAssignment(ProjectAssignment assignment) {
        if (!assignments.contains(assignment)) {
            assignments.add(assignment);
        }
    }

    void setDepartment(Department department) {
        this.department = department;
    }
}

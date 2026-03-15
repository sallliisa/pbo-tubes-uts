import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProjectAssignment {
    private LocalDate assignmentStartDate;
    private LocalDate assignmentEndDate;
    private String roleOnProject;
    private Employee employee;
    private Project project;

    public ProjectAssignment(LocalDate assignmentStartDate, LocalDate assignmentEndDate, String roleOnProject) {
        Validation.requireDateOrder(assignmentStartDate, assignmentEndDate, "assignment dates");
        this.assignmentStartDate = assignmentStartDate;
        this.assignmentEndDate = assignmentEndDate;
        this.roleOnProject = Validation.requireNonBlank(roleOnProject, "roleOnProject");
    }

    public void updateRole(String roleOnProject) {
        this.roleOnProject = Validation.requireNonBlank(roleOnProject, "roleOnProject");
    }

    public void updateAssignmentPeriod(LocalDate startDate, LocalDate endDate) {
        Validation.requireDateOrder(startDate, endDate, "assignment dates");
        assignmentStartDate = startDate;
        assignmentEndDate = endDate;
    }

    public boolean isActive(LocalDate onDate) {
        Validation.requireNonNull(onDate, "onDate");
        return !onDate.isBefore(assignmentStartDate) && !onDate.isAfter(assignmentEndDate);
    }

    public int getAssignmentDuration() {
        return (int) ChronoUnit.DAYS.between(assignmentStartDate, assignmentEndDate);
    }

    void attach(Employee employee, Project project) {
        this.employee = Validation.requireNonNull(employee, "employee");
        this.project = Validation.requireNonNull(project, "project");
    }
}

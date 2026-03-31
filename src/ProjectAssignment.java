import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import exceptions.AssignmentException;

public class ProjectAssignment {
    private LocalDate assignmentStartDate;
    private LocalDate assignmentEndDate;
    private String roleOnProject;
    private Employee employee;
    private Project project;

    public ProjectAssignment(
        LocalDate assignmentStartDate,
        LocalDate assignmentEndDate,
        String roleOnProject,
        Project project
    ) {
        Validation.requireDateOrder(assignmentStartDate, assignmentEndDate, "assignment dates");
        this.assignmentStartDate = assignmentStartDate;
        this.assignmentEndDate = assignmentEndDate;
        this.roleOnProject = Validation.requireNonBlank(roleOnProject, "roleOnProject");
        this.project = Validation.requireNonNull(project, "project");
    }

    public ProjectAssignment(
        LocalDate assignmentStartDate,
        LocalDate assignmentEndDate,
        String roleOnProject,
        Employee employee,
        Project project
    ) {
        this(assignmentStartDate, assignmentEndDate, roleOnProject, project);
        attachEmployee(employee);
    }

    public void updateRole(String roleOnProject) {
        this.roleOnProject = Validation.requireNonBlank(roleOnProject, "roleOnProject");
    }

    public void updateAssignmentPeriod(LocalDate startDate, LocalDate endDate) {
        Validation.requireDateOrder(startDate, endDate, "assignment dates");
        if (endDate.isBefore(LocalDate.now())) {
            throw new AssignmentException(
                "Cannot update an assignment that has already ended"
            );
        }

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

    void attachEmployee(Employee employee) {
        this.employee = Validation.requireNonNull(employee, "employee");
    }

    public void printInfo() {
        System.out.println("Assignment Start Date: " + assignmentStartDate);
        System.out.println("Assignment End Date: " + assignmentEndDate);
        System.out.println("Role on Project: " + roleOnProject);
        System.out.println("Employee: " + (employee != null ? employee.getFullName() : "-"));
        System.out.println("Project: " + (project != null ? project.getName() : "-"));
    }
}

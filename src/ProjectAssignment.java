import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import exceptions.AssignmentException;

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

    public ProjectAssignment(
        LocalDate assignmentStartDate,
        LocalDate assignmentEndDate,
        String roleOnProject,
        Employee employee,
        Project project
    ) {
        this(assignmentStartDate, assignmentEndDate, roleOnProject);
        attach(employee, project);
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

    void attach(Employee employee, Project project) {
        this.employee = Validation.requireNonNull(employee, "employee");
        this.project = Validation.requireNonNull(project, "project");
    }

    public void printInfo() {
        System.out.println("Assignment Start Date: " + assignmentStartDate);
        System.out.println("Assignment End Date: " + assignmentEndDate);
        System.out.println("Role on Project: " + roleOnProject);
        System.out.println("Employee: " + (employee != null ? employee.getFullName() : "-"));
        System.out.println("Project: " + (project != null ? project.getName() : "-"));
    }
}

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private final int projectId;
    private final String name;
    private final String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;
    private BigDecimal budget;
    private Client client;
    private final List<ProjectAssignment> staffing = new ArrayList<>();
    private final List<Contract> contracts = new ArrayList<>();
    private final List<Invoice> invoices = new ArrayList<>();
    private final List<Timesheet> timesheets = new ArrayList<>();

    public Project(
        int projectId,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        ProjectStatus status,
        BigDecimal budget
    ) {
        this.projectId = projectId;
        this.name = Validation.requireNonBlank(name, "name");
        this.description = Validation.requireNonBlank(description, "description");
        Validation.requireDateOrder(startDate, endDate, "project dates");
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = Validation.requireNonNull(status, "status");
        this.budget = Validation.requireNonNegative(budget, "budget");
    }

    public int getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void updateSchedule(LocalDate startDate, LocalDate endDate) {
        Validation.requireDateOrder(startDate, endDate, "project dates");
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateBudget(BigDecimal budget) {
        this.budget = Validation.requireNonNegative(budget, "budget");
    }

    public void changeStatus(ProjectStatus status) {
        this.status = Validation.requireNonNull(status, "status");
    }

    public ProjectAssignment assignEmployee(Employee employee, String role) {
        return assignEmployee(
            employee,
            role,
            LocalDate.now(),
            this.endDate
        );
    }

    public ProjectAssignment assignEmployee(
        Employee employee,
        String roleOnProject,
        LocalDate assignmentStartDate,
        LocalDate assignmentEndDate
    ) {
        Validation.requireNonNull(employee, "employee");
        ProjectAssignment assignment = new ProjectAssignment(
            assignmentStartDate,
            assignmentEndDate,
            roleOnProject,
            employee,
            this
        );
        staffing.add(assignment);
        employee.addAssignment(assignment);
        return assignment;
    }

    public void addContract(Contract contract) {
        Validation.requireNonNull(contract, "contract");
        if (!contracts.contains(contract)) {
            contracts.add(contract);
        }
    }

    public Contract addContract(
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
        Contract contract = new Contract(
            contractId,
            title,
            contractDate,
            notes,
            contractNumber,
            startDate,
            endDate,
            value,
            terms
        );
        addContract(contract);
        return contract;
    }

    public void addInvoice(Invoice invoice) {
        Validation.requireNonNull(invoice, "invoice");
        if (!invoices.contains(invoice)) {
            invoices.add(invoice);
        }
    }

    public BigDecimal getTotalBilledAmount() {
        return invoices.stream()
            .map(Invoice::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    void setClient(Client client) {
        this.client = client;
    }

    void addTimesheetRecord(Timesheet timesheet) {
        if (!timesheets.contains(timesheet)) {
            timesheets.add(timesheet);
        }
    }

    public void printInfo() {
        System.out.println("Project ID: " + projectId);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
        System.out.println("Status: " + status);
        System.out.println("Budget: " + budget);
        System.out.println("Client: " + (client != null ? client.getName() : "-"));
        System.out.println("Staffing Count: " + staffing.size());
        System.out.println("Contracts: " + contracts.size());
        System.out.println("Invoices: " + invoices.size());
        System.out.println("Timesheets: " + timesheets.size());
    }
}

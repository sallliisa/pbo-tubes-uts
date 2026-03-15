import java.util.ArrayList;
import java.util.List;

public class Department {
    private final int departmentId;
    private String name;
    private final List<Employee> employees = new ArrayList<>();
    private final List<Position> positions = new ArrayList<>();
    private Employee manager;

    public Department(int departmentId, String name) {
        this.departmentId = departmentId;
        this.name = Validation.requireNonBlank(name, "name");
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Validation.requireNonBlank(name, "name");
    }

    public void addEmployee(Employee employee) {
        Validation.requireNonNull(employee, "employee");
        if (!employees.contains(employee)) {
            employees.add(employee);
            employee.setDepartment(this);
        }
    }

    public void removeEmployee(int employeeId) {
        Employee employee = employees.stream()
            .filter(item -> item.getEmployeeId() == employeeId)
            .findFirst()
            .orElse(null);
        if (employee != null) {
            employees.remove(employee);
            employee.setDepartment(null);
            if (employee.equals(manager)) {
                manager = null;
            }
        }
    }

    public void assignManager(Employee manager) {
        Validation.requireNonNull(manager, "manager");
        addEmployee(manager);
        this.manager = manager;
    }

    public void addPosition(Position position) {
        Validation.requireNonNull(position, "position");
        if (!positions.contains(position)) {
            positions.add(position);
        }
    }

    public int getEmployeeCount() {
        return employees.size();
    }
}

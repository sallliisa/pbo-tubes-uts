import java.math.BigDecimal;
import java.time.LocalDate;

import exceptions.DomainException;

public class App {
    public static void main(String[] args) {
        try {
            Department consulting = new Department(1, "Consulting");

            Position seniorConsultant = new Position(
                1,
                "Senior Consultant",
                "Senior",
                new BigDecimal("6000.00"),
                new BigDecimal("12000.00"),
                "Leads delivery workstreams and mentors consultants."
            );
            consulting.addPosition(seniorConsultant);

            PermanentEmployee manager = new PermanentEmployee(
                1001,
                "Alicia",
                "Tan",
                "alicia.tan@example.com",
                LocalDate.of(2022, 1, 10),
                new BigDecimal("8500.00"),
                "Gold",
                18
            );

            manager.assignPosition(seniorConsultant);
            consulting.addEmployee(manager);
            consulting.assignManager(manager);

            ContractEmployee consultant = new ContractEmployee(
                2001,
                "John",
                "Doe",
                "john.doe@example.com",
                LocalDate.of(2025, 1, 1),
                new BigDecimal("7000.00"),
                LocalDate.of(2026, 1, 31),
                LocalDate.of(2026, 12, 1),
                new BigDecimal("50.00")
            );

            consultant.assignPosition(seniorConsultant);
            consulting.addEmployee(consultant);

            Client client = new Client(
                501,
                "Northwind Holdings",
                "Retail",
                "Marcus Lee",
                "marcus.lee@northwind.example",
                "+62-21-555-0101"
            );

            Project transformation = new Project(
                7001,
                "ERP Transformation",
                "Replace legacy ERP and streamline finance operations.",
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 6, 30),
                ProjectStatus.Active,
                new BigDecimal("150000.00")
            );

            client.addProject(transformation);

            transformation.assignEmployee(
                manager,
                "Project Manager",
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 6, 30)
            );

            // Example of method overloading
            transformation.assignEmployee(consultant, "Consultant");

            Timesheet timesheet = manager.createTimesheet(
                transformation,
                LocalDate.of(2026, 3, 1),
                LocalDate.of(2026, 3, 15)
            );

            timesheet.addEntry(new TimesheetEntry(
                1,
                LocalDate.of(2026, 3, 2),
                new BigDecimal("8.0"),
                true,
                "Workshop facilitation"
            ));

            timesheet.submit();

            System.out.println("Department: " + consulting.getName());
            System.out.println("Manager: " + manager.getFullName());
            System.out.println("Employees: " + consulting.getEmployeeCount());
            System.out.println("Timesheet hours: " + timesheet.getTotalHours());

            // Example of method overriding
            System.out.println("Manager compensation: " + manager.calculateCompensation());
            System.out.println("Consultant compensation: " + consultant.calculateCompensation());

        } catch (DomainException e) {
            System.out.println("[DOMAIN ERROR] " + e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println("[VALIDATION ERROR] " + e.getMessage());

        } catch (Exception e) {
            System.out.println("[UNEXPECTED ERROR] " + e.getMessage());
            e.printStackTrace();
        }
    }
}

import java.math.BigDecimal;
import java.time.LocalDate;

import exceptions.DomainException;

public class App {
    public static void main(String[] args) {
        try {
            printSection("Department and Position Setup");
            System.out.println("Membuat departemen dan posisi agar data organisasi bisa dipakai di proses berikutnya");
            Department consulting = new Department(1, "Consulting");
            consulting.setName("Business Consulting");

            Position seniorConsultant = new Position(
                    1,
                    "Senior Consultant",
                    "Senior",
                    new BigDecimal("6000.00"),
                    new BigDecimal("12000.00"),
                    "Leads delivery workstreams and mentors consultants");
            seniorConsultant.setDescription(
                    "Responsible for delivery quality, client communication, and team coordination");
            seniorConsultant.updateSalaryRange(
                    new BigDecimal("6000.00"),
                    new BigDecimal("13000.00"));
            consulting.addPosition(seniorConsultant);
            printPadding();
            seniorConsultant.printInfo();
            printPadding();
            System.out.println("Data departemen dan posisi sudah dibuat");

            printSection("Employee Management");
            System.out.println("Menambahkan pegawai, memperbarui profil, lalu menghubungkan pegawai ke departemen");
            String employeeFailureReason = null;

            PermanentEmployee manager = new PermanentEmployee(
                    1001,
                    "Alicia",
                    "Tan",
                    "alicia.tan@example.com",
                    LocalDate.of(2022, 1, 10),
                    new BigDecimal("8500.00"),
                    "Gold",
                    18);
            manager.assignPosition(seniorConsultant);
            manager.setEmail("alicia.tan@scroogemckinsey.example");
            manager.updateBenefitPlan("Platinum");
            try {
                System.out.println("[FAIL] Memperbarui gaji manager ke 15000.00");
                manager.updateSalary(new BigDecimal("15000.00"));
            } catch (IllegalArgumentException e) {
                employeeFailureReason = e.getMessage();
                System.out.println("Pembaruan gaji gagal. Menggunakan gaji yang sesuai rentang posisi");
                manager.updateSalary(new BigDecimal("9000.00"));
            }
            manager.requestLeave(3);
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
                    LocalDate.of(2026, 12, 1));
            consultant.assignPosition(seniorConsultant);
            consultant.setEmail("john.doe@contractor.example");
            consultant.extendContract(LocalDate.of(2027, 3, 31));
            consulting.addEmployee(consultant);

            PermanentEmployee analyst = new PermanentEmployee(
                    1002,
                    "Rina",
                    "Wijaya",
                    "rina.wijaya@example.com",
                    LocalDate.of(2025, 8, 1),
                    new BigDecimal("6500.00"),
                    "Silver",
                    12);
            analyst.assignPosition(seniorConsultant);
            consulting.addEmployee(analyst);
            consulting.removeEmployee(analyst.getEmployeeId());

            printPadding();
            manager.printInfo();
            printPadding();
            consultant.printInfo();
            printPadding();
            consulting.printInfo();
            printPadding();
            System.out.println("Consultant contract duration (days): " + consultant.getContractDuration());
            System.out.println(
                    "Consultant active on 2026-06-01: "
                            + consultant.isContractActive(LocalDate.of(2026, 6, 1)));
            System.out.println("Manager compensation: " + manager.calculateCompensation());
            System.out.println("Consultant compensation: " + consultant.calculateCompensation());
            printPadding();
            System.out.println("Data pegawai sudah dibuat dan relasinya ke departemen sudah tercatat");
            if (employeeFailureReason != null) {
                System.out.println("Kegagalan di bagian ini terjadi karena: " + employeeFailureReason);
            }

            printSection("Client and Project Management");
            System.out.println("Membuat data klien, membuat proyek, lalu menambahkan assignment pegawai ke proyek");
            Client client = new Client(
                    501,
                    "Northwind Holdings",
                    "Retail",
                    "Marcus Lee",
                    "marcus.lee@northwind.example",
                    "+62-21-555-0101");
            client.updateContact(
                    "Marcus Lee",
                    "marcus.lee@northwindholdings.example",
                    "+62-21-555-0199");

            Project transformation = new Project(
                    7001,
                    "ERP Transformation",
                    "Replace legacy ERP and streamline finance operations",
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 6, 30),
                    ProjectStatus.Active,
                    new BigDecimal("150000.00"));
            transformation.updateSchedule(
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 7, 31));
            transformation.updateBudget(new BigDecimal("175000.00"));
            transformation.changeStatus(ProjectStatus.Planned);
            transformation.changeStatus(ProjectStatus.Active);
            client.addProject(transformation);

            ProjectAssignment managerAssignment = transformation.assignEmployee(
                    manager,
                    "Project Manager",
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 7, 31));
            ProjectAssignment consultantAssignment = transformation.assignEmployee(
                    consultant,
                    "Consultant");
            managerAssignment.updateRole("Engagement Manager");
            managerAssignment.updateAssignmentPeriod(
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 8, 15));

            printPadding();
            client.printInfo();
            printPadding();
            transformation.printInfo();
            printPadding();
            managerAssignment.printInfo();
            printPadding();
            consultantAssignment.printInfo();
            printPadding();
            System.out.println(
                    "Manager assignment active on 2026-04-01: "
                            + managerAssignment.isActive(LocalDate.of(2026, 4, 1)));
            System.out.println("Manager assignment duration (days): " + managerAssignment.getAssignmentDuration());
            System.out.println("Client active projects: " + client.getActiveProjects().size());
            printPadding();
            System.out.println("Data klien, proyek, dan assignment sudah dibuat");

            printSection("Timesheet Workflow");
            System.out.println("Membuat timesheet yang ditolak dan timesheet yang disetujui");
            String timesheetFailureReason = null;

            Timesheet rejectedTimesheet = manager.createTimesheet(
                    transformation,
                    LocalDate.of(2026, 3, 1),
                    LocalDate.of(2026, 3, 15));
            rejectedTimesheet.addEntry(
                    1,
                    LocalDate.of(2026, 3, 2),
                    new BigDecimal("8.0"),
                    true,
                    "Kickoff workshop facilitation");
            rejectedTimesheet.addEntry(
                    2,
                    LocalDate.of(2026, 3, 3),
                    new BigDecimal("2.0"),
                    false,
                    "Internal planning");
            rejectedTimesheet.removeEntry(2);
            rejectedTimesheet.submit();
            rejectedTimesheet.reject("Clarify workshop outcomes before approval");
            printPadding();
            rejectedTimesheet.printInfo();
            printPadding();
            System.out.println("Timesheet pertama berhasil diproses sampai status rejected");
            printPadding();

            System.out.println("Membuat timesheet kedua yang akan dipakai untuk proses billing");
            Timesheet approvedTimesheet = consultant.createTimesheet(
                    transformation,
                    LocalDate.of(2026, 3, 16),
                    LocalDate.of(2026, 3, 31));
            TimesheetEntry discoveryEntry = new TimesheetEntry(
                    3,
                    LocalDate.of(2026, 3, 18),
                    new BigDecimal("7.5"),
                    false,
                    "Discovery interviews");
            discoveryEntry.markBillable();
            discoveryEntry.updateHours(new BigDecimal("8.0"));
            approvedTimesheet.addEntry(discoveryEntry);
            approvedTimesheet.addEntry(
                    4,
                    LocalDate.of(2026, 3, 19),
                    new BigDecimal("6.5"),
                    true,
                    "Process mapping");
            try {
                System.out.println("[FAIL] Menyetujui timesheet kedua sebelum submit");
                approvedTimesheet.approve();
            } catch (DomainException e) {
                timesheetFailureReason = e.getMessage();
                System.out.println("Persetujuan timesheet gagal. Timesheet akan disubmit terlebih dahulu");
            }
            approvedTimesheet.submit();
            approvedTimesheet.approve();
            printPadding();
            approvedTimesheet.printInfo();
            printPadding();
            System.out.println("Approved timesheet hours: " + approvedTimesheet.getTotalHours());
            System.out.println("Approved billable hours: " + approvedTimesheet.getBillableHours());
            printPadding();
            System.out.println(
                    "Timesheet kedua sudah approved. Data ini sekarang bisa dipakai untuk kontrak dan invoice");
            if (timesheetFailureReason != null) {
                System.out.println("Kegagalan di bagian ini terjadi karena: " + timesheetFailureReason);
            }

            printSection("Contract Workflow");
            System.out.println("Membuat kontrak aktif, memperbarui nilainya, dan mengakhiri satu kontrak tambahan");
            Contract masterServicesAgreement = transformation.addContract(
                    9001,
                    "Master Services Agreement",
                    LocalDate.of(2025, 12, 20),
                    "Awaiting signature package",
                    "MSA-2026-001",
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 12, 31),
                    new BigDecimal("180000.00"),
                    "Monthly billing based on approved billable work");
            masterServicesAgreement.activate();
            System.out.println(
                    "MSA active on 2026-05-01 before renewal: "
                            + masterServicesAgreement.isActive(LocalDate.of(2026, 5, 1)));
            masterServicesAgreement.renew(
                    LocalDate.of(2027, 6, 30),
                    new BigDecimal("220000.00"));
            printPadding();
            masterServicesAgreement.printInfo();
            printPadding();
            System.out.println("MSA value: " + masterServicesAgreement.getContractValue());
            printPadding();
            System.out.println("Kontrak utama sudah diperpanjang");

            System.out.println("Menambahkan change order terpisah untuk proses terminasi kontrak");
            Contract changeOrder = new Contract(
                    9002,
                    "Change Order",
                    LocalDate.of(2026, 2, 1),
                    "Additional reporting scope",
                    "CO-2026-003",
                    LocalDate.of(2026, 2, 15),
                    LocalDate.of(2026, 5, 31),
                    new BigDecimal("25000.00"),
                    "Covers executive dashboard enhancement");
            transformation.addContract(changeOrder);
            changeOrder.activate();
            changeOrder.terminate("Scope absorbed into renewed master agreement");
            printPadding();
            changeOrder.printInfo();
            printPadding();
            System.out.println("Kontrak tambahan sudah diterminasi");

            printSection("Invoice Workflow");
            System.out.println("Membuat invoice dari timesheet yang approved");
            Invoice invoice = new Invoice(
                    8101,
                    "March Billing",
                    LocalDate.of(2026, 4, 1),
                    "Prepared after approved consultant timesheet",
                    BigDecimal.ZERO);
            transformation.addInvoice(invoice);
            invoice.generateFromTimesheet(approvedTimesheet, new BigDecimal("150.00"));
            invoice.setNotes("Generated from approved March consultant timesheet");
            invoice.markSent();
            invoice.markPaid();
            printPadding();
            invoice.printInfo();
            printPadding();
            System.out.println("Total billed amount for project: " + transformation.getTotalBilledAmount());
            printPadding();
            transformation.printInfo();
            printPadding();
            System.out.println("Invoice berhasil dibuat, dikirim, dan ditandai lunas");
        } catch (DomainException e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printSection(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }

    private static void printPadding() {
        System.out.println();
    }
}

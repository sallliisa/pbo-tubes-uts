import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Client {
    private final int clientId;
    private final String name;
    private final String industry;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private final List<Project> projects = new ArrayList<>();

    public Client(
        int clientId,
        String name,
        String industry,
        String contactName,
        String contactEmail,
        String contactPhone
    ) {
        this.clientId = clientId;
        this.name = Validation.requireNonBlank(name, "name");
        this.industry = Validation.requireNonBlank(industry, "industry");
        updateContact(contactName, contactEmail, contactPhone);
    }

    public int getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public void updateContact(String contactName, String contactEmail, String contactPhone) {
        this.contactName = Validation.requireNonBlank(contactName, "contactName");
        this.contactEmail = Validation.requireNonBlank(contactEmail, "contactEmail");
        this.contactPhone = Validation.requireNonBlank(contactPhone, "contactPhone");
    }

    public void addProject(Project project) {
        Validation.requireNonNull(project, "project");
        if (!projects.contains(project)) {
            projects.add(project);
            project.setClient(this);
        }
    }

    public List<Project> getActiveProjects() {
        return projects.stream()
            .filter(project -> project.getStatus() == ProjectStatus.Active)
            .collect(Collectors.toUnmodifiableList());
    }

    public void printInfo() {
        System.out.println("Client ID: " + clientId);
        System.out.println("Name: " + name);
        System.out.println("Industry: " + industry);
        System.out.println("Contact Name: " + contactName);
        System.out.println("Contact Email: " + contactEmail);
        System.out.println("Contact Phone: " + contactPhone);
        System.out.println("Projects: " + projects.size());
    }
}


import java.util.ArrayList;
import java.util.List;

interface Employee {
    void showDetails();
}

class Developer implements Employee {
    private String name;

    public Developer(String name) {
        this.name = name;
    }

    @Override
    public void showDetails() {
        System.out.println("Developer: " + name);
    }
}

class Manager implements Employee {
    private String name;

    public Manager(String name) {
        this.name = name;
    }

    @Override
    public void showDetails() {
        System.out.println("Manager: " + name);
    }
}

class Department implements Employee {
    private String name;
    private List<Employee> employees = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    @Override
    public void showDetails() {
        System.out.println("Department: " + name);
        System.out.println("Employees in " + name + ":");
        for (Employee employee : employees) {
            employee.showDetails();
        }
    }
}

public class Main {
    public static void main(String[] args) {

        Employee developer1 = new Developer("John Doe");
        Employee developer2 = new Developer("Jane Doe");
        Employee manager = new Manager("Alice Smith");

        Department developmentDepartment = new Department("Development");
        developmentDepartment.addEmployee(developer1);
        developmentDepartment.addEmployee(developer2);
        developmentDepartment.addEmployee(manager);

        Employee hrManager = new Manager("Bob Johnson");

        Department hrDepartment = new Department("HR");
        hrDepartment.addEmployee(hrManager);

        Department company = new Department("XYZ Corp");
        company.addEmployee(developmentDepartment);
        company.addEmployee(hrDepartment);

        company.showDetails();
    }
}

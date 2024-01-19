import java.util.ArrayList;
import java.util.List;

class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee: " + name + " (Salary: $" + salary + ")";
    }
}

class Manager extends Employee {
    private List<Employee> subordinates;

    public Manager(String name, double salary) {
        super(name, salary);
        this.subordinates = new ArrayList<>();
    }

    public void addSubordinate(Employee subordinate) {
        subordinates.add(subordinate);
    }

    public void removeSubordinate(Employee subordinate) {
        subordinates.remove(subordinate);
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    @Override
    public String toString() {
        return "Manager: " + getName() + " (Salary: $" + getSalary() + ")";
    }
}

class Department {
    private String name;
    private List<Employee> employees;

    public Department(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    public void displayEmployees() {
        System.out.println("Employees in " + name + " Department:");
        for (Employee employee : employees) {
            System.out.println(employee.toString());
        }
    }

    public void calculateTotalSalaries() {
        double totalSalaries = employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
        System.out.println("Total Salaries in " + name + " Department: $" + totalSalaries);
    }

    public void promoteEmployee(Employee employee, double promotionAmount) {
        employee.setSalary(employee.getSalary() + promotionAmount);
        System.out.println(employee.getName() + " has been promoted with a salary increase of $" + promotionAmount);
    }
}

public class Main {
    public static void main(String[] args) {

        Employee employee1 = new Employee("John Doe", 50000);
        Employee employee2 = new Employee("Jane Smith", 60000);

        Manager manager1 = new Manager("Manager1", 80000);

        manager1.addSubordinate(employee1);
        manager1.addSubordinate(employee2);

        Department salesDepartment = new Department("Sales");

        salesDepartment.addEmployee(manager1);
        salesDepartment.addEmployee(employee1);
        salesDepartment.addEmployee(employee2);

        salesDepartment.displayEmployees();

        salesDepartment.calculateTotalSalaries();

        salesDepartment.promoteEmployee(employee1, 5000);

        salesDepartment.displayEmployees();
        salesDepartment.calculateTotalSalaries();
    }
}

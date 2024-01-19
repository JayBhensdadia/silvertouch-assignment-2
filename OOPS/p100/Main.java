import java.util.ArrayList;
import java.util.List;


class Employee {
    private int employeeId;
    private String name;
    private String designation;

    public Employee(int employeeId, String name, String designation) {
        this.employeeId = employeeId;
        this.name = name;
        this.designation = designation;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }
}

class Salary {
    private int employeeId;
    private double basicSalary;
    private double allowances;
    private double deductions;

    public Salary(int employeeId, double basicSalary, double allowances, double deductions) {
        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.allowances = allowances;
        this.deductions = deductions;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public double getAllowances() {
        return allowances;
    }

    public double getDeductions() {
        return deductions;
    }

    public double calculateNetSalary() {
        return basicSalary + allowances - deductions;
    }
}

class Payroll {
    private List<Employee> employees;
    private List<Salary> salaries;

    public Payroll() {
        this.employees = new ArrayList<>();
        this.salaries = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void addSalary(Salary salary) {
        salaries.add(salary);
    }

    public void generatePayStubs() {
        for (Employee employee : employees) {
            System.out.println("Pay Stub for Employee ID " + employee.getEmployeeId());
            System.out.println("Name: " + employee.getName());
            System.out.println("Designation: " + employee.getDesignation());

            Salary salary = salaries.stream()
                    .filter(s -> s.getEmployeeId() == employee.getEmployeeId())
                    .findFirst()
                    .orElse(null);

            if (salary != null) {
                System.out.println("Basic Salary: " + salary.getBasicSalary());
                System.out.println("Allowances: " + salary.getAllowances());
                System.out.println("Deductions: " + salary.getDeductions());
                System.out.println("Net Salary: " + salary.calculateNetSalary());
            } else {
                System.out.println("Salary information not found.");
            }

            System.out.println("----------------------------------------");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Employee emp1 = new Employee(1, "John Doe", "Software Engineer");
        Employee emp2 = new Employee(2, "Jane Smith", "HR Manager");

        Salary salary1 = new Salary(1, 50000.0, 2000.0, 1500.0);
        Salary salary2 = new Salary(2, 60000.0, 2500.0, 1800.0);

        Payroll payroll = new Payroll();

        payroll.addEmployee(emp1);
        payroll.addEmployee(emp2);

        payroll.addSalary(salary1);
        payroll.addSalary(salary2);

        payroll.generatePayStubs();
    }
}

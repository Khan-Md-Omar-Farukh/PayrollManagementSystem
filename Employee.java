public class Employee {
    protected String name;
    protected int id;
    protected double baseSalary;

    public Employee(String name, int id, double baseSalary) {
        this.name = name;
        this.id = id;
        this.baseSalary = baseSalary;
        
    }

    public double calculateSalary() {
        return baseSalary;
    }

    public String getRole() {
        return "Employee";
    }

    public String getName() { return name; }
    public int getId() { return id; }
    public double getBaseSalary() { return baseSalary; }
}

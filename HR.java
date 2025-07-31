public class HR extends Employee {
    private double commission;

    public HR(String name, int id, double baseSalary, double commission) {
        super(name, id, baseSalary);
        this.commission = commission;
    }

    @Override
    public double calculateSalary() {
        return baseSalary + commission;
    }

    @Override
    public String getRole() {
        return "HR";
    }

    public double getCommission() { return commission; }
}
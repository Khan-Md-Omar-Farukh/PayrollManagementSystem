public class Payslip {
    public static String generate(Employee emp) {
        StringBuilder sb = new StringBuilder();
        sb.append("Payslip\n");
        sb.append("------------------------\n");
        sb.append("Name: ").append(emp.getName()).append("\n");
        sb.append("ID: ").append(emp.getId()).append("\n");
        sb.append("Role: ").append(emp.getRole()).append("\n");
        sb.append("Base Salary: ").append(String.format("%.2f", emp.getBaseSalary())).append("\n");
        if (emp instanceof Manager) {
            sb.append("Bonus: ").append(String.format("%.2f", ((Manager) emp).getBonus())).append("\n");
        } else if (emp instanceof HR) {
            sb.append("Commission: ").append(String.format("%.2f", ((HR) emp).getCommission())).append("\n");
        }
        sb.append("------------------------\n");
        sb.append("Total Salary: ").append(String.format("%.2f", emp.calculateSalary())).append("\n");
        return sb.toString();
    }
}

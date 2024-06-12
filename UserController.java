import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class UserController {
    private static Map<String, String> users = new HashMap<>();
    private static Map<String, List<Expense>> userExpenses = new HashMap<>();
    private static double monthlyIncome;
    private static PieChart pieChart;
    private static String currentUser;
    private static Map<String, Color> colorMap = new HashMap<>();

    static {
        colorMap.put("食物", Color.RED);
        colorMap.put("住宿", Color.BLUE);
        colorMap.put("交通", Color.GREEN);
        colorMap.put("娱乐", Color.ORANGE);
        colorMap.put("其他", Color.MAGENTA);
    }

    public static boolean login(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    public static boolean register(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        users.put(username, password);
        return true;
    }

    public static void setCurrentUser(String username) {
        currentUser = username;
        if (!userExpenses.containsKey(username)) {
            userExpenses.put(username, new ArrayList<>());
        }
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void showUserFrame() {
        JFrame frame = new JFrame("用户界面 - " + getCurrentUser());
        frame.setSize(800, 600);

        JPanel panel = new JPanel(new BorderLayout());

        pieChart = new PieChart(getExpenses(getCurrentUser()), colorMap);

        JTextField expenseNameField = new JTextField(10);
        JTextField expenseAmountField = new JTextField(10);
        JButton addExpenseButton = new JButton("添加支出");
        addExpenseButton.addActionListener(e -> {
            String expenseName = expenseNameField.getText();
            String amountText = expenseAmountField.getText();
            if (expenseName.isEmpty() || amountText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "支出名称和金额不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double expenseAmount = Double.parseDouble(amountText);
            if (expenseAmount + getTotalExpenses(getCurrentUser()) > monthlyIncome) {
                JOptionPane.showMessageDialog(frame, "总支出不能大于月收入", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            addExpense(getCurrentUser(), expenseName, expenseAmount);
        });

        JTextField incomeField = new JTextField(10);
        JButton setIncomeButton = new JButton("设置月收入");
        setIncomeButton.addActionListener(e -> {
            String incomeText = incomeField.getText();
            JOptionPane.showMessageDialog(incomeField, "设置成功", "", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            if (incomeText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "月收入不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double income = Double.parseDouble(incomeText);
            setIncome(income);
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("支出名称:"));
        inputPanel.add(expenseNameField);
        inputPanel.add(new JLabel("金额:"));
        inputPanel.add(expenseAmountField);
        inputPanel.add(addExpenseButton);
        inputPanel.add(new JLabel("月收入:"));
        inputPanel.add(incomeField);
        inputPanel.add(setIncomeButton);

        panel.add(pieChart, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        JButton adminLoginButton = new JButton("管理员登录");
        adminLoginButton.addActionListener(e -> {
            String adminPassword = JOptionPane.showInputDialog(frame, "请输入管理员密码:");
            if ("admin123".equals(adminPassword)) {
                AdminController.showAdminFrame();
            } else {
                JOptionPane.showMessageDialog(frame, "管理员密码错误", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(adminLoginButton, BorderLayout.NORTH);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void addExpense(String username, String name, double amount) {
        Expense expense = new Expense(name, amount, getRandomColor());
        userExpenses.get(username).add(expense);
        pieChart.updateData(getExpenses(username));
    }

    public static void updateExpense(String username, int index, Expense newExpense) {
        List<Expense> userExpensesList = userExpenses.get(username);
        if (userExpensesList != null && index >= 0 && index < userExpensesList.size()) {
            userExpensesList.set(index, newExpense);
            pieChart.updateData(getExpenses(username));
        }
    }

    public static void deleteExpense(String username, int index) {
        List<Expense> userExpensesList = userExpenses.get(username);
        if (userExpensesList != null && index >= 0 && index < userExpensesList.size()) {
            userExpensesList.remove(index);
            pieChart.updateData(getExpenses(username));
        }
    }

    public static void setIncome(double income) {
        monthlyIncome = income;
    }

    public static double getTotalExpenses(String username) {
        return userExpenses.get(username).stream().mapToDouble(Expense::getAmount).sum();
    }

    public static List<Expense> getExpenses(String username) {
        return userExpenses.getOrDefault(username, new ArrayList<>());
    }

    private static Color getRandomColor() {
        return new Color((int) (Math.random() * 0x1000000));
    }
}

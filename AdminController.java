import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminController {
    public static void showAdminFrame() {
        JFrame frame = new JFrame("管理员界面");
        frame.setSize(800, 600);

        JPanel panel = new JPanel(new BorderLayout());

        String currentUser = UserController.getCurrentUser();
        List<Expense> expenses = UserController.getExpenses(currentUser);
        JList<Expense> expenseList = new JList<>(expenses.toArray(new Expense[0]));
        JScrollPane scrollPane = new JScrollPane(expenseList);

        JTextField newNameField = new JTextField(10);
        JTextField newAmountField = new JTextField(10);
        JButton updateExpenseButton = new JButton("修改支出");
        JButton deleteExpenseButton = new JButton("删除支出");

        updateExpenseButton.addActionListener(e -> {
            int selectedIndex = expenseList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(frame, "请先选择要修改的支出", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String newName = newNameField.getText();
            String newAmountText = newAmountField.getText();
            if (newName.isEmpty() || newAmountText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "支出名称和金额不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double newAmount = Double.parseDouble(newAmountText);
            Expense newExpense = new Expense(newName, newAmount, expenses.get(selectedIndex).getColor());
            UserController.updateExpense(currentUser, selectedIndex, newExpense);
            expenseList.setListData(UserController.getExpenses(currentUser).toArray(new Expense[0]));
        });

        deleteExpenseButton.addActionListener(e -> {
            int selectedIndex = expenseList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(frame, "请先选择要删除的支出", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            UserController.deleteExpense(currentUser, selectedIndex);
            expenseList.setListData(UserController.getExpenses(currentUser).toArray(new Expense[0]));
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("新支出名称:"));
        inputPanel.add(newNameField);
        inputPanel.add(new JLabel("新金额:"));
        inputPanel.add(newAmountField);
        inputPanel.add(updateExpenseButton);
        inputPanel.add(deleteExpenseButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class PieChart extends JPanel {
    private List<Expense> expenses;
    private Map<String, Color> colorMap;

    public PieChart(List<Expense> expenses, Map<String, Color> colorMap) {
        this.expenses = expenses;
        this.colorMap = colorMap;
    }

    public void updateData(List<Expense> expenses) {
        this.expenses = expenses;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        double startAngle = 0;

        for (Expense expense : expenses) {
            double arcAngle = expense.getAmount() / total * 360;
            g.setColor(expense.getColor());
            g.fillArc(100, 100, 200, 200, (int) startAngle, (int) arcAngle);
            startAngle += arcAngle;
        }

        // 绘制图例
        int legendX = 350;
        int legendY = 100;
        for (Expense expense : expenses) {
            g.setColor(expense.getColor());
            g.fillRect(legendX, legendY, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString(expense.getName() + ": " + expense.getAmount(), legendX + 25, legendY + 15);
            legendY += 25;
        }

        // 显示总支出
        g.setColor(Color.BLACK);
        g.drawString("总支出：" + total, 100, 80);

    }
}

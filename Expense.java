import java.awt.*;

public class Expense {
    private String name;
    private double amount;
    private Color color;

    public Expense(String name, double amount, Color color) {
        this.name = name;
        this.amount = amount;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name + ": " + amount;
    }
}

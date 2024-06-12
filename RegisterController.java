import javax.swing.*;
import java.awt.*;

public class RegisterController {
    public static void showRegisterFrame() {
        JFrame frame = new JFrame("注册界面");
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton registerButton = new JButton("注册");

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "用户名或密码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            } else if (UserController.register(username, password)) {
                JOptionPane.showMessageDialog(frame, "注册成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "注册失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel("用户名:"));
        panel.add(usernameField);
        panel.add(new JLabel("密码:"));
        panel.add(passwordField);
        panel.add(registerButton);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

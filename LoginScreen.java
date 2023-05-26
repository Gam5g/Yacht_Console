package Yacht;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private String[] registeredUsernames;
    private String[] registeredPasswords;

    public LoginScreen() {
        setTitle("로그인 화면");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("사용자명:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("비밀번호:");
        passwordField = new JPasswordField();
        loginButton = new JButton("로그인");

        Action loginAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login();
            }
        };
        loginButton.addActionListener(loginAction);

        JLabel registerLabel = new JLabel("아직 계정이 없으신가요?");
        JButton registerButton = new JButton("회원가입");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register();
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);
        panel.add(registerLabel);
        panel.add(registerButton);

        add(panel);

        InputMap inputMap = panel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "login");
        panel.getActionMap().put("login", loginAction);

        registeredUsernames = new String[100];
        registeredPasswords = new String[100];

        setVisible(true);
    }

    private void Login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        boolean isAuthenticated = PerformAuthentication(username, password);

        if (isAuthenticated) {
            JOptionPane.showMessageDialog(null, "로그인 성공!");
            new YachtDisplay();
        } else {
            JOptionPane.showMessageDialog(null, "로그인 실패!");
        }
    }

    private boolean PerformAuthentication(String username, String password) {
        for (int i = 0; i < registeredUsernames.length; i++) {
            if (username.equals(registeredUsernames[i]) && password.equals(registeredPasswords[i])) {
                return true;
            }
        }
        return false;
    }

    private void Register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        for (int i = 0; i < registeredUsernames.length; i++) {
            if (registeredUsernames[i] == null) {
                registeredUsernames[i] = username;
                registeredPasswords[i] = password;
                JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다!");
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "회원가입이 불가능합니다. 최대 회원 수를 초과하였습니다.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        System.exit(0);
                    }
                });
            }
        });
    }
}


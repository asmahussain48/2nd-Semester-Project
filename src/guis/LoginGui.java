package guis;

import db_objs.MyJDBC;
import db_objs.User;
import entities.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginGui extends JFrame {

    public LoginGui() {
        setTitle("Mobile App Store");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE); // Set white background
        // Set window icon
        ImageIcon icon = new ImageIcon(getClass().getResource("/login.png"));
        setIconImage(icon.getImage());

        addGuiComponents();

        setVisible(true);
    }

    private void addGuiComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Title label
        JLabel titleLabel = new JLabel("Mobilink");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Username label and text field
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameField, gbc);

        // Password label and password field
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordField, gbc);

        // Login button
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setBackground(new Color(220, 20, 60));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEtchedBorder());
        add(loginButton, gbc);

        // Register label with link-like style
        gbc.gridy++;
        JLabel registerLabel = new JLabel("<html><center>Don't have an account? <a href='' style='color:#0066CC;text-decoration:none'>Register here</a></center></html>");
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        registerLabel.setForeground(Color.BLACK);
        add(registerLabel, gbc);

        // Set default button (Enter triggers login)
        getRootPane().setDefaultButton(loginButton);

        // Login button action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            if (username.isEmpty() && password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter username and password");
            } else if (username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter username");
            } else if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter password");
            } else if (!username.matches("^[a-zA-Z0-9]+$")) {
                JOptionPane.showMessageDialog(null, "Username can only contain letters and numbers", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                User user = MyJDBC.validateLogin(username, password);
                if (user != null) {
                    Session.setCurrentUser(user);
                    this.dispose();
                    JOptionPane.showMessageDialog(null, "Login Successfully!");
                    openMobilePanel();
                } else {
                    JOptionPane.showMessageDialog(this, "Login failed...");
                }
            }
        });

        // Register label click event to open the login page
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginGui.this.dispose();
                new RegisterGui().setVisible(true);
            }
        });
    }

    private void openMobilePanel() {
        this.setVisible(false);
        JFrame mobileFrame = new JFrame("MobiLink Mobile Store");
        MobileManagementPanel mobilePanel = new MobileManagementPanel();

        mobileFrame.getContentPane().add(mobilePanel);
        mobileFrame.pack();
        mobileFrame.setLocationRelativeTo(null);
        mobileFrame.setVisible(true);
        mobileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Back button returns to LoginGui if needed (currently commented out)
        mobilePanel.getBackToMainMenuButton().addActionListener(ev -> {
            mobileFrame.dispose(); //close the mobileFrame
            // LoginGui.this.setVisible(true); // Uncomment if you want to reopen login page
        });
    }
}

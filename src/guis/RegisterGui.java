package guis;
import db_objs.MyJDBC; // for connecting to database class
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
/*
Mouse events let you track when a mouse is pressed, released,
clicked, moved, dragged, when it enters a component, when it exits
 and when a mouse wheel is moved.
 */
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterGui extends JFrame {
    public static void main(String[] args) {
        new RegisterGui();
    }
    public RegisterGui() {
        setTitle("Mobile App Store");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.WHITE);
        //Or
//        getContentPane().setBackground(new Color(250,250,250));
        // is used in Java Swing to change the background color of the main content area
        // (the area where components like buttons, labels, etc., are added) to white.
        // Set Icon
        ImageIcon icon = new ImageIcon(getClass().getResource("/login.png"));
        //getResourse for the login.png file in project
        // ImageIcon(C:/images/login.png) for directly given it path but not for production will issue
        // if you share the file with others
        setIconImage(icon.getImage());
        addGuiComponents();
        setVisible(true);
    }

    private void addGuiComponents() {
        //It defines how each component is placed and behaves inside a grid cell.
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(15, 20, 15, 20);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int y = 0;

        JLabel titleLabel = new JLabel("Mobilink");
        titleLabel.setFont(new Font("Ravie", Font.BOLD, 30));
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = y++;
        gbc.anchor = GridBagConstraints.CENTER;
        /*
        When the component does not fill the entire grid cell (because of fill),
        anchor decides where to "stick" it:
        Top-left?
        Center?
        Bottom-right? etc.
         */
        add(titleLabel, gbc);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;

        // Fields
        JTextField nameField = new JTextField(25);
        JTextField phoneField = new JTextField(25);
        JTextField addressField = new JTextField(25);
        JTextField emailField = new JTextField(25);
        JTextField usernameField = new JTextField(25);
        JPasswordField passwordField = new JPasswordField(25);
        JPasswordField rePasswordField = new JPasswordField(25);
/* instead of
JLabel nameLabel = new JLabel("Full Name:");
panel.add(nameLabel, gbc);
panel.add(nameField, gbc);
this below code is helpfull

 */
        addLabelAndField("Full Name:", nameField, gbc, y++);
        addLabelAndField("Phone Number:", phoneField, gbc, y++);
        addLabelAndField("Address:", addressField, gbc, y++);
        addLabelAndField("Email:", emailField, gbc, y++);
        addLabelAndField("Username:", usernameField, gbc, y++);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel("Gender:"), gbc);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JRadioButton maleButton = new JRadioButton("Male");
        JRadioButton femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);

        gbc.gridx = 1;
        add(genderPanel, gbc);
        y++;

        addLabelAndField("Password:", passwordField, gbc, y++);
        addLabelAndField("Re-type Password:", rePasswordField, gbc, y++);

        // Register Button
        JButton registerButton = new JButton("Register");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        gbc.gridx = 1;
        gbc.gridy = y++;
        gbc.gridwidth = 150;
        // Prevent the layout from stretching the button
        gbc.fill = GridBagConstraints.NONE; // {                   } length fill
        gbc.anchor = GridBagConstraints.CENTER;// postition of Register at center west etc

        gbc.gridwidth = 1;
        registerButton.setBackground(new Color(220, 20, 60)); // Crimson red
        registerButton.setPreferredSize(new Dimension(120, 40)); // Set width and height
        registerButton.setForeground(Color.WHITE);// White text
        registerButton.setFocusPainted(false); // Optional: no focus border
        registerButton.setBorder(BorderFactory.createEtchedBorder());
        add(registerButton, gbc);

        // Login link
        gbc.gridy++;
        JLabel loginLabel = new JLabel("<html><center>Already have an account? <a href='' style='color:#0066CC;text-decoration:none'>Sign in here</a></center></html>");
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        loginLabel.setForeground(Color.BLACK);  // Set font color here
        add(loginLabel, gbc);

        getRootPane().setDefaultButton(registerButton); // Set default button to register button


        // Register button action
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String rePassword = String.valueOf(rePasswordField.getPassword());
            String fullName = nameField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            String gender = maleButton.isSelected() ? "Male" :
                    femaleButton.isSelected() ? "Female" : "";

            if (fullName.isEmpty() || phone.isEmpty() || address.isEmpty() ||
                    email.isEmpty() || username.isEmpty() || password.isEmpty() ||
                    rePassword.isEmpty() || gender.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields, including gender.");
                return;
            }else if (!fullName.matches("^[a-zA-Z]+( [a-zA-Z]+)*$")) { // Regex for only Alphabetic with spaces between name
                // Examples of allowed input:
                // "John"
                // "John Doe"
                // "Alice Mary Johnson"
                //
                // Examples of rejected input:
                // "John123"       (contains digits)
                // "John_Doe"      (underscore is not allowed)
                // "John  Doe"     (two spaces between words)
                // "John-Doe"      (hyphen not allowed)
                JOptionPane.showMessageDialog(this, "Invalid full name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }else if (!phone.matches("^[0-9]{11}$")) { // Regex for only numbers and 11 digits
                // Examples of allowed input:
                // "03123456789"
                // "12345678901"
                //
                // Examples of rejected input:
                // "03123-456789"  (contains hyphen)
                // "1234567890"    (only 10 digits)
                // "abcdefghijk"   (letters instead of digits)
                JOptionPane.showMessageDialog(this, "Invalid phone number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if (!username.matches("^[a-zA-Z0-9]+$")) { // Regex for only Alphanumeric
                // Examples of allowed input:
                // "user123"
                // "Username"
                // "abc123XYZ"
                //
                // Examples of rejected input:
                // "user_123"      (underscore not allowed)
                // "user name"     (space not allowed)
                // "user!name"     (exclamation mark not allowed)
                JOptionPane.showMessageDialog(null, "Username can only contain letters and numbers", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (!password.matches(("^.{8,}$"))) {
                // Examples allowed:
                // "password"        (8 letters)
                // "p@ssw0rd!"       (letters, numbers, symbols)
                // "12345678"        (numbers only)
                // "my pass word"    (includes spaces)
                //
                // Examples rejected:
                // "pass"            (less than 8 characters)
                // ""                (empty string)
                JOptionPane.showMessageDialog(null, "Password can only contain letters and numbers", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (!email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) { // Regex for only Email address
                // Examples of allowed input:
                // "user@example.com"
                // "user.name+tag@domain.co.uk"
                // "user_name@sub.domain.com"
                //
                // Examples of rejected input:
                // "user@domain"           (no TLD, but this regex doesn't check TLD strictly)
                // "user@@domain.com"      (two @ symbols)
                // "user@domain,com"       (comma not allowed)
                JOptionPane.showMessageDialog(this, "Invalid email address", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(rePassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }

            if (username.length() < 6) {
                JOptionPane.showMessageDialog(this, "Username must be at least 6 characters.");
                return;
            }

            if (MyJDBC.register(username, email, password, fullName, phone, address)) {
                this.dispose();
                //Close the current window (JFrame) and release its resources
                // You want to move from one window to another (like going from Login → Dashboard).
                LoginGui loginGui = new LoginGui();
                loginGui.setVisible(true);
                JOptionPane.showMessageDialog(loginGui, "Registered Account Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Error: Username or Email already exists.");
            }
        });
        // Login link click
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterGui.this.dispose();//close the Register Frame
                new LoginGui().setVisible(true); //Open the Login Frame
            }
        });
    }

    private void addLabelAndField(String label, JComponent field, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel(label), gbc);
        gbc.gridx = 1;
        add(field, gbc);
    }
    private boolean validateUserInput(String username, String password, String rePassword) {
        return username.length() >= 6 && password.equals(rePassword);
    }
}

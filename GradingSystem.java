import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = new ImageIcon("bpsu3.jpg").getImage();
        } catch (Exception e) {
            System.err.println("Error: Image file not found!");
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            int width = this.getWidth();
            int height = this.getHeight();
            int imageWidth = backgroundImage.getWidth(this);
            int imageHeight = backgroundImage.getHeight(this);

            double scaleX = (double) width / imageWidth;
            double scaleY = (double) height / imageHeight;
            double scale = Math.max(scaleX, scaleY);

            int newImageWidth = (int) (imageWidth * scale);
            int newImageHeight = (int) (imageHeight * scale);

            int x = (width - newImageWidth) / 2;
            int y = (height - newImageHeight) / 2;

            g.drawImage(backgroundImage, x, y, newImageWidth, newImageHeight, this);
        }
    }
}
public class GradingSystem extends JFrame {
    private JTextField studentIdField, firstnameField, lastnameField;
    private JTextField[] scoreFields;
    private JLabel resultLabel, gradePercentageLabel, equivalentGradeLabel;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private JPanel gradingPanel, loginPanel;
  
    public GradingSystem() {
        setTitle("Grading System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
         setLayout(new CardLayout());;

        createLoginPanel();
        createGradingPanel();

        add(loginPanel);
        add(gradingPanel);

        gradingPanel.setVisible(false); 
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        
    }

    private void createLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(0, 0, 1200, 800);
        loginPanel.setBackground(Color.WHITE); 
        
        loginPanel = new BackgroundPanel("bpsu3.jpg");
        loginPanel.setLayout(null);
        loginPanel.setBounds(0, 0, 1200, 800);
        
        ImageIcon originalIcon = new ImageIcon("bpsu.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH); 
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setBounds(600, 60, 200, 200); 
        loginPanel.add(imageLabel);

        JLabel titleLabel = new JLabel("Grading System Login Form");
        titleLabel.setBounds(500, 270, 200, 25);
        loginPanel.add(titleLabel);
        titleLabel.setForeground(Color.WHITE);
        
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(500, 300, 100, 25);
        loginPanel.add(userLabel);
        userLabel.setForeground(Color.WHITE);

        JTextField userField = new JTextField();
        userField.setBounds(600, 300, 200, 25);
        loginPanel.add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(500, 350, 100, 25);
        loginPanel.add(passLabel);
        passLabel.setForeground(Color.WHITE);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(600, 350, 200, 25);
        loginPanel.add(passField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(650, 400, 100, 25);
        loginPanel.add(loginButton);
        loginButton.setForeground(Color.BLACK);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
               
                if (username.equals("admin") && password.equals("admin")) {
                    changePageToDebug();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials.");
                }
            }
        });
    }

    private void createGradingPanel() {
        
        
        gradingPanel = new JPanel();
        gradingPanel.setLayout(null);
        gradingPanel.setBounds(0, 0, 1200, 800);
        gradingPanel.setBackground(new Color(128, 0, 0));
                 
        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setBounds(20, 20, 100, 25);
        gradingPanel.add(studentIdLabel);
        studentIdField = new JTextField();
        studentIdField.setBounds(140, 20, 200, 25);
        gradingPanel.add(studentIdField);
        studentIdLabel.setForeground(Color.WHITE);
        

        JLabel firstnameLabel = new JLabel("Firstname:");
        firstnameLabel.setBounds(20, 60, 100, 25);
        gradingPanel.add(firstnameLabel);
        firstnameField = new JTextField();
        firstnameField.setBounds(140, 60, 200, 25);
        gradingPanel.add(firstnameField);
        firstnameLabel.setForeground(Color.WHITE);

        JLabel lastnameLabel = new JLabel("Lastname:");
        lastnameLabel.setBounds(20, 100, 100, 25);
        gradingPanel.add(lastnameLabel);
        lastnameField = new JTextField();
        lastnameField.setBounds(140, 100, 200, 25);
        gradingPanel.add(lastnameField);
        lastnameLabel.setForeground(Color.WHITE);

        String[] subjects = {
                "Computer Programming 2", 
                "Network Principles and Programming",
                "Discrete Structures 2",
                "Purposive Communication",
                "Understanding The Self",
                "SineSosyedad/Pelikulang Panlipunan",
                "Foreign Language (Asian)",
                "PATHFIT 2",
                "National Training Service 2",                
        };
        
        scoreFields = new JTextField[subjects.length];
        int yPosition = 140;
        for (int i = 0; i < subjects.length; i++) {
            JLabel subjectLabel = new JLabel(subjects[i]);
            subjectLabel.setBounds(20, yPosition, 250, 25);
            subjectLabel.setForeground(Color.WHITE);
            gradingPanel.add(subjectLabel);
            scoreFields[i] = new JTextField();
            scoreFields[i].setBounds(280, yPosition, 100, 25);
            gradingPanel.add(scoreFields[i]);
            yPosition += 40;
        }

        JButton computeButton = new JButton("Compute Grades");
        computeButton.setBounds(20, yPosition + 20, 150, 25);
        computeButton.addActionListener(new ComputeGradesListener());
        gradingPanel.add(computeButton);
        
        JButton okButton = new JButton("OK");
        okButton.setBounds(180, yPosition + 20, 100, 25);
        okButton.addActionListener(new OkButtonListener());
        gradingPanel.add(okButton);

        resultLabel = new JLabel("Result: ");
        resultLabel.setBounds(20, yPosition + 60, 200, 25);
        gradingPanel.add(resultLabel);
        resultLabel.setForeground(Color.WHITE);

        gradePercentageLabel = new JLabel("Grade Percentage: ");
        gradePercentageLabel.setBounds(20, yPosition + 100, 200, 25);
        gradingPanel.add(gradePercentageLabel);
        gradePercentageLabel.setForeground(Color.WHITE);

        equivalentGradeLabel = new JLabel("Equivalent Grade: ");
        equivalentGradeLabel.setBounds(20, yPosition + 140, 200, 25);
        gradingPanel.add(equivalentGradeLabel);
        equivalentGradeLabel.setForeground(Color.WHITE);

        String[] columnNames = {"Student ID", "Firstname", "Lastname", "Grade", "Percentage", "Equivalent"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultsTable = new JTable(tableModel);
        resultsTable.setBackground(Color.WHITE); 
        resultsTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBounds(400, 20, 750, 600);
        gradingPanel.add(scrollPane);
        
         ImageIcon originalIcon = new ImageIcon("bpsu.png");
           if (originalIcon.getIconWidth() == -1) {
        System.err.println("Error: Image file not found!");
         } else {
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH); 
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        
        JLabel imageLabel = new JLabel(resizedIcon);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xPos = screenSize.width - 150; 
        imageLabel.setBounds(xPos, 20, 100, 100); 
        gradingPanel.add(imageLabel);
    }
       
 }

    private void changePageToDebug() {
        loginPanel.setVisible(false);
        gradingPanel.setVisible(true);
        gradingPanel.repaint(); 
    }

    private class ComputeGradesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int[] scores = new int[scoreFields.length];
                for (int i = 0; i < scoreFields.length; i++) {
                    scores[i] = Integer.parseInt(scoreFields[i].getText());
                }
                double grade = calculateGrade(scores);
                String gradeDescription = getGradeDescription(grade);
                String equivalentGrade = getEquivalentGrade(grade);

                resultLabel.setText("Result: " + gradeDescription);
                gradePercentageLabel.setText("Grade Percentage: " + (int) grade); 
                equivalentGradeLabel.setText("Equivalent Grade: " + equivalentGrade);

                tableModel.addRow(new Object[]{
                        studentIdField.getText(),
                        firstnameField.getText(),
                        lastnameField.getText(),
                        gradeDescription,
                        (int) grade, 
                        equivalentGrade
                });
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid scores.");
            }
        }
    }

    private class OkButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            studentIdField.setText("");
            firstnameField.setText("");
            lastnameField.setText("");
            resultLabel.setText("Result: ");
            gradePercentageLabel.setText("Grade Percentage: ");
            equivalentGradeLabel.setText("Equivalent Grade: ");
            for (JTextField field : scoreFields) {
                field.setText("");
            }
        }
    }

    private double calculateGrade(int[] scores) {
        double totalGrade = 0;
        for (int score : scores) {
            totalGrade += score;
        }
        return totalGrade / scores.length;
    }

    private String getGradeDescription(double grade) {
        if (grade >= 98) return "Excellent/Very Superior";
        if (grade >= 95) return "Superior";
        if (grade >= 92) return "Very Good/Above Average";
        if (grade >= 89) return "Very Good/Above Average";
        if (grade >= 86) return "Very Good/Above Average";
        if (grade >= 83) return "Good/Average";
        if (grade >= 80) return "Good/Average";
        if (grade >= 77) return "Satisfactory/Fair";
        if (grade >= 75) return "Satisfactory/Fair";
        return "Failed";
    }

    private String getEquivalentGrade(double grade) {
        if (grade >= 98) return "1.0";
        if (grade >= 95) return "1.25";
        if (grade >= 92) return "1.5";
        if (grade >= 89) return "1.75";
        if (grade >= 86) return "2.0";
        if (grade >= 83) return "2.25";
        if (grade >= 80) return "2.5";
        if (grade >= 77) return "2.75";
        if (grade >= 75) return "3.0";
        return "5.0";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GradingSystem frame = new GradingSystem();
            frame.setVisible(true);
        });
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TriangleApp {
    private JFrame frame;
    private JTextField side1Field;
    private JTextField side2Field;
    private JTextField side3Field;
    private JLabel resultLabel;
    private JTextArea resultArea;
    private TrianglePanel trianglePanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                TriangleApp window = new TriangleApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TriangleApp() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Triangle Type Checker");
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);

        inputPanel.add(new JLabel("Side 1:"));
        side1Field = new JTextField();
        inputPanel.add(side1Field);

        inputPanel.add(new JLabel("Side 2:"));
        side2Field = new JTextField();
        inputPanel.add(side2Field);

        inputPanel.add(new JLabel("Side 3:"));
        side3Field = new JTextField();
        inputPanel.add(side3Field);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayTriangleType();
            }
        });
        inputPanel.add(btnSubmit);

        resultLabel = new JLabel("Result:");
        frame.getContentPane().add(resultLabel, BorderLayout.CENTER);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBackground(UIManager.getColor("Panel.background"));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        frame.getContentPane().add(scrollPane, BorderLayout.SOUTH);

        trianglePanel = new TrianglePanel();
        frame.getContentPane().add(trianglePanel, BorderLayout.WEST);
    }

    private void displayTriangleType() {
        try {
            double side1 = Double.parseDouble(side1Field.getText());
            double side2 = Double.parseDouble(side2Field.getText());
            double side3 = Double.parseDouble(side3Field.getText());

            if (isTriangle(side1, side2, side3)) {
                String triangleType = getTriangleType(side1, side2, side3);
                String result = "Triangle Type: " + triangleType + "\n\n";
                result += drawTriangle(side1, side2, side3);
                resultArea.setText(result);
                resultLabel.setText("Result: " + triangleType);
                trianglePanel.setTriangle(side1, side2, side3);
            } else {
                resultArea.setText("Invalid Triangle");
                resultLabel.setText("Result: Invalid Triangle");
                trianglePanel.clearTriangle();
            }
        } catch (NumberFormatException ex) {
            resultArea.setText("Please enter valid numeric values for sides.");
            resultLabel.setText("Result: Invalid Input");
            trianglePanel.clearTriangle();
        }
    }

    private boolean isTriangle(double side1, double side2, double side3) {
        return (side1 + side2 > side3) && (side2 + side3 > side1) && (side3 + side1 > side2);
    }

    private String getTriangleType(double side1, double side2, double side3) {
        if (side1 == side2 && side2 == side3) {
            return "Equilateral";
        } else if (side1 == side2 || side2 == side3 || side1 == side3) {
            return "Isosceles";
        } else {
            return "Scalene";
        }
    }

    private String drawTriangle(double side1, double side2, double side3) {
        // For simplicity, returning a basic text representation
        return "   /\\\n  /  \\\n /____\\";
    }

    private class TrianglePanel extends JPanel {
        private double side1, side2, side3;

        public void setTriangle(double side1, double side2, double side3) {
            this.side1 = side1;
            this.side2 = side2;
            this.side3 = side3;
            repaint();
        }

        public void clearTriangle() {
            this.side1 = 0;
            this.side2 = 0;
            this.side3 = 0;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the triangle
            int x1 = 50, y1 = getHeight() - 50;
            int x2 = x1 + (int) side1, y2 = y1;
            int x3 = x1 + (int) (side1 / 2), y3 = y1 - (int) (Math.sqrt(3) * side1 / 2);

            if (isTriangle(side1, side2, side3)) {
                g.setColor(Color.BLUE); // Set color to blue for the triangle
                g.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
            }
        }
    }
}

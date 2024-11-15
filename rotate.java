import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class RotateDisplay extends JFrame {
    private RotatingPanel rotatingPanel;
    private Timer timer;
    private double angle = 0; // Initial angle

    public RotateDisplay() {
        setTitle("Rotating Display");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        rotatingPanel = new RotatingPanel();
        add(rotatingPanel, BorderLayout.CENTER);

        // Timer to increment the angle and repaint the panel
        timer = new Timer(50, e -> {
            angle += 5; // Increment the angle (in degrees)
            if (angle >= 360) {
                angle = 0; // Reset after a full rotation
            }
            rotatingPanel.setAngle(Math.toRadians(angle)); // Convert to radians
            rotatingPanel.repaint();
        });

        timer.start();
    }

    // Panel that handles rotation
    private static class RotatingPanel extends JPanel {
        private double angle = 0;

        public void setAngle(double angle) {
            this.angle = angle;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Enable anti-aliasing for smooth rotation
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Get panel's center
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            // Save the current transform
            AffineTransform originalTransform = g2d.getTransform();

            // Rotate around the panel's center
            g2d.rotate(angle, centerX, centerY);

            // Draw a sample shape (rotating content)
            g2d.setColor(Color.BLUE);
            g2d.fillRect(centerX - 50, centerY - 50, 100, 100);

            g2d.setColor(Color.WHITE);
            g2d.drawString("Rotating!", centerX - 25, centerY + 5);

            // Restore the original transform
            g2d.setTransform(originalTransform);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RotateDisplay rotateDisplay = new RotateDisplay();
            rotateDisplay.setVisible(true);
        });
    }
}

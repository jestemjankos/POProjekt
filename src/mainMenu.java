import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainMenu {
    public JFrame frame;
    public JPanel panel;
    public JLabel logoLabel;
    public JButton przyciskZagraj;
    public JButton przyciskBJ;
    public JButton przyciskWyjdz;

    public mainMenu() {
        frame = new JFrame("Kasyno BETA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        panel = new BackgroundPanel("src/tlo.png");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoLabel = new JLabel(new ImageIcon("src/logo.png"));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoLabel);

        przyciskZagraj = new JButton("Zagraj w Kołko i Krzyżyk");
        przyciskZagraj.setFont(new Font("Arial", Font.PLAIN, 18));
        przyciskZagraj.setAlignmentX(Component.CENTER_ALIGNMENT);
        przyciskZagraj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new kolkoKrzyzyk();
                frame.dispose();
            }
        });

        przyciskBJ = new JButton("Zagraj w BlackJack'a ");
        przyciskBJ.setFont(new Font("Arial", Font.PLAIN, 18));
        przyciskBJ.setAlignmentX(Component.CENTER_ALIGNMENT);
        przyciskBJ.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BlackjackFrame();
                frame.dispose();
            }
        });

        przyciskWyjdz = new JButton("Zakończ");
        przyciskWyjdz.setFont(new Font("Arial", Font.PLAIN, 18));
        przyciskWyjdz.setAlignmentX(Component.CENTER_ALIGNMENT);
        przyciskWyjdz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(przyciskZagraj);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(przyciskBJ);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(przyciskWyjdz);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new mainMenu();
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = Toolkit.getDefaultToolkit().getImage(imagePath);
        } catch (Exception e) {
            System.err.println("Błąd podczas ładowania obrazu: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

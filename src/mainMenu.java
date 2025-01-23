import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainMenu {
    private Gracz gracz; // Obiekt Gracz

    public JFrame frame;
    public JPanel panel;
    public JLabel logoLabel;
    public JButton przyciskZagraj;
    public JButton przyciskBJ;
    public JButton przyciskKosci;
    public JButton przyciskWyjdz;
    public JButton przyciskDoladuj;

    public mainMenu(Gracz gracz) {
        this.gracz = gracz; // Przypisanie gracza do pola

        frame = new JFrame("Kasyno BETA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        panel = new BackgroundPanel("src/tlo.png");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoLabel = new JLabel(new ImageIcon("src/logo.png"));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoLabel);

        JLabel saldoLabel = new JLabel("Saldo: " + gracz.getSaldo() + " PLN", SwingConstants.CENTER);
        saldoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        saldoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(saldoLabel);

        // Przycisk "Doładuj saldo"
        przyciskDoladuj = new JButton("Doładuj saldo (+1000 PLN)");
        przyciskDoladuj.setFont(new Font("Arial", Font.PLAIN, 18));
        przyciskDoladuj.setAlignmentX(Component.CENTER_ALIGNMENT);
        przyciskDoladuj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gracz.dodajSaldo(1000); // Dodanie 1000 PLN do salda
                saldoLabel.setText("Saldo: " + gracz.getSaldo() + " PLN"); // Aktualizacja etykiety salda
            }
        });

        przyciskBJ = new JButton("Zagraj w BlackJack'a ");
        przyciskBJ.setFont(new Font("Arial", Font.PLAIN, 18));
        przyciskBJ.setAlignmentX(Component.CENTER_ALIGNMENT);
        przyciskBJ.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BlackjackFrame(gracz);
                frame.dispose();
            }
        });

        przyciskKosci = new JButton("Zagraj w Kości");
        przyciskKosci.setFont(new Font("Arial", Font.PLAIN, 18));
        przyciskKosci.setAlignmentX(Component.CENTER_ALIGNMENT);
        przyciskKosci.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DiceGame(gracz);
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

        // Dodanie przycisku doładowania salda
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(przyciskDoladuj);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(przyciskBJ);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(przyciskKosci);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(przyciskWyjdz);

        frame.add(panel);
        frame.setVisible(true);
    }

    public mainMenu() {
        this(new Gracz("Default", 1000)); // Domyślny konstruktor
    }

    public static void main(String[] args) {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {

            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        // Przyklad: Utworzenie obiektu Gracz i otwarcie menu
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

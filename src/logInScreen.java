import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class logInScreen {
    private final String filePath = "src/users.txt";
    public JFrame frame;
    public JPanel panel;
    public JTextField nickField;
    private JPasswordField passwordField;
    public JButton loginButton;
    public JButton registerButton;
    public JLabel messageLabel;
    private final String listaGraczy = "src/listaGraczy.bin";
    private final String daneGraczy = "src/daneGraczy.bin";
    public logInScreen() {
        frame = new JFrame("Logowanie - Kasyno BETA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 310);
        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Witaj w Kasynie BETA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nickLabel = new JLabel("Podaj swój login:");
        nickLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nickField = new JTextField(15);
        nickField.setMaximumSize(new Dimension(200, 30));

        JLabel passwordLabel = new JLabel("Podaj swoje hasło:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(new Dimension(200, 30));

        registerButton = new JButton("Rejestracja");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new registerScreen();
                frame.dispose();
            }
        });
        loginButton = new JButton("Zaloguj się");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nick = nickField.getText().trim();

                if (!nick.isEmpty() && logowanie(nick, hashowanie(passwordField.getPassword()))) {
                    Gracz gracz = null; // wczytanie danych o graczu
                    try {
                        gracz = wczytajGracza(nick);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    if(gracz != null)
                    {
                        new mainMenu(gracz); // Przejście do menu głównego
                        frame.dispose();
                    }
                } else {
                    messageLabel.setText("Niepoprawny login lub hasło");
                }
            }
        });

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(nickLabel);
        panel.add(nickField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(registerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {

            //System.out.println(info + " - " + info.getName()); //wy�wietlenie dost�pnych opcji!

            //ustawienie Nimbus

            if ("Nimbus".equals(info.getName())) {

                try {

                    UIManager.setLookAndFeel(info.getClassName());

                } catch (ClassNotFoundException | InstantiationException

                         | IllegalAccessException

                         | UnsupportedLookAndFeelException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                }

                break;

            }

        }
        new logInScreen();
    }
    private boolean logowanie(String nick, String password)
    {
        boolean zalogowano = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] podzial = line.split(","); // Rozdzielanie na nick i hasło
                if (podzial.length == 2 && podzial[0].equals(nick) && podzial[1].equals(password)) {
                    zalogowano = true;
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
        }
        return zalogowano;
    }

    private Gracz wczytajGracza(String nick) throws FileNotFoundException {
        Gracz gracz = null;
        try (RandomAccessFile raf = new RandomAccessFile(daneGraczy, "rw")) {
            Map<String, Long> indeks;

            File plikIndeksu = new File(listaGraczy);
            if (plikIndeksu.exists()) {
                indeks = Gracz.odczytajIndex(listaGraczy);
            } else {
                indeks = new HashMap<>();
            }

            gracz = Gracz.odczytajGracza(raf, indeks, nick);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gracz;
    }

    private String hashowanie(char [] password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] zakodowanyHash = digest.digest(new String(password).getBytes());
            StringBuilder hash = new StringBuilder();
            for (byte bit : zakodowanyHash) {
                String hex = Integer.toHexString(0xff & bit);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Błąd algorytmu hashującego: " + e.getMessage());
        }
    }
}

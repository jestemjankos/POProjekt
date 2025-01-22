import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class registerScreen {
    private final String filePath = "src/users.txt";
    public JFrame frame;
    public JPanel panel;
    public JTextField nickField;
    private JPasswordField passwordField;
    private JPasswordField passwordField2;
    public JButton loginButton;
    private JButton registerButton;
    public JLabel messageLabel;
    private final String listaGraczy = "src/listaGraczy.bin";
    private final String daneGraczy = "src/daneGraczy.bin";
    public registerScreen() {
        frame = new JFrame("Rejestracja - Kasyno BETA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
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

        JLabel passwordLabel2 = new JLabel("Powtórz hasło:");
        passwordLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField2 = new JPasswordField(15);
        passwordField2.setMaximumSize(new Dimension(200, 30));

        loginButton = new JButton("Logowanie");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new logInScreen();
                frame.dispose();
            }
        });

        registerButton = new JButton("Zarejestruj");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nick = nickField.getText().trim();

                if (!nick.isEmpty() && czyWolnyLogin(nick)) {
                    if(rejestrowanie(nick, hashowanie(passwordField.getPassword()), hashowanie(passwordField2.getPassword())))
                    {
                        utworzNowegoGracza(nick);
                        new logInScreen(); // Przejście do logowania
                        frame.dispose();
                    }else
                    {
                        messageLabel.setText("Hasła się nie zgadzają");
                    }
                } else {
                    messageLabel.setText("Login jest zajęty");
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
        panel.add(passwordLabel2);
        panel.add(passwordField2);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(registerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new registerScreen();
    }

    private boolean czyWolnyLogin(String nick)
    {
        boolean wolnyLogin = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] podzial = line.split(","); // Rozdzielanie na nick i hasło
                if (podzial[0].equals(nick)) {
                    wolnyLogin = false;
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
        }
        return wolnyLogin;
    }
    private boolean rejestrowanie(String nick, String password, String password2)
    {
        if(!password.equals(password2) || password.isEmpty() || nick.isEmpty())
        {
            return false;
        }

        boolean udane = false;

        try (FileWriter fw = new FileWriter(filePath, true);
        PrintWriter writer = new PrintWriter(fw))
        {
            String zapisz = nick + "," + password;
        writer.println(zapisz);
        udane = true;
    } catch (IOException e) {
        e.printStackTrace(); // Obsługuje wyjątek w przypadku problemów z plikiem
    }
        return udane;
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
    private void utworzNowegoGracza(String nick) {
        Gracz nowyGracz = new Gracz(nick, 0);
        try (RandomAccessFile raf = new RandomAccessFile(daneGraczy, "rw")) {
            Map<String, Long> indeks;

            File plikIndeksu = new File(listaGraczy);
            if (plikIndeksu.exists()) {
                indeks = Gracz.odczytajIndex(listaGraczy);
            } else {
                indeks = new HashMap<>();
            }
            Gracz.zapiszGracza(daneGraczy, indeks, nowyGracz);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

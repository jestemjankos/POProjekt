import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class logInScreen {
    public JFrame frame;
    public JPanel panel;
    public JTextField nickField;
    public JButton loginButton;
    public JLabel messageLabel;

    public logInScreen() {
        frame = new JFrame("Logowanie - Kasyno BETA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Witaj w Kasynie BETA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nickLabel = new JLabel("Podaj swój nick:");
        nickLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nickField = new JTextField(15);
        nickField.setMaximumSize(new Dimension(200, 30));

        loginButton = new JButton("Zaloguj się");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nick = nickField.getText().trim();
                if (!nick.isEmpty()) {
                    Gracz gracz = new Gracz(nick, 1000); // Tworzenie obiektu Gracz z domyślnym saldem
                    new mainMenu(gracz); // Przejście do menu głównego
                    frame.dispose();
                } else {
                    messageLabel.setText("Nick nie może być pusty!");
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
        panel.add(loginButton);
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
}

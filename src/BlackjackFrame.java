import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackjackFrame extends JFrame {
    private JLabel saldoLabel;
    private JButton wrocButton;
    private JButton rozpocznijGręButton;
    private JButton zasadyButton;
    private BlackJack blackJack;

    public BlackjackFrame() {
        setTitle("Blackjack");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        // Inicjalizowanie przycisków
        wrocButton = new JButton("Wróć do kasyna");
        rozpocznijGręButton = new JButton("Rozpocznij grę");
        zasadyButton = new JButton("Zasady gry");

        // Przycisk do rozpoczęcia gry
        rozpocznijGręButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Uruchomienie gry
                blackJack = new BlackJack();
                dispose();  // Zamknięcie okna obecnego
            }
        });

        // Przycisk do powrotu do menu
        wrocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new mainMenu();  // Przejście do menu głównego
                dispose();  // Zamknięcie obecnego okna
            }
        });

        // Przycisk do wyświetlenia zasad gry
        zasadyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Wyświetlenie zasad gry
                String zasady = "Zasady gry w Blackjack:\n\n" +
                        "1. Celem gry jest zdobycie jak największej liczby punktów, " +
                        "ale nie przekraczając 21.\n" +
                        "2. Karty 2-10 mają wartość równą swojej liczbie.\n" +
                        "3. Karty J, Q, K mają wartość 10 punktów.\n" +
                        "4. As (A) może być wart 1 lub 11 punktów.\n" +
                        "5. Gracz i dealer dostają po 2 karty. Gracz widzi wszystkie karty " +
                        "dealer'a oprócz jednej (tzw. ukrytej).\n" +
                        "6. Gracz może wybrać 'Hit' (dobranie karty) lub 'Stay' (pozostanie " +
                        "przy obecnym wyniku).\n" +
                        "7. Gra kończy się, gdy gracz przekroczy 21 punktów (przegrana), " +
                        "lub gdy dealer przekroczy 21 punktów (wygrana).\n" +
                        "8. Jeśli gracz ma wyższą sumę niż dealer, wygrywa, jeśli niższą, przegrywa.";

                JOptionPane.showMessageDialog(BlackjackFrame.this, zasady, "Zasady gry", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Panel z przyciskami
        JPanel panel = new JPanel();
        panel.add(rozpocznijGręButton);
        panel.add(zasadyButton);
        panel.add(wrocButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BlackjackFrame(); // Uruchomienie głównego okna
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackjackFrame extends JFrame {
    //private Gracz gracz;
    private JLabel saldoLabel;
    private JButton wrocButton;

    public BlackjackFrame(/*Gracz gracz*/) {
        //this.gracz = gracz;

        setTitle("Blackjack");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

       // saldoLabel = new JLabel("Saldo: " + gracz.getSaldo());
        wrocButton = new JButton("Wróć do kasyna");

        wrocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new mainMenu();
                dispose();
            }
        });

        JPanel panel = new JPanel();
        //panel.add(saldoLabel);
        panel.add(wrocButton);

        add(panel);
        setVisible(true);
    }
}
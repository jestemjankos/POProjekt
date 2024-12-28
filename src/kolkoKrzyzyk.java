import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class kolkoKrzyzyk implements ActionListener{
    public JFrame frame;
    public JPanel panel;
    public JLabel label;
    public JButton przyciskCofnij;
    public JButton[] buttons = new JButton[9];
    public boolean xRuch = true;


    public kolkoKrzyzyk() {
        frame = new JFrame("Kółko Krzyżyk :)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label = new JLabel("Kółko Krzyżyk - Gracz X zaczyna!", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setOpaque(true);
        label.setBackground(Color.decode("#9be8b4"));
        label.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        panel = new JPanel();
        panel.setBackground(Color.decode("#01822a"));
        panel.setLayout((new GridLayout(3,3)));
        panel.setBorder(BorderFactory.createEmptyBorder(75,75,75,75));

        for(int i = 0; i < 9; i++){
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 40));
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
        }

        frame.add(label, BorderLayout.NORTH);
        frame.add(panel,  BorderLayout.CENTER);
        frame.setSize(600,600);
        frame.setVisible(true);



        przyciskCofnij = new JButton("Wróć");
        przyciskCofnij.setFont(new Font("Arial", Font.PLAIN, 18));
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(przyciskCofnij);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        przyciskCofnij.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new mainMenu();
                frame.dispose();
            }
        });
    }
    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        if(xRuch){
            button.setText("X");
        }
        else{
            button.setText("O");
        }
        button.setEnabled(false);
        xRuch = !xRuch;

        checkForWinner();
    }
    private void checkForWinner() {
        String[][] patterns = {
                { "0", "1", "2" },
                { "3", "4", "5" },
                { "6", "7", "8" },
                { "0", "3", "6" },
                { "1", "4", "7" },
                { "2", "5", "8" },
                { "0", "4", "8" },
                { "2", "4", "6" }
        };

        for (String[] pattern : patterns) {
            int a = Integer.parseInt(pattern[0]);
            int b = Integer.parseInt(pattern[1]);
            int c = Integer.parseInt(pattern[2]);

            if (!buttons[a].getText().isEmpty() &&
                    buttons[a].getText().equals(buttons[b].getText()) &&
                    buttons[a].getText().equals(buttons[c].getText())) {
                JOptionPane.showMessageDialog(frame, buttons[a].getText() + " wygrał!");
                resetGame();
                return;
            }
        }

        boolean remis = true;
        for (JButton button : buttons) {
            if (button.isEnabled()) {
                remis = false;
                break;
            }
        }

        if (remis) {
            JOptionPane.showMessageDialog(frame, "Remis!");
            resetGame();
        }
    }
    public void resetGame(){
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
        }
        xRuch = true;
    }

    public static void main(String[] args){
        new kolkoKrzyzyk();
    }
}
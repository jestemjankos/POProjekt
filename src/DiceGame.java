import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DiceGame implements ActionListener{
    public JFrame frame;
    public DicePanel panel;
    public ScoreBoard scoreBoard;
    private int[] punkty;
    private int pozostaleTury;
    Timer timer;
    public static void main(String[] args) {
        new DiceGame();
    }
    protected class mouseClickListener extends MouseAdapter {
    public void mouseClicked(MouseEvent e) {
        JLabel source = (JLabel) e.getSource();
        if(scoreBoard.scores[1][1] == source && punkty[0] > 0 && scoreBoard.nieWykorzystane[0]) //mozliwe punkty za jedynki
        {
            source.setText(punkty[0] + "");
            scoreBoard.nieWykorzystane[0] = false;
            scoreBoard.calkowityWynik += punkty[0];
        }
        if(scoreBoard.scores[2][1] == source && punkty[1] > 0 && scoreBoard.nieWykorzystane[1]) //mozliwe punkty za dwójki
        {
            source.setText(punkty[1] + "");
            scoreBoard.nieWykorzystane[1] = false;
            scoreBoard.calkowityWynik += punkty[1];
        }
        if(scoreBoard.scores[3][1] == source && punkty[2] > 0 && scoreBoard.nieWykorzystane[2]) //mozliwe punkty za trójki
        {
            source.setText(punkty[2] + "");
            scoreBoard.nieWykorzystane[2] = false;
            scoreBoard.calkowityWynik += punkty[2];
        }
        if(scoreBoard.scores[4][1] == source && punkty[3] > 0 && scoreBoard.nieWykorzystane[3]) //mozliwe punkty za czwórki
        {
            source.setText(punkty[3] + "");
            scoreBoard.nieWykorzystane[3] = false;
            scoreBoard.calkowityWynik += punkty[3];
        }
        if(scoreBoard.scores[5][1] == source && punkty[4] > 0 && scoreBoard.nieWykorzystane[4]) //mozliwe punkty za piątki
        {
            source.setText(punkty[4] + "");
            scoreBoard.nieWykorzystane[4] = false;
            scoreBoard.calkowityWynik += punkty[4];
        }
        if(scoreBoard.scores[6][1] == source && punkty[5] > 0 && scoreBoard.nieWykorzystane[5]) //mozliwe punkty za szóstki
        {
            source.setText(punkty[5] + "");
            scoreBoard.nieWykorzystane[5] = false;
            scoreBoard.calkowityWynik += punkty[5];
        }
        if(scoreBoard.scores[7][1] == source && punkty[6] > 0 && scoreBoard.nieWykorzystane[6]) //mozliwe punkty za trzy jednakowe
        {
            source.setText(punkty[6] + "");
            scoreBoard.nieWykorzystane[6] = false;
            scoreBoard.calkowityWynik += punkty[6];
        }
        if(scoreBoard.scores[8][1] == source && punkty[7] > 0 && scoreBoard.nieWykorzystane[7]) //mozliwe punkty za cztery jednakowe
        {
            source.setText(punkty[7] + "");
            scoreBoard.nieWykorzystane[7] = false;
            scoreBoard.calkowityWynik += punkty[7];
        }
        if(scoreBoard.scores[9][1] == source && punkty[8] > 0 && scoreBoard.nieWykorzystane[8]) //mozliwe punkty za fulla
        {
            source.setText(punkty[8] + "");
            scoreBoard.nieWykorzystane[8] = false;
            scoreBoard.calkowityWynik += punkty[8];
        }
        if(scoreBoard.scores[10][1] == source && punkty[9] > 0 && scoreBoard.nieWykorzystane[9]) //mozliwe punkty za małego strita
        {
            source.setText(punkty[9] + "");
            scoreBoard.nieWykorzystane[9] = false;
            scoreBoard.calkowityWynik += punkty[9];
        }
        if(scoreBoard.scores[11][1] == source && punkty[10] > 0 && scoreBoard.nieWykorzystane[10]) //mozliwe punkty za dużego strita
        {
            source.setText(punkty[10] + "");
            scoreBoard.nieWykorzystane[10] = false;
            scoreBoard.calkowityWynik += punkty[10];
        }
        if(scoreBoard.scores[12][1] == source && punkty[11] > 0 && scoreBoard.nieWykorzystane[11]) //mozliwe punkty za generała
        {
            source.setText(punkty[11] + "");
            scoreBoard.nieWykorzystane[11] = false;
            scoreBoard.calkowityWynik += punkty[11];
        }
        if(scoreBoard.scores[13][1] == source && punkty[12] > 0 && scoreBoard.nieWykorzystane[12]) //mozliwe punkty za szansę
        {
            source.setText(punkty[12] + "");
            scoreBoard.nieWykorzystane[12] = false;
            scoreBoard.calkowityWynik += punkty[12];
        }








        scoreBoard.scores[14][1].setText(scoreBoard.calkowityWynik + "");
    }
    }
    private mouseClickListener listener = new mouseClickListener();
    public DiceGame() {
        pozostaleTury = 13;
        frame = new JFrame("Kości");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null); // Wyśrodkowanie okna na ekranie
        frame.setLayout(new BorderLayout());
        // Ustawienie układu
        scoreBoard = new ScoreBoard();
        scoreBoard.setLayout(new GridLayout(scoreBoard.getRows(), scoreBoard.getCols()));
        scoreBoard.setBackground(Color.WHITE);
        scoreBoard.setPreferredSize(new Dimension(300, 100));
        scoreBoard.setListeners(listener);

        panel = new DicePanel(400, 250);
        panel.setPreferredSize(new Dimension(900, 800));
        panel.setBackground(Color.GRAY);
        panel.rollButton.addActionListener(this);
        timer = new Timer(10, panel); // co 0.01s

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scoreBoard, panel);
        splitPane.setDividerLocation(0.3);
        splitPane.setResizeWeight(0.3);
        splitPane.setContinuousLayout(true);
        splitPane.setDividerSize(0);

        frame.add(splitPane);
        //scoreBoard.scores[3][1].setText("asd");
        //frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panel.rollButton) {
            if (panel.stanKubka && pozostaleTury > 0) {
                pozostaleTury--;
                timer.start();
                panel.ukryjKosci();
                panel.stanKubka = false;
            }else if(pozostaleTury > 0)
            {
                timer.stop();
                panel.pokazKosci();
                punkty = panel.podliczPunkty();
                //podliczPunkty();
                panel.stanKubka = true;
                //kostka1.setBounds(xPos + 20, yPos + 210, 30, 30); //poprawne ustawienie miejsca kostek odmawia współpracy
            }else
            {
                panel.setVisible(false);
            }
        }
    }
    /*
    private void podliczPunkty() {
        if(punkty[0] > 0) //mozliwe punkty za jedynki
        {
            scoreBoard.aktywnoscPunktacji[0] = 1;
        }
    }

     */
}


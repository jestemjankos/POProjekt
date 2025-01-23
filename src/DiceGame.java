import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class DiceGame implements ActionListener{
    private JFrame frame;
    protected Gracz gracz;
    private final int MAX_TURY = 13;
    private JFrame frameSummary;
    private JPanel summaryPanel;
    public DicePanel panel;
    public ScoreBoard scoreBoard;
    private JSplitPane splitPane;
    private int[] punkty = {0,0,0,0,0,0,0,0,0,0,0,0,0};
    private int pozostaleTury;
    private int rerols = 0;
    Timer timer;
    private enum StanGry{
        PierwszyRzyt,
        WTrakcieRerolli,
        ZapisPunktow
    }
    private boolean punktuZaGornaCzescTabeli = true;
    private boolean punktyZaDolnaCzescTabeli = true;
    StanGry stanGry = StanGry.PierwszyRzyt;
    public static void main(String[] args) {
        new DiceGame(new Gracz("Nick", 100)); //usunąć nowego gracza, przekazać jako parametr z mainMenu
    }
    protected class mouseClickListener extends MouseAdapter {
    public void mouseClicked(MouseEvent e) {
        JLabel source = (JLabel) e.getSource();
        if(stanGry != StanGry.ZapisPunktow)
        {
            return;
        }
        if(punktuZaGornaCzescTabeli)
        {
            if(scoreBoard.scores[1][1] == source && punkty[0] > 0 && scoreBoard.nieWykorzystane[0]) //mozliwe punkty za jedynki
            {
                punktuZaGornaCzescTabeli = false;
                source.setText(punkty[0] + "");
                scoreBoard.nieWykorzystane[0] = false;
                scoreBoard.calkowityWynik += punkty[0];
                scoreBoard.wygasTabele(0, 6);
            }
            if(scoreBoard.scores[2][1] == source && punkty[1] > 0 && scoreBoard.nieWykorzystane[1]) //mozliwe punkty za dwójki
            {
                punktuZaGornaCzescTabeli = false;
                source.setText(punkty[1] + "");
                scoreBoard.nieWykorzystane[1] = false;
                scoreBoard.calkowityWynik += punkty[1];
                scoreBoard.wygasTabele(0, 6);
            }
            if(scoreBoard.scores[3][1] == source && punkty[2] > 0 && scoreBoard.nieWykorzystane[2]) //mozliwe punkty za trójki
            {
                punktuZaGornaCzescTabeli = false;
                source.setText(punkty[2] + "");
                scoreBoard.nieWykorzystane[2] = false;
                scoreBoard.calkowityWynik += punkty[2];
                scoreBoard.wygasTabele(0, 6);
            }
            if(scoreBoard.scores[4][1] == source && punkty[3] > 0 && scoreBoard.nieWykorzystane[3]) //mozliwe punkty za czwórki
            {
                punktuZaGornaCzescTabeli = false;
                source.setText(punkty[3] + "");
                scoreBoard.nieWykorzystane[3] = false;
                scoreBoard.calkowityWynik += punkty[3];
                scoreBoard.wygasTabele(0, 6);
            }
            if(scoreBoard.scores[5][1] == source && punkty[4] > 0 && scoreBoard.nieWykorzystane[4]) //mozliwe punkty za piątki
            {
                punktuZaGornaCzescTabeli = false;
                source.setText(punkty[4] + "");
                scoreBoard.nieWykorzystane[4] = false;
                scoreBoard.calkowityWynik += punkty[4];
                scoreBoard.wygasTabele(0, 6);
            }
            if(scoreBoard.scores[6][1] == source && punkty[5] > 0 && scoreBoard.nieWykorzystane[5]) //mozliwe punkty za szóstki
            {
                punktuZaGornaCzescTabeli = false;
                source.setText(punkty[5] + "");
                scoreBoard.nieWykorzystane[5] = false;
                scoreBoard.calkowityWynik += punkty[5];
                scoreBoard.wygasTabele(0, 6);
            }
        }

        if(punktyZaDolnaCzescTabeli)
        {
            if(scoreBoard.scores[7][1] == source && punkty[6] > 0 && scoreBoard.nieWykorzystane[6]) //mozliwe punkty za trzy jednakowe
            {
                punktyZaDolnaCzescTabeli = false;
                source.setText(punkty[6] + "");
                scoreBoard.nieWykorzystane[6] = false;
                scoreBoard.calkowityWynik += punkty[6];
                scoreBoard.wygasTabele(6, 13);
            }
            if(scoreBoard.scores[8][1] == source && punkty[7] > 0 && scoreBoard.nieWykorzystane[7]) //mozliwe punkty za cztery jednakowe
            {
                punktyZaDolnaCzescTabeli = false;
                source.setText(punkty[7] + "");
                scoreBoard.nieWykorzystane[7] = false;
                scoreBoard.calkowityWynik += punkty[7];
                scoreBoard.wygasTabele(6, 13);
            }
            if(scoreBoard.scores[9][1] == source && punkty[8] > 0 && scoreBoard.nieWykorzystane[8]) //mozliwe punkty za fulla
            {
                punktyZaDolnaCzescTabeli = false;
                source.setText(punkty[8] + "");
                scoreBoard.nieWykorzystane[8] = false;
                scoreBoard.calkowityWynik += punkty[8];
                scoreBoard.wygasTabele(6, 13);
            }
            if(scoreBoard.scores[10][1] == source && punkty[9] > 0 && scoreBoard.nieWykorzystane[9]) //mozliwe punkty za małego strita
            {
                punktyZaDolnaCzescTabeli = false;
                source.setText(punkty[9] + "");
                scoreBoard.nieWykorzystane[9] = false;
                scoreBoard.calkowityWynik += punkty[9];
                scoreBoard.wygasTabele(6, 13);
            }
            if(scoreBoard.scores[11][1] == source && punkty[10] > 0 && scoreBoard.nieWykorzystane[10]) //mozliwe punkty za dużego strita
            {
                punktyZaDolnaCzescTabeli = false;
                source.setText(punkty[10] + "");
                scoreBoard.nieWykorzystane[10] = false;
                scoreBoard.calkowityWynik += punkty[10];
                scoreBoard.wygasTabele(6, 13);
            }
            if(scoreBoard.scores[12][1] == source && punkty[11] > 0 && scoreBoard.nieWykorzystane[11]) //mozliwe punkty za generała
            {
                punktyZaDolnaCzescTabeli = false;
                source.setText(punkty[11] + "");
                scoreBoard.nieWykorzystane[11] = false;
                scoreBoard.calkowityWynik += punkty[11];
                scoreBoard.wygasTabele(6, 13);
            }
            if(scoreBoard.scores[13][1] == source && punkty[12] > 0 && scoreBoard.nieWykorzystane[12]) //mozliwe punkty za szansę
            {
                punktyZaDolnaCzescTabeli = false;
                source.setText(punkty[12] + "");
                scoreBoard.nieWykorzystane[12] = false;
                scoreBoard.calkowityWynik += punkty[12];
                scoreBoard.wygasTabele(6, 13);
            }
        }









        scoreBoard.scores[14][1].setText(scoreBoard.calkowityWynik + "");
    }
    }
    private mouseClickListener listener = new mouseClickListener();
    public DiceGame(Gracz gracz) {
        this.gracz = gracz;
        if(gracz.getSaldo() < 50)
        {
            String brakSrodkow = "Niewystarczające środki, aby zagrać w tą grę. Proszę doładować saldo w menu głównym.";

            JOptionPane.showMessageDialog(frame, brakSrodkow, "Niewysratczające środki!", JOptionPane.INFORMATION_MESSAGE);
            new mainMenu(gracz);
        }else {
            gracz.setSaldo(gracz.getSaldo() - 50);
            gracz.aktualizuj();

            String zasady = "Zasady gry w Kości:\n\n" +
                    "Gra składa się z 13 rund, w każdej rundzie można rzucić kośćmi trzykrotnie." +
                    "Po pierwszym rzucie, kliknij na kości, które chcesz zachować.\n" +
                    "Kości pozostawione na stole zostaną rzucone ponownie.\n" +
                    "Na końcu każdej rundy, zaznacz w tabeli pola, za które chcesz dostać punkty z tej rundy.\n" +
                    "Punktacja: \n" +
                    "Jedynki: suma wszystkich wyrzuconych jedynek\n" +
                    "Dwójki: suma wszystkich wyrzuconych dwójek\n" +
                    "Trójki: suma wszystkich wyrzuconych trójek\n" +
                    "Czwórki: suma wszystkich wyrzuconych czwórek\n" +
                    "Piątki: suma wszystkich wyrzuconych piątek\n" +
                    "Szóstki: suma wszystkich wyrzuconych szóstek\n" +
                    "Trzy jednakowe: wyrzucono 3 jednakowe kości - suma oczek wszystkich kości\n" +
                    "Cztery jednakowe: wyrzucono 4 jednakowe kości - suma oczek wszystkich kości\n" +
                    "Full: wyrzucono 3 jednakowe kości i parę - 25 punktów\n" +
                    "Mały strit: wyrzucono cztery kolejne kości (np. 1,2,3,4) - 30 punktów\n" +
                    "Duży strit: wyrzucono pięć kolejnych kości (np. 2,3,4,5,6) - 40 punktów\n" +
                    "Generał: wyrzucono pięć jednakowych kości - 50 punktów\n";

            JOptionPane.showMessageDialog(frame, zasady, "Zasady gry", JOptionPane.INFORMATION_MESSAGE);
            pozostaleTury = MAX_TURY;

            frame = new JFrame("Kości");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize(1500, 800);
            frame.setLocationRelativeTo(null); // Wyśrodkowanie okna na ekranie
            frame.setLayout(new BorderLayout());
            // Ustawienie układu
            scoreBoard = new ScoreBoard();
            scoreBoard.scores[0][0].setText("Saldo: " + gracz.getSaldo());
            scoreBoard.scores[0][1].setText(gracz.getImie());
            scoreBoard.setLayout(new GridLayout(scoreBoard.getRows(), scoreBoard.getCols()));
            scoreBoard.setBackground(Color.WHITE);
            scoreBoard.setPreferredSize(new Dimension(300, 100));
            scoreBoard.setListeners(listener);

            panel = new DicePanel(400, 250);
            panel.setPreferredSize(new Dimension(900, 800));
            panel.setBackground(Color.GRAY);
            panel.rollButton.addActionListener(this);
            panel.powrot.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new mainMenu(gracz);
                    frame.dispose();
                }
            });
            panel.rerollButton.addActionListener(this);
            timer = new Timer(10, panel); // co 0.01s

            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scoreBoard, panel);
            splitPane.setDividerLocation(0.3);
            splitPane.setResizeWeight(0.3);
            splitPane.setContinuousLayout(true);
            splitPane.setDividerSize(0);

            frame.add(splitPane);

            frame.setVisible(true);
            frame.setResizable(false);

            panel.aktualizujRundy(pozostaleTury);
            panel.aktualizujPodejscia(rerols);
            resetujStanGry();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        panel.aktualizujPodejscia((rerols-1 == -1) ? 3 : rerols - 1);
        System.out.println(Arrays.toString(panel.ostateczneWartosci));
        if(panel.ostateczneWartosciIndex == 5) //wszystkie kości zostały zachowane
        {
            rerols = 0; //przejdź do podsumowania
        }
        if (e.getSource() == panel.rerollButton && rerols > 0 && panel.stanKubka)
        {
            rerols--;
            panel.pokazKubek();
            timer.start();
            source.setText("Stop");
            panel.ukryjKosci();
            panel.stanKubka = false;
        }else if(e.getSource() == panel.rerollButton && rerols > 1)
        {
            panel.ukryjKubek();
            //rerols--;
            timer.stop();
            panel.pokazKosci(false);
            source.setText("Reroll");
            panel.stanKubka = true;
            //kostka1.setBounds(xPos + 20, yPos + 210, 30, 30); //poprawne ustawienie miejsca kostek odmawia współpracy
        }else if(e.getSource() == panel.rerollButton && rerols == 1)
        {
            panel.ukryjKubek();
            rerols--;
            timer.stop();
            panel.pokazKosci(true);
            source.setText("Zapisz punkty");
            panel.stanKubka = true;;
            panel.wylaczKosci();
        }else if (e.getSource() == panel.rerollButton && panel.stanKubka && rerols <= 0) //koniec rollowania, przejdz do zapisu punktów
        {
            source.setVisible(false);
            panel.pokazKosci(true);
            panel.przywrocKosci();
            podliczPunkty();
            panel.rollButton.setVisible(true);
            stanGry = StanGry.ZapisPunktow;
            panel.wylaczKosci();
        }

        if (e.getSource() == panel.rollButton) {
            pierwszyRzutWRundzie();
        }
    }

    private void podliczPunkty() {
        panel.przywrocKosci();
        int [] wartosci = panel.ostateczneWartosci;
        Arrays.sort(wartosci);
        int suma = 0;
        int maxJednakowych = 1;
        int jednakowych = 1;
        boolean para = false;
        boolean duzyStrit = true;
        int malyStrit = 1;
        boolean czyMalyStrit = false;
        for (int i = 0; i < 5; i++) {
            suma += wartosci[i];
            punkty[0] += (wartosci[i] == 1) ? 1 : 0; //jedynki
            punkty[1] += (wartosci[i] == 2) ? 2 : 0; //dwójki
            punkty[2] += (wartosci[i] == 3) ? 3 : 0; //trójki
            punkty[3] += (wartosci[i] == 4) ? 4 : 0; //czwórki
            punkty[4] += (wartosci[i] == 5) ? 5 : 0; //piątki
            punkty[5] += (wartosci[i] == 6) ? 6 : 0; //szóstki
        }
        punkty[12] = suma;
        int pierwszaWartosc = wartosci[0];
        for (int i = 1; i < 5; i++) {
            if(wartosci[i] != wartosci[i-1] + 1)
            {
                duzyStrit = false;
            }
            if(wartosci[i] == wartosci[i-1] + 1)
            {
                malyStrit += 1;
                if(malyStrit == 4)
                {
                    czyMalyStrit = true;
                }
            }else if(wartosci[i] != wartosci[i-1])
            {
                malyStrit = 1;
            }
            if(wartosci[i] == pierwszaWartosc)
            {
                jednakowych++;
            }else
            {
                if(jednakowych == 2) //jest dokładnie 2
                {
                    para = true;
                }
                maxJednakowych = Math.max(jednakowych, maxJednakowych);
                jednakowych = 1;
                pierwszaWartosc = wartosci[i];
            }
        }
        if(jednakowych == 2) //powtórzyć po pętli aby sprawdzić czy ostatnie przejście nie spełniło warunku pomijając blok else
        {
            para = true;
        }
        maxJednakowych = Math.max(jednakowych, maxJednakowych);

        punkty[6] += (maxJednakowych >= 3) ? suma : 0; //trzy jednakowe
        punkty[7] += (maxJednakowych >= 4) ? suma : 0; //cztery jednakowe
        punkty[8] += (maxJednakowych == 3 && para) ? 25 : 0; //full
        punkty[9] += (czyMalyStrit) ? 30 : 0; //mały strit
        punkty[10] += (duzyStrit) ? 40 : 0; //duży strit
        punkty[11] += (maxJednakowych >= 5) ? 50 : 0; //generał



        //podświetl dostępne pola
        scoreBoard.podswietlTabele(punkty);

    }
    private void resetujStanGry()
    {
        //pozostaleTury = MAX_TURY;
        stanGry = StanGry.PierwszyRzyt;
        punktuZaGornaCzescTabeli = true;
        punktyZaDolnaCzescTabeli = true;
        panel.pokazKubek();
        scoreBoard.wygasTabele(0, 13);
        //scoreBoard.wyzerujTabele();
        for(int i = 0; i < 13; i++)
        {
            punkty[i] = 0;
        }
        panel.resetujStanGry();
    }
    private void pierwszyRzutWRundzie()
    {
            if(pozostaleTury <= 0)
            {
                String endMessage;
                if(scoreBoard.calkowityWynik >= 100)
                {
                    endMessage = "Wygrałeś 100 żetonów!";
                }else
                {
                    endMessage = "Przegrałeś, otrzymujesz 0 żetonów";
                }


                String msg = "Podsumowanie\n\n" +
                        "Udało ci się uzyskać " + scoreBoard.calkowityWynik + "/100 punktów\n"+endMessage;

                JOptionPane.showMessageDialog(frame, msg, "Podsumowanie", JOptionPane.INFORMATION_MESSAGE);
                new mainMenu(gracz);
                frame.dispose();
            }

            if (panel.stanKubka && pozostaleTury > 0) {
                pozostaleTury--;
                resetujStanGry();
                rerols = 3;
                panel.rollButton.setVisible(false);
                panel.rerollButton.setVisible(true);
                panel.aktualizujRundy(pozostaleTury);
                stanGry = StanGry.WTrakcieRerolli;
                panel.pokazKubek();
                timer.start();
                panel.ukryjKosci();
                panel.stanKubka = false;
            }

    }
}


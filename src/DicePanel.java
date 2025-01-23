import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class DicePanel extends JPanel implements ActionListener {
    final int WIDTH = 1000;
    final int HEIGHT = 800;

    public void setKosciWGrze(int kosciWGrze) {
        this.kosciWGrze = kosciWGrze;
    }

    private int kosciWGrze = 5;
    Image cupImage;
    public JButton rollButton;
    public  JButton rerollButton;
    public JButton powrot;
    public JLabel rerollChances;
    public JLabel pozostaleRundy;
    int xVel, yVel;
    int xPos, yPos;
    boolean stanKubka;
    public int[] ostateczneWartosci = {0,0,0,0,0};
    public int ostateczneWartosciIndex = 0;
    public int[] poszczegolnePunkty = {0,0,0,0,0,0,0,0,0,0,0,0,0};
    Kostka[] kostki = {
            new Kostka(6, 30, 30),
            new Kostka(6, 30, 30),
            new Kostka(6, 30, 30),
            new Kostka(6, 30, 30),
            new Kostka(6, 30, 30),
            new Kostka(6, 30, 30),
    };
    public DicePanel(int xPos, int yPos) {
        this.setLayout(null);
        xVel = 6;
        yVel = 6;
        this.xPos = xPos;
        this.yPos = yPos;
        stanKubka = true;
        cupImage = new ImageIcon("src/cupImg.png").getImage();

        for (int i = 0; i < kosciWGrze; i++)
        {
            Kostka kosc = new Kostka(6, 30, 30);
            kosc.setBounds(xPos + 20 + i * 50, yPos + 210, 30, 30);
            kosc.setPreferredSize(new Dimension(30, 30));
            kosc.setVisible(false);
            kosc.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if(kosc.czyAktywna())
                    {
                        //kosc.setAktywna(false);
                        kosc.setVisible(false);
                        kosciWGrze--;
                        ostateczneWartosci[ostateczneWartosciIndex] = kosc.getSciana();
                        ostateczneWartosciIndex++;
                    }
                }
            });
            kostki[i] = kosc;
            this.add(kostki[i]);
        }


        rollButton = new JButton("Rzuć kośćmi");
        rerollButton = new JButton("Stop");
        rerollChances = new JLabel("");
        powrot = new JButton("Powrót");

        rollButton.setBounds(WIDTH/2 - 60, 25, 120, 30);
        this.add(rollButton);

        rerollButton.setBounds(WIDTH/2 - 60, 25, 120, 30);
        rerollButton.setVisible(false);
        this.add(rerollButton);

        rerollChances.setBounds(20, 0, 280, 80);
        rerollChances.setFont(new Font("Arial", Font.PLAIN, 25));
        this.add(rerollChances);

        pozostaleRundy = new JLabel("");
        pozostaleRundy.setFont(new Font("Arial", Font.PLAIN, 25));
        pozostaleRundy.setBounds(WIDTH - 180, 0, 280, 80);
        this.add(pozostaleRundy);

        powrot.setBounds(WIDTH - 60, HEIGHT - 95, 120, 30);
        this.add(powrot);
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(cupImage, xPos, yPos, null);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(xPos >= WIDTH - cupImage.getWidth(null) - 100|| xPos < 100)
        {
            xVel *= -1;
        }
        if(yPos >= HEIGHT - cupImage.getHeight(null) - 200 || yPos < 50)
        {
            yVel *= -1;
        }
        xPos += xVel;
        yPos += yVel;
        repaint();
    }

    public int getxPos() {
        return xPos;
    }
    public int getyPos() {
        return yPos;
    }
    public int[] podliczPunkty() {
        int [] wartosci = new int[5];
        int suma = 0;
        int maxJednakowych = 1;
        int jednakowych = 1;
        boolean para = false;
        boolean duzyStrit = true;
        int malyStrit = 1;
        boolean czyMalyStrit = false;
        for (int i = 0; i < 5; i++) {
            wartosci[i] = kostki[i].getSciana();
            suma += kostki[i].getSciana();
            poszczegolnePunkty[0] += (kostki[i].getSciana() == 1) ? 1 : 0; //jedynki
            poszczegolnePunkty[1] += (kostki[i].getSciana() == 2) ? 2 : 0; //dwójki
            poszczegolnePunkty[2] += (kostki[i].getSciana() == 3) ? 3 : 0; //trójki
            poszczegolnePunkty[3] += (kostki[i].getSciana() == 4) ? 4 : 0; //czwórki
            poszczegolnePunkty[4] += (kostki[i].getSciana() == 5) ? 5 : 0; //piątki
            poszczegolnePunkty[5] += (kostki[i].getSciana() == 6) ? 6 : 0; //szóstki
            poszczegolnePunkty[12] += kostki[i].getSciana(); //szansa
        }
        Arrays.sort(wartosci);
        int pierwszaWartosc = wartosci[0];
        for (int i = 1; i < 5; i++) {
            if(wartosci[i] != wartosci[i-1])
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
            }else
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

        poszczegolnePunkty[6] += (maxJednakowych >= 3) ? suma : 0; //trzy jednakowe
        poszczegolnePunkty[7] += (maxJednakowych >= 4) ? suma : 0; //cztery jednakowe
        poszczegolnePunkty[8] += (maxJednakowych == 3 && para) ? 25 : 0; //full
        poszczegolnePunkty[9] += (czyMalyStrit) ? 30 : 0; //mały strit
        poszczegolnePunkty[10] += (duzyStrit) ? 40 : 0; //duży strit
        poszczegolnePunkty[11] += (maxJednakowych >= 5) ? 50 : 0; //generał
        return poszczegolnePunkty;
    }
    public void ukryjKosci()
    {
        for (int i = 0; i < 5; i++)
        {
            kostki[i].setVisible(false);
            poszczegolnePunkty[i] = 0;
        }

    }
    public void pokazKosci(boolean ostatniReroll)
    {
        if(ostatniReroll)
        {
            for(int i = ostateczneWartosciIndex; i < 5; i++)
            {
                ostateczneWartosci[i] = kostki[i-ostateczneWartosciIndex].getSciana();
            }
        }
        for (int i = 0; i < kosciWGrze; i++)
        {
            kostki[i].losuj();
            kostki[i].setVisible(true);
        }
    }
    public void przywrocKosci()
    {
        kosciWGrze = 5;
        for(int i = 0; i < kosciWGrze; i++)
        {
            kostki[i].setSciana(ostateczneWartosci[i]);
            kostki[i].setVisible(true);
        }
    }
    public void resetujStanGry()
    {
        ostateczneWartosciIndex = 0;
        kosciWGrze = 5;
        rollButton.setVisible(true);
        rollButton.setText("Rzuć kości");
        rerollButton.setVisible(false);
        rerollButton.setText("Stop");
        for(int i = 0; i < kosciWGrze; i++)
        {
            kostki[i].setAktywna(true);
            kostki[i].setVisible(false);
        }
    }
    public void ukryjKubek()
    {
        cupImage = new ImageIcon("src/nothing.png").getImage();
        repaint();
    }
    public void pokazKubek()
    {
        cupImage = new ImageIcon("src/cupImg.png").getImage();
        repaint();
    }
    public void wylaczKosci()
    {
        for(int i = 0; i < 5; i++)
        {
            kostki[i].setAktywna(false);
        }
    }
    public void aktualizujPodejscia(int rerolls)
    {
        rerollChances.setText("Pozostałe Rerolle: "+rerolls);
    }
    public void aktualizujRundy(int pozostaleRundy)
    {
        this.pozostaleRundy.setText("Pozostałe Rundy: "+pozostaleRundy);
    }
}

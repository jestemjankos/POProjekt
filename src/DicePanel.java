import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DicePanel extends JPanel implements ActionListener {
    final int WIDTH = 1000;
    final int HEIGHT = 800;
    Image cupImage;
    public JButton rollButton;
    int xVel, yVel;
    int xPos, yPos;
    boolean stanKubka;
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

        for (int i = 0; i < 6; i++)
        {
            kostki[i].setBounds(xPos + 20 + i * 50, yPos + 210, 30, 30);
            kostki[i].setPreferredSize(new Dimension(30, 30));
            kostki[i].setVisible(false);
            this.add(kostki[i]);
        }


        rollButton = new JButton("Rzuć kośćmi");


        rollButton.setBounds(WIDTH/2 - 60, 0, 120, 30);
        this.add(rollButton);
        //cupImage = addAncestorListener(new AncestorListener() {});

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
        int general = kostki[0].getSciana();
        boolean czyGeneral = true;
        for (int i = 0; i < 6; i++) {
            poszczegolnePunkty[0] += (kostki[i].getSciana() == 1) ? 1 : 0; //jedynki
            poszczegolnePunkty[1] += (kostki[i].getSciana() == 2) ? 2 : 0; //dwójki
            poszczegolnePunkty[2] += (kostki[i].getSciana() == 3) ? 3 : 0; //trójki
            poszczegolnePunkty[3] += (kostki[i].getSciana() == 4) ? 4 : 0; //czwórki
            poszczegolnePunkty[4] += (kostki[i].getSciana() == 5) ? 5 : 0; //piątki
            poszczegolnePunkty[5] += (kostki[i].getSciana() == 6) ? 6 : 0; //szóstki
            czyGeneral = (czyGeneral && kostki[i].getSciana() == general);
            poszczegolnePunkty[12] += kostki[i].getSciana(); //szansa
        }
        poszczegolnePunkty[11] += (czyGeneral) ? 50 : 0;
        return poszczegolnePunkty;
    }
    public void ukryjKosci()
    {
        for (int i = 0; i < 6; i++)
        {
            kostki[i].setVisible(false);
            poszczegolnePunkty[i] = 0;
        }

    }
    public void pokazKosci()
    {
        for (int i = 0; i < 6; i++)
        {
            kostki[i].losuj();
            kostki[i].setVisible(true);
        }
    }
}

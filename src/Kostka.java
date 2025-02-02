import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.Random;

public class Kostka extends JPanel implements ActionListener {
    private Random generator; // Generator liczb pseudolosowych
    private int liczbaScianek;
    private int width;
    private int height;
    private int xPos;
    private int yPos;
    private int obecnaSciana;
    private Image grafika;
    private boolean aktywna = true;
    public Kostka(int liczbaScianek, int width, int height) {
        this.liczbaScianek = liczbaScianek;
        this.generator = new Random();
        this.width = width;
        this.height = height;
        this.xPos = 0;
        this.yPos = 0;
        obecnaSciana = generator.nextInt(liczbaScianek) + 1;
        grafika = new ImageIcon("src/dice" + obecnaSciana + ".png").getImage();
    }
    public int losuj()
    {
        obecnaSciana = generator.nextInt(liczbaScianek) + 1;
        grafika = new ImageIcon("src/dice" + obecnaSciana + ".png").getImage();
        repaint();
        return obecnaSciana;
    }
    public boolean czyAktywna()
    {
        return aktywna;
    }
    public void setAktywna(boolean aktywna)
    {
        this.aktywna = aktywna;
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(grafika, 0, 0, null);
    }

    public void setPosiotion(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.setBounds(xPos, yPos, width, height);
        repaint();
    }
    public int getSciana()
    {
        return obecnaSciana;
    }
    public void setSciana(int wartosc)
    {
        obecnaSciana = wartosc;
        grafika = new ImageIcon("src/dice" + obecnaSciana + ".png").getImage();
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class ScoreBoard extends JPanel{
    private final int rows = 15;
    private final int cols = 2;
    public int calkowityWynik;
    public boolean[] nieWykorzystane = {true,true,true,true,true,true,true,true,true,true,true,true,true};
    public JLabel[][] scores = {
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},
            new JLabel[]{ new JLabel("0"), new JLabel("1")},

    };

    public ScoreBoard(){
        scores[0][0] = new JLabel("");
        scores[0][1] = new JLabel("Gracz");
        //scores[0][2] = new JLabel("Komputer");
        scores[1][0] = new JLabel("Jedynki");
        scores[2][0] = new JLabel("Dwojki");
        scores[3][0] = new JLabel("Trojki");
        scores[4][0] = new JLabel("Czworki");
        scores[5][0] = new JLabel("Piatki");
        scores[6][0] = new JLabel("Szostki");
        scores[7][0] = new JLabel("Trzy jednakowe");
        scores[8][0] = new JLabel("Cztery jednakowe");
        scores[9][0] = new JLabel("Full");
        scores[10][0] = new JLabel("Mały strit");
        scores[11][0] = new JLabel("Duży strit");
        scores[12][0] = new JLabel("Generał");
        scores[13][0] = new JLabel("Szansa");
        scores[14][0] = new JLabel("Razem");

        calkowityWynik = 0;
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                scores[i][j] = new JLabel("");
                //aktywnoscPunktacji.put(scores[i][j], false);
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                scores[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                scores[i][j].setPreferredSize(new Dimension(10, 47));
                this.add(scores[i][j]);
            }
        }
    }

    public int getCols() {
        return cols;
    }
    public int getRows() {
        return rows;
    }
    public void setListeners(MouseListener listener)
    {
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                scores[i][j].addMouseListener(listener);
            }
        }
    }
}

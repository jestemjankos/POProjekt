import java.io.Serializable;

/**
 * importujemy Serializable żeby można było
 * zapisywać i odczytywać obiekty tej klasy
 * do/z pliku binarnego
 */
public class Gracz implements Serializable {
    private String imie;
    private int saldo;

    /**
     * @param imie the imie
     * @param saldo the saldo gracza
     */
    public Gracz(String imie, int saldo) {

        this.imie = imie;
        this.saldo = saldo;
    }


    public String getImie() {
        return imie;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo( int saldo ){
        this.saldo= saldo;
    }
    public void dodajSaldo( int kwota ){
        saldo+= kwota;
    }

    public void odejmijSaldo(int kwota){
        saldo-= kwota;
    }
}

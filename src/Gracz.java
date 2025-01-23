import java.io.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
    public Gracz() {

        this.imie = null;
        this.saldo = 0;
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

    public static void zapiszGracza(String daneGraczyPath, Map<String, Long> indeks, Gracz gracz) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(daneGraczyPath, "rw")) {
            Long pozycja = indeks.get(gracz.getImie()); // Znajdź pozycję, gdzie zapisać gracza
            if(pozycja == null) //jeśli nie ma takiego gracza, zapisz na końcu
            {
                pozycja = raf.length();
                indeks.put(gracz.getImie(), pozycja); // Zaktualizuj index
            }
            zapiszIndex(indeks, "src/listaGraczy.bin");
            raf.seek(pozycja);          // Przejdź na wskazaną pozycję
            try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(raf.getFD())))) {
                oos.writeObject(gracz); // Zapisz obiekt
            }
        }
    }
    public static Gracz odczytajGracza(RandomAccessFile raf, Map<String, Long> indeks, String nick) throws IOException, ClassNotFoundException {
        Long pozycja = indeks.get(nick); // Znajdź pozycję gracza
        if (pozycja == null) {
            System.out.println("Gracz o nicku '" + nick + "' nie istnieje.");
            return null;
        }
        raf.seek(pozycja); // Przejdź do odpowiedniej pozycji
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(raf.getFD())))) {
            return (Gracz) ois.readObject(); // Odczytaj obiekt
        }
    }
    public static void zapiszIndex(Map<String, Long> index, String nazwaPliku) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nazwaPliku))) {
            oos.writeObject(index);
        }
    }
    public static Map<String, Long> odczytajIndex(String nazwaPliku) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nazwaPliku))) {
            return (Map<String, Long>) ois.readObject();
        }
    }
    public void aktualizuj()
    {
        try (RandomAccessFile raf = new RandomAccessFile("src/daneGraczy.bin", "rw")) {
            Map<String, Long> indeks;

            File plikIndeksu = new File("src/listaGraczy.bin");
            if (plikIndeksu.exists()) {
                indeks = Gracz.odczytajIndex("src/listaGraczy.bin");
            } else {
                indeks = new HashMap<>();
            }

            Gracz.zapiszGracza("src/daneGraczy.bin", indeks, this);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public String toString() {
        return "Gracz{" +
                "imie='" + imie + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}

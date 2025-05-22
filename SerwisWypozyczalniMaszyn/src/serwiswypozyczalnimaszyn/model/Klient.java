package serwiswypozyczalnimaszyn.model; // <--- ZMIANA TUTAJ

/**
 * Klasa Klient.
 * Reprezentuje klienta wypożyczalni, który jest firmą identyfikowaną przez NIP.
 */
public class Klient {

    private int id; 
    private String nazwaFirmy; 
    private String numerNIP; 

    public Klient(int id, String nazwaFirmy, String numerNIP) {
        this.id = id;
        this.nazwaFirmy = nazwaFirmy;
        this.numerNIP = numerNIP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwaFirmy() {
        return nazwaFirmy;
    }

    public void setNazwaFirmy(String nazwaFirmy) {
        this.nazwaFirmy = nazwaFirmy;
    }

    public String getNumerNIP() {
        return numerNIP;
    }

    public void setNumerNIP(String numerNIP) {
        this.numerNIP = numerNIP;
    }

    @Override
    public String toString() {
        return "ID Klienta: " + id + ", Firma: " + nazwaFirmy + ", NIP: " + numerNIP;
    }
}
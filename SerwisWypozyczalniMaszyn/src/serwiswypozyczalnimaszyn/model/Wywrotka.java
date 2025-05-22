package serwiswypozyczalnimaszyn.model; // <--- ZMIANA TUTAJ

/**
 * Klasa Wywrotka dziedzicząca po klasie Pojazd.
 * Reprezentuje specyficzny typ pojazdu budowlanego - wywrotkę.
 */
public class Wywrotka extends Pojazd {

    private double ladownosc; 
    private int liczbaOsi;
    private double dziennaStawka;

    public Wywrotka(int id, String nazwaModelu, int rokProdukcji, double ladownosc, int liczbaOsi, double dziennaStawka) {
        super(id, nazwaModelu, rokProdukcji); 
        this.ladownosc = ladownosc;
        this.liczbaOsi = liczbaOsi;
        this.dziennaStawka = dziennaStawka;
    }

    public double getLadownosc() {
        return ladownosc;
    }

    public void setLadownosc(double ladownosc) {
        this.ladownosc = ladownosc;
    }

    public int getLiczbaOsi() {
        return liczbaOsi;
    }

    public void setLiczbaOsi(int liczbaOsi) {
        this.liczbaOsi = liczbaOsi;
    }

    @Override
    public double getDziennaStawka() {
        return dziennaStawka;
    }

    public void setDziennaStawka(double dziennaStawka) {
        this.dziennaStawka = dziennaStawka;
    }

    @Override
    public String wyswietlSzczegoly() {
        return super.toString() +
               ", Typ: Wywrotka" +
               ", Ładowność: " + ladownosc + " t" +
               ", Liczba osi: " + liczbaOsi;
    }
}
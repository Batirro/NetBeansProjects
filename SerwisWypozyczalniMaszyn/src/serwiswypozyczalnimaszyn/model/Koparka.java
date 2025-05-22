package serwiswypozyczalnimaszyn.model;

/**
 * Klasa Koparka dziedzicząca po klasie Pojazd.
 * Reprezentuje specyficzny typ pojazdu budowlanego - koparkę.
 */
public class Koparka extends Pojazd {

    private double glebokoscKopania; 
    private String typPodwozia;
    private double dziennaStawka;

    public Koparka(int id, String nazwaModelu, int rokProdukcji, double glebokoscKopania, String typPodwozia, double dziennaStawka) {
        super(id, nazwaModelu, rokProdukcji); 
        this.glebokoscKopania = glebokoscKopania;
        this.typPodwozia = typPodwozia;
        this.dziennaStawka = dziennaStawka;
    }

    public double getGlebokoscKopania() {
        return glebokoscKopania;
    }

    public void setGlebokoscKopania(double glebokoscKopania) {
        this.glebokoscKopania = glebokoscKopania;
    }

    public String getTypPodwozia() {
        return typPodwozia;
    }

    public void setTypPodwozia(String typPodwozia) {
        this.typPodwozia = typPodwozia;
    }

    @Override
    public double getDziennaStawka() {
        return this.dziennaStawka;
    }

    /**
     * Ustawia dzienną stawkę za wypożyczenie koparki.
     * @param dziennaStawka Dzienna stawka za wypożyczenie (np. w PLN).
     */
    public void setDziennaStawka(double dziennaStawka) {
        this.dziennaStawka = dziennaStawka;
    }

    @Override
    public String wyswietlSzczegoly() {
        return super.toString() +
               ", Typ: Koparka" +
               ", Głębokość kopania: " + glebokoscKopania + " m" +
               ", Podwozie: " + typPodwozia;
    }
}
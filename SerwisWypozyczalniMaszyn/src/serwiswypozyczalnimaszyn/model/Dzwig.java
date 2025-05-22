package serwiswypozyczalnimaszyn.model; // <--- ZMIANA TUTAJ

/**
 * Klasa Dzwig dziedzicząca po klasie Pojazd.
 * Reprezentuje specyficzny typ pojazdu budowlanego - dźwig.
 */
public class Dzwig extends Pojazd {

    private double maksymalnyUdzwig; 
    private double maksymalnaWysokoscPodnoszenia;
    private double dziennaStawka;

    public Dzwig(int id, String nazwaModelu, int rokProdukcji, double maksymalnyUdzwig, double maksymalnaWysokoscPodnoszenia, double dziennaStawka) {
        super(id, nazwaModelu, rokProdukcji);
        this.maksymalnyUdzwig = maksymalnyUdzwig;
        this.maksymalnaWysokoscPodnoszenia = maksymalnaWysokoscPodnoszenia;
        this.dziennaStawka = dziennaStawka;
    }

    public double getMaksymalnyUdzwig() {
        return maksymalnyUdzwig;
    }

    public void setMaksymalnyUdzwig(double maksymalnyUdzwig) {
        this.maksymalnyUdzwig = maksymalnyUdzwig;
    }

    public double getMaksymalnaWysokoscPodnoszenia() {
        return maksymalnaWysokoscPodnoszenia;
    }

    public void setMaksymalnaWysokoscPodnoszenia(double maksymalnaWysokoscPodnoszenia) {
        this.maksymalnaWysokoscPodnoszenia = maksymalnaWysokoscPodnoszenia;
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
               ", Typ: Dźwig" +
               ", Max udźwig: " + maksymalnyUdzwig + " t" +
               ", Max wysokość podnoszenia: " + maksymalnaWysokoscPodnoszenia + " m";
    }
}
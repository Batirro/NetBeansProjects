package serwiswypozyczalnimaszyn.model;

/**
 * Klasa abstrakcyjna Pojazd.
 * Jest to bazowa klasa dla wszystkich pojazdów w wypożyczalni.
 * Definiuje wspólne cechy i zachowania pojazdów.
 */
public abstract class Pojazd {

    // Pola przechowujące podstawowe informacje o pojeździe
    private int id; // Unikalny identyfikator pojazdu
    private String nazwaModelu; // Nazwa modelu pojazdu, np. "CAT 320D"
    private int rokProdukcji; // Rok produkcji pojazdu
    private boolean dostepny; // Status dostępności pojazdu (true - dostępny, false - wypożyczony)

    /**
     * Konstruktor klasy Pojazd.
     * Używany przez klasy dziedziczące do inicjalizacji wspólnych pól.
     *
     * @param id Unikalny identyfikator pojazdu.
     * @param nazwaModelu Nazwa modelu pojazdu.
     * @param rokProdukcji Rok produkcji pojazdu.
     */
    public Pojazd(int id, String nazwaModelu, int rokProdukcji) {
        this.id = id;
        this.nazwaModelu = nazwaModelu;
        this.rokProdukcji = rokProdukcji;
        this.dostepny = true; // Domyślnie każdy nowy pojazd jest dostępny
    }

    // Gettery i Settery dla pól klasy

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwaModelu() {
        return nazwaModelu;
    }

    public void setNazwaModelu(String nazwaModelu) {
        this.nazwaModelu = nazwaModelu;
    }

    public int getRokProdukcji() {
        return rokProdukcji;
    }

    public void setRokProdukcji(int rokProdukcji) {
        this.rokProdukcji = rokProdukcji;
    }

    public boolean isDostepny() {
        return dostepny;
    }

    public void setDostepny(boolean dostepny) {
        this.dostepny = dostepny;
    }

    /**
     * Metoda abstrakcyjna do wyświetlania szczegółowych informacji o pojeździe.
     * Każda konkretna klasa pojazdu (Koparka, Wywrotka, Dzwig) musi ją zaimplementować.
     *
     * @return String zawierający sformatowane szczegóły pojazdu.
     */
    public abstract String wyswietlSzczegoly();
    /**
     * Metoda abstrakcyjna do pobierania dziennej stawki za wypożyczenie pojazdu.
     * Każda konkretna klasa pojazdu musi zaimplementować tę metodę.
     * @return Dzienna stawka za wypożyczenie (np. w PLN).
     */
    public abstract double getDziennaStawka();
    @Override
    public String toString() {
        // Możemy dodać stawkę do toString dla łatwiejszego debugowania, ale nie jest to konieczne dla GUI
        return "ID: " + id + ", Model: " + nazwaModelu + ", Rok: " + rokProdukcji +
                ", Dostępny: " + (dostepny ? "Tak" : "Nie") +
                ", Stawka/dzień: " + String.format("%.2f", getDziennaStawka()) + " PLN"; // Używamy getDziennaStawka()
    }

}
package po25_lab6_brutkowski;


class Produkt {
    private String nazwa;
    private double cena;
    private double waga;
    private DataWaznosci dataWaznosci;
    
    private static int countStatic_ = 0;
    private int countNonStatic_ = 0;
    
    private static int idStatic_ = 0;
    private int idNonStatic_ = 0;
    
    private int id;
    
    public Produkt() {
        this.nazwa = "ERROR";
        this.cena = -1;
        this.waga = -1;
        this.dataWaznosci = null;
        
        countStatic_++;
        countNonStatic_++;
        idStatic_++;
        idNonStatic_ = idStatic_;
        id = idStatic_;
        
        System.out.println("Wywołano konstruktor domyślny Produkt()");
    }
    
    public Produkt(String nazwa, double cena, double waga) {
        this.nazwa = nazwa;
        this.cena = cena;
        this.waga = waga;
        this.dataWaznosci = null;
        
        countStatic_++;
        countNonStatic_++;
        idStatic_++;
        idNonStatic_ = idStatic_;
        id = idStatic_;
        
        System.out.println("Wywołano konstruktor z parametrami Produkt(" + nazwa + ", " + cena + ", " + waga + ")");
    }
    
    public Produkt(String nazwa, double cena, double waga, DataWaznosci dataWaznosci) {
        this.nazwa = nazwa;
        this.cena = cena;
        this.waga = waga;
        this.dataWaznosci = dataWaznosci;
        
        countStatic_++;
        countNonStatic_++;
        idStatic_++;
        idNonStatic_ = idStatic_;
        id = idStatic_;
        
        System.out.println("Wywołano konstruktor z parametrami i datą ważności Produkt(" + nazwa + ", " + cena + ", " + waga + ", " + dataWaznosci + ")");
    }
    
    public Produkt(Produkt innyProdukt) {
        this.nazwa = innyProdukt.nazwa;
        this.cena = innyProdukt.cena;
        this.waga = innyProdukt.waga;
        if (innyProdukt.dataWaznosci != null) {
            this.dataWaznosci = new DataWaznosci(innyProdukt.dataWaznosci);
        }
        
        countStatic_++;
        countNonStatic_++;
        idStatic_++;
        idNonStatic_ = idStatic_;
        id = idStatic_;
        
        System.out.println("Wywołano konstruktor kopiujący Produkt(Produkt) dla " + nazwa);
    }
    
    public void wyswietl() {
        System.out.println("Produkt: ID=" + id + ", nazwa=" + nazwa + ", cena=" + cena + " zł, waga=" + waga + " kg" + 
                (dataWaznosci != null ? ", data ważności: " + dataWaznosci.toString() : ""));
    }
    
    public static int getCountStatic() {
        return countStatic_;
    }
    
    public static int getIdStatic() {
        return idStatic_;
    }
}


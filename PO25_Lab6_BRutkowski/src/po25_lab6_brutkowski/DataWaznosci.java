package po25_lab6_brutkowski;

class DataWaznosci {
    private int rok;
    private int miesiac;
    private int dzien;
    
    public DataWaznosci(int rok, int miesiac, int dzien) {
        this.rok = rok;
        this.miesiac = miesiac;
        this.dzien = dzien;
        System.out.println("Utworzono datę ważności: " + toString());
    }
    
    // Konstruktor kopiujący
    public DataWaznosci(DataWaznosci innaData) {
        this.rok = innaData.rok;
        this.miesiac = innaData.miesiac;
        this.dzien = innaData.dzien;
        System.out.println("Skopiowano datę ważności: " + toString());
    }
    
    @Override
    public String toString() {
        return dzien + "." + miesiac + "." + rok;
    }
}


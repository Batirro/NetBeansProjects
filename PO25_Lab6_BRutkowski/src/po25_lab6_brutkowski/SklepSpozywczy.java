package po25_lab6_brutkowski;

import java.util.ArrayList;
import java.util.List;

class SklepSpozywczy {
    private String nazwa;
    private List<Produkt> produkty;
    
    public SklepSpozywczy(String nazwa) {
        this.nazwa = nazwa;
        this.produkty = new ArrayList<>();
        System.out.println("Utworzono sklep: " + nazwa);
    }
    
    public void dodajProdukt(Produkt produkt) {
        produkty.add(produkt);
        System.out.println("Dodano produkt do sklepu " + nazwa);
    }
    
    public void wyswietlProdukty() {
        System.out.println("\nProdukty w sklepie " + nazwa + ":");
        for (Produkt produkt : produkty) {
            produkt.wyswietl();
        }
    }
}

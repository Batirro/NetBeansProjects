package po25_lab6_brutkowski;

import java.util.ArrayList;
import java.util.Arrays;

public class PO25_Lab6_BRutkowski {

    public static void main(String[] args) {
        System.out.println("Sklep spożywczy");
        System.out.println("=============================================");
        
        System.out.println("\n--- Zadanie 4.0 - Konstruktory ---");
        
        Produkt produktBlad = new Produkt();
        Produkt produktJablko = new Produkt("Jabłko", 3.99, 0.25);
        Produkt produktJablkoKopiowany = new Produkt(produktJablko);
        
        System.out.println("\nObiekty stworzone osobno:");
        produktBlad.wyswietl();
        produktJablko.wyswietl();
        produktJablkoKopiowany.wyswietl();
        
        // Użycie tablicy
        System.out.println("\nUżycie tablicy:");
        Produkt[] tab = new Produkt[3];
        System.out.println("Elementy tablicy przed inicjalizacją:");
        for (int i = 0; i < tab.length; i++) {
            System.out.println("Element " + i + ": " + (tab[i] == null ? "null" : "nie-null"));
        }
        
        Arrays.fill(tab, new Produkt("Gruszka", 4.99, 0.3));
        
        System.out.println("\nElementy tablicy po uzupełnieniu:");
        for (Produkt produkt : tab) {
            produkt.wyswietl();
        }
        
        System.out.println("\nUżycie kolekcji:");
        ArrayList<Produkt> lista = new ArrayList<>();
        lista.add(new Produkt());
        lista.add(new Produkt("Pomarańcza", 5.99, 0.35));
        lista.add(new Produkt(lista.get(1)));
        
        System.out.println("\nElementy kolekcji:");
        for (Produkt produkt : lista) {
            produkt.wyswietl();
        }
        
        System.out.println("\n--- Zadanie 4.1 - Pola statyczne vs niestatyczne ---");
        
        System.out.println("Liczba wszystkich utworzonych produktów: " + Produkt.getCountStatic());
        
        System.out.println("\n--- Zadanie 4.2 - Zależność między klasami ---");
        DataWaznosci data = new DataWaznosci(2024, 4, 30);
        Produkt produktZData = new Produkt("Mleko", 3.49, 1.0, data);
        produktZData.wyswietl();
        
        System.out.println("\n--- Zadanie 4.3 - Enkapsulacja i modyfikatory dostępu ---");
        System.out.println("Statystyki produktów (dostęp do pól statycznych):");
        System.out.println("Liczba wszystkich utworzonych produktów: " + Produkt.getCountStatic());
        System.out.println("Ostatnie przyznane ID: " + Produkt.getIdStatic());
        
        SklepSpozywczy sklep = new SklepSpozywczy("Spożywczak u Bartka");
        sklep.dodajProdukt(produktJablko);
        sklep.dodajProdukt(produktZData);
        sklep.wyswietlProdukty();
    }
}

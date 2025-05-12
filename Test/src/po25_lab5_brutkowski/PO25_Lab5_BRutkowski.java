package po25_lab5_brutkowski;

import static po25_lab5_brutkowski.ZapisDoPlikow.zapisDoPlikow;

public class PO25_Lab5_BRutkowski {

    public static void main(String[] args) {
        // Zadanie 3.0
        Tour tour = new Tour();

        tour.printCountriesCities();
        tour.printCities();
        tour.printUniqueCities();

        zapisDoPlikow(tour);
    }

}


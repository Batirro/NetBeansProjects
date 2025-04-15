package po25_lab7_brutkowski_5;

public class KalkulatorZysku {

    public static double obliczZysk(double dystans, double wynagrodzenie, SrodekTransportu srodek) {
        if (srodek == null) {
            throw new IllegalArgumentException("Środek transportu nie może być null.");
        }
        double kosztTransportu = srodek.obliczKoszt(dystans);
        return wynagrodzenie - kosztTransportu;
    }
}
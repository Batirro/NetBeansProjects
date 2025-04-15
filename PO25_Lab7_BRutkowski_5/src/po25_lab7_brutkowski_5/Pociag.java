package po25_lab7_brutkowski_5;

public class Pociag implements SrodekTransportu {

    @Override
    public double obliczKoszt(double dystans) {
        return 20.0 * dystans;
    }

     @Override
    public String toString() {
        return "Pociąg";
    }
}
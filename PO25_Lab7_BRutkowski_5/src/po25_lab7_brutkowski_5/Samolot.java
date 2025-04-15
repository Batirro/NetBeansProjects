package po25_lab7_brutkowski_5;

public class Samolot implements SrodekTransportu {

    @Override
    public double obliczKoszt(double dystans) {
        // Koszt samolotu = 100 * S^2
        return 100.0 * dystans * dystans;
    }

    @Override
    public String toString() {
        return "Samolot";
    }
}
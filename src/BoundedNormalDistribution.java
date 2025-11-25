import java.security.SecureRandom;

public class BoundedNormalDistribution {
    public static int generateBoundedNormalDoubleInt(double mean, double stdDev, double min, double max) {
        SecureRandom r = new SecureRandom();
        double x;
        do {
            double standardNormal = r.nextGaussian();
            x = mean + standardNormal * stdDev;
        } while (x <= min || x >= max);

        return (int) Math.round(x);
    }

    public static double generateBoundedNormal(double mean, double stdDev, int min, int max) {
        SecureRandom r = new SecureRandom();
        double x;
        do {
            int standardNormal = (int) r.nextGaussian();
            x = mean + standardNormal * stdDev;
        } while (x <= min || x >= max);

        return x;
    }

    public static int generateBoundedNormal(int mean, int stdDev, int min, int max) {
        SecureRandom r = new SecureRandom();
        int x;
        do {
            int standardNormal = (int) r.nextGaussian();
            x = mean + standardNormal * stdDev;
        } while (x <= min || x >= max);

        return x;
    }
}

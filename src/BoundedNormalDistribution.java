import java.security.SecureRandom;

public class BoundedNormalDistribution {
    public static double generateBoundedNormal(double mean, double stdDev, double min, double max) {
        SecureRandom r = new SecureRandom();
        double x;
        do {
            double standardNormal = r.nextGaussian();
            x = mean + standardNormal * stdDev;
        } while (x <= min || x >= max);

        return x;
    }
}

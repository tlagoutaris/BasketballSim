package result;

public class TimeStamp {
    int period;
    double timeRemainingInPeriod;
    double shotClock;

    public TimeStamp(int period, double timeRemainingInPeriod, double shotClock) {
        this.period = period;
        this.timeRemainingInPeriod = timeRemainingInPeriod;
        this.shotClock = shotClock;
    }

    public int getPeriod() {
        return period;
    }

    public double getTimeRemainingInPeriod() {
        return timeRemainingInPeriod;
    }

    public double getShotClock() {
        return shotClock;
    }

    public void printTime() {
        System.out.printf("\n%d: %f - %f\n", this.period, this.timeRemainingInPeriod, this.shotClock);
    }
}

public class Config {
    // Simulation Parameters
    public static final int NUM_SECONDS_POSSESSION = 24;
    public static final int NUM_MINUTES_PER_PERIOD = 12;
    public static final int NUM_MINUTES_PER_OVERTIME_PERIOD = 5;
    public static final int NUM_PERIODS = 4;

    // Simulation averages
    public static final double BASE_TWO_POINT_PERCENTAGE = 54;
    public static final double BASE_THREE_POINT_PERCENTAGE = 36;
    public static final double BASE_AVERAGE_POSSESSION_SECONDS = 14.4;
    public static final double BASE_AVERAGE_POSSESSION_STDDEV = 5.0;
    public static final double BASE_FOUL_SUCCESS_CHANCE = 40;
    public static final double BASE_STEAL_SUCCESS_CHANCE = 40;

    // Simulation tendencies
    public static final double BASE_TWO_POINT_ATTEMPT_TENDENCY = 60;
    public static final double BASE_THREE_POINT_ATTEMPT_TENDENCY = 40;
    public static final double BASE_STEAL_ATTEMPT_TENDENCY = 18;
    public static final double BASE_FOUL_TENDENCY = 18;
    public static final double BASE_TENDENCY_STDDEV = 10;

    // Attribute generation parameters
    public static final double BASE_ATTRIBUTE_MEAN = 50;
    public static final double BASE_ATTRIBUTE_STDDEV = 16;

    // Normal distribution parameters
    public static final double LOWER_BOUND = 0.0;
    public static final double UPPER_BOUND = 100.0;

    // Multipliers
    public static final double ATTRIBUTE_DIFFERENCE_MULTIPLIER = 0.5;
}

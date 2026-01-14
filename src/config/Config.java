package config;

public class Config {
    // Simulation Parameters
    public static final int NUM_SECONDS_POSSESSION = 24;
    public static final int NUM_MINUTES_PER_PERIOD = 12;
    public static final int NUM_MINUTES_PER_OVERTIME_PERIOD = 5;
    public static final int NUM_PERIODS = 4;
    public static final int REGULATION_PERIOD_LENGTH_SECONDS = NUM_MINUTES_PER_PERIOD * 60;
    public static final int OVERTIME_PERIOD_LENGTH_SECONDS = NUM_MINUTES_PER_OVERTIME_PERIOD * 60;

    // Simulation averages
    public static final double BASE_TWO_POINT_PERCENTAGE = 54;
    public static final double BASE_THREE_POINT_PERCENTAGE = 36;
    public static final double BASE_FREE_THROW_PERCENTAGE = 75;
    public static final double BASE_AVERAGE_POSSESSION_SECONDS = 14.4;
    public static final double BASE_AVERAGE_POSSESSION_STDDEV = 5.0;
    public static final double BASE_FOUL_SUCCESS_CHANCE = 40;
    public static final double BASE_STEAL_SUCCESS_CHANCE = 40;
    public static final double BASE_BALL_OUT_OF_BOUNDS_CHANCE = 0.5;
    public static final double BASE_PASS_BALL_OUT_OF_BOUNDS_CHANCE = 1;
    public static final double BASE_PASS_DEFLECTED_CHANCE = 4;
    public static final double BASE_PASS_INTERCEPTED_CHANCE = 3;
    public static final double BASE_AVERAGE_SHOTS_PER_TEAM = 89.4;
    public static final double BASE_AVERAGE_PASSES_PER_TEAM = 292;
    public static final double BASE_AVERAGE_POSSESSIONS_PER_TEAM = 104;
    public static final double BASE_TIME_NEEDED_TO_SHOOT = 0.4;
    public static final double BASE_POSSESSION_ABORTING_SHOT = 2.0;
    public static final double BASE_MOMENTS_PER_POSSESSION = (BASE_AVERAGE_PASSES_PER_TEAM + BASE_AVERAGE_SHOTS_PER_TEAM/ BASE_AVERAGE_POSSESSIONS_PER_TEAM);

    // Simulation tendencies
    public static final double BASE_TWO_POINT_ATTEMPT_TENDENCY = 60;
    public static final double BASE_THREE_POINT_ATTEMPT_TENDENCY = 100 - BASE_TWO_POINT_ATTEMPT_TENDENCY;
    public static final double BASE_PASS_ATTEMPT_TENDENCY = (BASE_AVERAGE_PASSES_PER_TEAM / BASE_AVERAGE_POSSESSIONS_PER_TEAM) * 10;
    public static final double BASE_SHOT_ATTEMPT_TENDENCY = (BASE_PASS_ATTEMPT_TENDENCY / 2);
    public static final double BASE_DRIBBLE_TENDENCY = BASE_SHOT_ATTEMPT_TENDENCY;
    public static final double BASE_STEAL_ATTEMPT_TENDENCY = 18;
    public static final double BASE_INTERCEPTION_TENDENCY = 4;
    public static final double BASE_DEFLECTION_TENDENCY = 6;
    public static final double BASE_OFFENSIVE_REBOUND_TENDENCY = 26;
    public static final double BASE_DEFENSIVE_REBOUND_TENDENCY = 100 - BASE_OFFENSIVE_REBOUND_TENDENCY;
    public static final double BASE_FOUL_TENDENCY = 18;

    // Attribute generation parameters
    public static final double BASE_ATTRIBUTE_MEAN = 50;
    public static final double BASE_ATTRIBUTE_STDDEV = 16;
    public static final double BASE_TENDENCY_STDDEV = 10;
    public static final double BASE_MOMENT_LENGTH_STDDEV = 3;

    // Normal distribution parameters
    public static final double LOWER_BOUND = 0.0;
    public static final double UPPER_BOUND = 100.0;

    // Multipliers
    public static final double ATTRIBUTE_DIFFERENCE_MULTIPLIER = 0.5;

    // NBA Rules
    public static final double SHOT_CLOCK_RESET_ON_OFFENSIVE_REBOUND = 14.0;
    public static final double SHOT_CLOCK_RESET_ON_FOUL = 14.0;
}

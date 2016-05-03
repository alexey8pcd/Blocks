package main.bonus;

/**
 * @author Alexey
 */
public enum BonusType {
    NEARED("N", 0.3), 
    LINE("L", 0.2), 
    TIMES_2("2", 0.1), 
    TIMES_3("3", 0.05), 
    MIX_COLOR("C", 0.25);
    private final String name;
    private final double probability;

    private BonusType(String name, double probability) {
        this.name = name;
        this.probability = probability;
    }

    public String getName() {
        return name;
    }

    public double getProbability() {
        return probability;
    }

    /**
     * Создает суперблок с учетом вероятности
     *
     * @param value - вероятность от 0 до 1
     * @return
     */
    public static BonusType createWithProbability(double value) {
        if (value < 0 || value > 1) {
            return null;
        }
        double prb = NEARED.probability;
        if (value < prb) {
            return NEARED;
        }
        prb += LINE.probability;
        if (value < prb) {
            return LINE;
        }
        prb += MIX_COLOR.probability;
        if (value < prb) {
            return MIX_COLOR;
        }
        prb += TIMES_2.probability;
        if (value < prb) {
            return TIMES_2;
        }
        return TIMES_3;
    }

    public static BonusType create(int number) {
        switch (number) {
            case 0:
                return NEARED;
            case 1:
                return LINE;
            case 2:
                return TIMES_2;
            case 3:
                return TIMES_3;
            default:
                return MIX_COLOR;
        }
    }

}

package main.bonus;

public class Bonus {

    private final BonusType type;

    public Bonus(BonusType type, int size) {
        this.type = type;
    }

    public BonusType getType() {
        return type;
    }

    public Bonus(int type) {
        this.type = BonusType.create(type);
    }

}

package homework3;

public abstract class Hero {
    protected static final int MIN_STAT_VALUE = 1;

    private final String name;
    private final int maxHealth;
    private final int baseAttack;
    private int health;

    public Hero(String name, int maxHealth, int baseAttack) {
        if (name == null || name.length() == 0) {
            this.name = "Герой";
        } else {
            this.name = name;
        }

        if (maxHealth < MIN_STAT_VALUE) {
            this.maxHealth = MIN_STAT_VALUE;
        } else {
            this.maxHealth = maxHealth;
        }

        if (baseAttack < MIN_STAT_VALUE) {
            this.baseAttack = MIN_STAT_VALUE;
        } else {
            this.baseAttack = baseAttack;
        }

        health = this.maxHealth;
    }

    public String getName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getHealth() {
        return health;
    }

    public abstract void attack(Hero target);

    public abstract ActionType makeTurn(Hero target);

    public abstract String getResourceInfo();

    public void takeDamage(int damage) {
        if (damage <= 0) {
            return;
        }

        health = health - damage;

        if (health < 0) {
            health = 0;
        }

        if (health == 0) {
            System.out.println(name + " пал в бою!");
        }
    }

    public void heal(int amount) {
        if (amount <= 0) {
            return;
        }

        health = health + amount;

        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }
}
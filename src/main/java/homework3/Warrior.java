package homework3;

public class Warrior extends Hero implements Castable, Restable {
    private final int armor;

    public Warrior(String name, int maxHealth, int baseAttack, int armor) {
        super(name, maxHealth, baseAttack);

        if (armor < MIN_STAT_VALUE) {
            this.armor = MIN_STAT_VALUE;
        } else {
            this.armor = armor;
        }
    }

    @Override
    public void takeDamage(int damage) {
        int finalDamage = damage - armor;

        if (finalDamage < MIN_STAT_VALUE) {
            finalDamage = MIN_STAT_VALUE;
        }

        super.takeDamage(finalDamage);
    }

    @Override
    public void attack(Hero target) {
        if (target == null) {
            return;
        }

        System.out.println(
                getName() + " атакует " + target.getName()
                        + " на " + getBaseAttack() + " урона"
        );

        target.takeDamage(getBaseAttack());
    }

    @Override
    public boolean canCast() {
        return getHealth() * 2 < getMaxHealth();
    }

    @Override
    public void castSpecialSkill(Hero target) {
        if (target == null) {
            return;
        }

        if (!canCast()) {
            System.out.println(
                    "Воин попытался ударить щитом, но потерял равновесие!"
            );
            return;
        }

        int damage = getBaseAttack() + armor * 2;

        System.out.println(
                getName() + " использует удар щитом на "
                        + damage + " урона"
        );

        target.takeDamage(damage);
    }

    @Override
    public boolean needsRest() {
        return getHealth() * 100 < getMaxHealth() * 15;
    }

    @Override
    public void rest() {
        heal(armor * 2);

        System.out.println(
                getName() + " использует второе дыхание"
        );
    }

    @Override
    public ActionType makeTurn(Hero target) {
        if (canCast()) {
            castSpecialSkill(target);
            return ActionType.SPECIAL_SKILL;
        }

        if (needsRest()) {
            rest();
            return ActionType.REST;
        }

        attack(target);
        return ActionType.BASE_ATTACK;
    }

    @Override
    public String getResourceInfo() {
        return "Броня: " + armor;
    }
}
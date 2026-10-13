package homework3;

public class Mage extends Hero implements Castable, Restable {
    private final int maxMana;
    private int mana;

    public Mage(String name, int maxHealth, int baseAttack, int maxMana) {
        super(name, maxHealth, baseAttack);

        if (maxMana < MIN_STAT_VALUE) {
            this.maxMana = MIN_STAT_VALUE;
        } else {
            this.maxMana = maxMana;
        }

        mana = this.maxMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getMana() {
        return mana;
    }

    @Override
    public void attack(Hero target) {
        if (target == null) {
            return;
        }

        if (mana >= 10) {
            int damage = getBaseAttack() * 2;
            mana = mana - 10;

            System.out.println(
                    getName() + " использует магический выстрел на "
                            + damage + " урона"
            );

            target.takeDamage(damage);
        } else {
            int damage = getBaseAttack() / 2;

            mana = mana + 5;

            if (mana > maxMana) {
                mana = maxMana;
            }

            System.out.println(
                    getName() + " бьет посохом на "
                            + damage + " урона"
            );

            target.takeDamage(damage);
        }
    }

    @Override
    public boolean canCast() {
        return mana >= 25;
    }

    @Override
    public void castSpecialSkill(Hero target) {
        if (target == null) {
            return;
        }

        if (!canCast()) {
            System.out.println(
                    getName() + " не хватает маны для заклинания"
            );
            return;
        }

        int damage = getBaseAttack() * 3;
        mana = mana - 25;

        System.out.println(
                getName() + " использует огненную глыбу на "
                        + damage + " урона"
        );

        target.takeDamage(damage);
    }

    @Override
    public boolean needsRest() {
        return mana == 0
                || getHealth() * 100 < getMaxHealth() * 30;
    }

    @Override
    public void rest() {
        mana = maxMana;
        heal(getBaseAttack());

        System.out.println(
                getName() + " использует исцеляющую медитацию"
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
        return "Мана: " + mana + "/" + maxMana;
    }
}
package homework3;

public class Arena {
    private final Hero fighter1;
    private final Hero fighter2;
    private int roundCounter;

    public Arena(Hero fighter1, Hero fighter2) {
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
        roundCounter = 0;
    }

    public void startTournament() {
        if (fighter1 == null || fighter2 == null) {
            System.out.println("Бой не может начаться");
            return;
        }

        while (fighter1.isAlive() && fighter2.isAlive()) {
            roundCounter++;

            System.out.println();
            System.out.println("Раунд " + roundCounter);

            Hero attacker;
            Hero defender;

            if (Math.random() < 0.5) {
                attacker = fighter1;
                defender = fighter2;
            } else {
                attacker = fighter2;
                defender = fighter1;
            }

            ActionType action = attacker.makeTurn(defender);

            printAction(attacker, action);

            if (!defender.isAlive()) {
                break;
            }

            action = defender.makeTurn(attacker);

            printAction(defender, action);

            if (!attacker.isAlive()) {
                break;
            }

            printStatus();
        }

        if (fighter1.isAlive()) {
            System.out.println();
            System.out.println(
                    "Победитель: " + fighter1.getName()
            );
        } else {
            System.out.println();
            System.out.println(
                    "Победитель: " + fighter2.getName()
            );
        }
    }

    private void printAction(Hero fighter, ActionType action) {
        System.out.println(
                "Игрок " + fighter.getName()
                        + " совершил действие: "
                        + action.getTitle()
        );
    }

    private void printStatus() {
        System.out.println(
                fighter1.getName()
                        + ": "
                        + fighter1.getHealth()
                        + "/"
                        + fighter1.getMaxHealth()
                        + ", "
                        + fighter1.getResourceInfo()
        );

        System.out.println(
                fighter2.getName()
                        + ": "
                        + fighter2.getHealth()
                        + "/"
                        + fighter2.getMaxHealth()
                        + ", "
                        + fighter2.getResourceInfo()
        );
    }
}
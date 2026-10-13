package homework3;

import java.util.Scanner;

public class Main {
    public static Hero createHero(Scanner scanner, int number) {
        System.out.println();
        System.out.println("Создание героя " + number);
        System.out.println("1 - Воин");
        System.out.println("2 - Маг");
        System.out.print("Тип героя: ");

        int type = readInt(scanner);

        while (type != 1 && type != 2) {
            System.out.print("Введите 1 или 2: ");
            type = readInt(scanner);
        }

        System.out.print("Имя: ");
        String name = scanner.next();

        System.out.print("Максимальное здоровье: ");
        int maxHealth = readInt(scanner);

        System.out.print("Базовая атака: ");
        int baseAttack = readInt(scanner);

        if (type == 1) {
            System.out.print("Броня: ");
            int armor = readInt(scanner);

            return new Warrior(
                    name,
                    maxHealth,
                    baseAttack,
                    armor
            );
        }

        System.out.print("Максимальная мана: ");
        int maxMana = readInt(scanner);

        return new Mage(
                name,
                maxHealth,
                baseAttack,
                maxMana
        );
    }

    public static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Введите целое число: ");
            scanner.next();
        }

        return scanner.nextInt();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Hero fighter1 = createHero(scanner, 1);
        Hero fighter2 = createHero(scanner, 2);

        Arena arena = new Arena(fighter1, fighter2);

        arena.startTournament();
    }
}
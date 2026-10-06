package task1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Account account = new Account(
                12345,
                999,
                10000.00,
                BankType.AUM
        );

        System.out.println("Добро пожаловать в банкомат!");

        System.out.print("Введите номер карты: ");

        if (!scanner.hasNextInt()) {
            System.out.println(
                    "Ошибка: номер карты должен быть числом."
            );
            return;
        }

        int cardNumber = scanner.nextInt();

        System.out.print("Введите пин-код: ");

        if (!scanner.hasNextInt()) {
            System.out.println(
                    "Ошибка: пин-код должен быть числом."
            );
            return;
        }

        int pinCode = scanner.nextInt();

        if (cardNumber != account.getCardNumber()
                || pinCode != account.getPinCode()) {
            System.out.println("Ошибка доступа.");
            return;
        }

        System.out.println("Авторизация успешна.");
        System.out.println(account);

        CashMachine cashMachine = new CashMachine();

        double currentBalance = account.getBalance();

        System.out.print("Введите сумму внесения: ");

        if (!scanner.hasNextDouble()) {
            System.out.println(
                    "Ошибка: сумма должна быть числом."
            );
            return;
        }

        double depositAmount = scanner.nextDouble();

        currentBalance = cashMachine.deposit(
                currentBalance,
                depositAmount
        );

        System.out.printf(
                "Баланс после внесения: %.2f руб.%n",
                currentBalance
        );

        System.out.print("Введите сумму снятия: ");

        if (!scanner.hasNextDouble()) {
            System.out.println(
                    "Ошибка: сумма должна быть числом."
            );
            return;
        }

        double withdrawalAmount = scanner.nextDouble();

        double commission = cashMachine.applyCommission(
                withdrawalAmount,
                account.getBankType()
        );

        System.out.printf(
                "Комиссия банка: %.2f руб.%n",
                commission
        );

        currentBalance = cashMachine.withdraw(
                currentBalance,
                withdrawalAmount,
                account.getBankType()
        );

        System.out.printf(
                "Баланс после снятия: %.2f руб.%n",
                currentBalance
        );
    }
}

package task1;

public class CashMachine implements WithdrawalOperations, DepositOperations {

    @Override
    public double deposit(
            double currentBalance,
            Double depositAmount
    ) {
        if (depositAmount == null || depositAmount <= 0) {
            return round(currentBalance);
        }

        return round(currentBalance + depositAmount);
    }

    @Override
    public double withdraw(
            double currentBalance,
            Double withdrawalAmount,
            BankType bankType
    ) {
        if (withdrawalAmount == null || withdrawalAmount <= 0) {
            return round(currentBalance);
        }

        double commission = applyCommission(
                withdrawalAmount,
                bankType
        );

        double totalAmount = withdrawalAmount + commission;

        if (totalAmount > currentBalance) {
            System.out.println("Недостаточно средств.");
            return round(currentBalance);
        }

        return round(currentBalance - totalAmount);
    }

    public double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

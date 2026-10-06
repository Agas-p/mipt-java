package task1;

public class Account {

    public int cardNumber;
    public int pinCode;
    public double balance;
    public BankType bankType;

    public Account(
            int cardNumber,
            int pinCode,
            double balance,
            BankType bankType
    ) {
        if (cardNumber >= 10000 && cardNumber <= 99999) {
            this.cardNumber = cardNumber;
        } else {
            this.cardNumber = 10000;
        }

        if (pinCode >= 100 && pinCode <= 999) {
            this.pinCode = pinCode;
        } else {
            this.pinCode = 100;
        }

        if (balance >= 0) {
            this.balance = round(balance);
        } else {
            this.balance = 0.0;
        }

        if (bankType == null) {
            this.bankType = BankType.NEO;
        } else {
            this.bankType = bankType;
        }
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public int getPinCode() {
        return pinCode;
    }

    public double getBalance() {
        return balance;
    }

    public BankType getBankType() {
        return bankType;
    }

    public double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return bankType.getBankName()
                + " Карта: "
                + cardNumber
                + ", Баланс: "
                + String.format("%.2f", balance)
                + " руб.";
    }
}

package task1;

public enum BankType {
    NEO("НеоКредит Банк", 0.01),
    AUM("Арум Финтех", 0.02),
    VTA("Вектор Альянс Банк", 0.00);

    public String bankName;
    public double commission;

    BankType(String bankName, double commission) {
        this.bankName = bankName;
        this.commission = commission;
    }

    public String getBankName() {
        return bankName;
    }

    public double getCommission() {
        return commission;
    }
}
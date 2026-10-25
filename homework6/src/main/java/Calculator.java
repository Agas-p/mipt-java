public class Calculator<T extends Number> {
    public double sum(T firstNumber, T secondNumber) {
        if (firstNumber == null || secondNumber == null) {
            return Double.NaN;
        }

        return firstNumber.doubleValue() + secondNumber.doubleValue();
    }

    public double subtract(T firstNumber, T secondNumber) {
        if (firstNumber == null || secondNumber == null) {
            return Double.NaN;
        }

        return firstNumber.doubleValue() - secondNumber.doubleValue();
    }

    public double multiply(T firstNumber, T secondNumber) {
        if (firstNumber == null || secondNumber == null) {
            return Double.NaN;
        }

        return firstNumber.doubleValue() * secondNumber.doubleValue();
    }

    public double divide(T firstNumber, T secondNumber) {
        if (firstNumber == null || secondNumber == null) {
            return Double.NaN;
        }

        if (secondNumber.doubleValue() == 0.0) {
            return Double.NaN;
        }

        return firstNumber.doubleValue() / secondNumber.doubleValue();
    }

    public static void main(String[] args) {
        final Calculator<Integer> intCalculator = new Calculator<>();

        System.out.println(intCalculator.sum(5, 3));
        System.out.println(intCalculator.subtract(5, 3));
        System.out.println(intCalculator.multiply(5, 3));
        System.out.println(intCalculator.divide(6, 3));

        final Calculator<Double> doubleCalculator = new Calculator<>();

        System.out.println(doubleCalculator.divide(10.0, 4.0));
        System.out.println(doubleCalculator.divide(10.0, 0.0));
        System.out.println(doubleCalculator.sum(null, 5.0));
    }
}

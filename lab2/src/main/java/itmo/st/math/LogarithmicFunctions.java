package itmo.st.math;

public class LogarithmicFunctions {

    private final BaseFunctions baseFunctions;

    public LogarithmicFunctions(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
    }

    public LogarithmicFunctions() {
        this.baseFunctions = new BaseFunctions();
    }

    /**
     * Вычисляет логарифм по основанию a: log_a(x) = ln(x) / ln(a)
     */
    public double log(double base, double x) {
        validateLogArguments(base, x);
        return baseFunctions.ln(x) / baseFunctions.ln(base);
    }

    /**
     * Проверяет корректность аргументов логарифма
     */
    private void validateLogArguments(double base, double x) {
        if (base <= 0 || base == 1) {
            throw new IllegalArgumentException("Base must be positive and not equal to 1");
        }
        if (x <= 0) {
            throw new IllegalArgumentException("Argument must be positive");
        }
    }

    /**
     * Вычисляет натуральный логарифм (прокси-метод для удобства)
     */
    public double ln(double x) {
        return baseFunctions.ln(x);
    }

    /**
     * Логарифм по основанию 2
     */
    public double log2(double x) {
        return log(2, x);
    }

    /**
     * Логарифм по основанию 3
     */
    public double log3(double x) {
        return log(3, x);
    }

    /**
     * Логарифм по основанию 5
     */
    public double log5(double x) {
        return log(5, x);
    }

    /**
     * Логарифм по основанию 10
     */
    public double log10(double x) {
        return log(10, x);
    }
}

package itmo.st.math;

public class LogarithmicFunctions {
    /**
     * Вычисляет логарифм по основанию a: log_a(x) = ln(x) / ln(a)
     */
    public static double log(double base, double x) {
        validateLogArguments(base, x);
        return BaseFunctions.ln(x) / BaseFunctions.ln(base);
    }

    /**
     * Проверяет корректность аргументов логарифма
     */
    private static void validateLogArguments(double base, double x) {
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
    public static double ln(double x) {
        return BaseFunctions.ln(x);
    }

    /**
     * Логарифм по основанию 2
     */
    public static double log2(double x) {
        return log(2, x);
    }

    /**
     * Логарифм по основанию 3
     */
    public static double log3(double x) {
        return log(3, x);
    }

    /**
     * Логарифм по основанию 5
     */
    public static double log5(double x) {
        return log(5, x);
    }

    /**
     * Логарифм по основанию 10
     */
    public static double log10(double x) {
        return log(10, x);
    }
}

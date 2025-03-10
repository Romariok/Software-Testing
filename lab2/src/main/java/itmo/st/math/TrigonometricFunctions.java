package itmo.st.math;

public class TrigonometricFunctions {
    /**
     * Вычисляет синус через косинус: sin(x) = cos(x - π/2)
     */
    public static double sin(double x) {
        return BaseFunctions.cos(x - Math.PI / 2);
    }

    /**
     * Вычисляет тангенс: tan(x) = sin(x) / cos(x)
     */
    public static double tan(double x) {
        double cosValue = cos(x);
        checkDivisionByZero(cosValue, "Tangent is undefined at x = " + x);
        return sin(x) / cosValue;
    }

    /**
     * Вычисляет котангенс: cot(x) = cos(x) / sin(x)
     */
    public static double cot(double x) {
        double sinValue = sin(x);
        checkDivisionByZero(sinValue, "Cotangent is undefined at x = " + x);
        return cos(x) / sinValue;
    }

    /**
     * Вычисляет косеканс: csc(x) = 1 / sin(x)
     */
    public static double csc(double x) {
        double sinValue = sin(x);
        checkDivisionByZero(sinValue, "Cosecant is undefined at x = " + x);
        return 1 / sinValue;
    }

    /**
     * Вычисляет секанс: sec(x) = 1 / cos(x)
     */
    public static double sec(double x) {
        double cosValue = cos(x);
        checkDivisionByZero(cosValue, "Secant is undefined at x = " + x);
        return 1 / cosValue;
    }

    /**
     * Вспомогательный метод для проверки деления на ноль
     */
    private static void checkDivisionByZero(double value, String message) {
        if (Math.abs(value) < BaseFunctions.EPSILON) {
            throw new ArithmeticException(message);
        }
    }

    /**
     * Вычисляет косинус (прокси-метод для удобства)
     */
    public static double cos(double x) {
        return BaseFunctions.cos(x);
    }
}

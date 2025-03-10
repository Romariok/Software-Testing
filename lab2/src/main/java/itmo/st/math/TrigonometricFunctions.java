package itmo.st.math;

public class TrigonometricFunctions {
    private final BaseFunctions baseFunctions;

    public TrigonometricFunctions(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
    }

    public TrigonometricFunctions() {
        this.baseFunctions = new BaseFunctions();
    }
    /**
     * Вычисляет синус через косинус: sin(x) = cos(x - π/2)
     */
    public double sin(double x) {
        return baseFunctions.cos(x - Math.PI / 2);
    }

    /**
     * Вычисляет тангенс: tan(x) = sin(x) / cos(x)
     */
    public double tan(double x) {
        double cosValue = baseFunctions.cos(x);
        checkDivisionByZero(cosValue, "Tangent is undefined at x = " + x);
        return sin(x) / cosValue;
    }

    /**
     * Вычисляет котангенс: cot(x) = cos(x) / sin(x)
     */
    public double cot(double x) {
        double sinValue = sin(x);
        checkDivisionByZero(sinValue, "Cotangent is undefined at x = " + x);
        return baseFunctions.cos(x) / sinValue;
    }

    /**
     * Вычисляет косеканс: csc(x) = 1 / sin(x)
     */
    public double csc(double x) {
        double sinValue = sin(x);
        checkDivisionByZero(sinValue, "Cosecant is undefined at x = " + x);
        return 1 / sinValue;
    }

    /**
     * Вычисляет секанс: sec(x) = 1 / cos(x)
     */
    public double sec(double x) {
        double cosValue = baseFunctions.cos(x);
        checkDivisionByZero(cosValue, "Secant is undefined at x = " + x);
        return 1 / cosValue;
    }

    /**
     * Вспомогательный метод для проверки деления на ноль
     */
    private void checkDivisionByZero(double value, String message) {
        if (Math.abs(value) < BaseFunctions.EPSILON) {
            throw new ArithmeticException(message);
        }
    }

    /**
     * Вычисляет косинус (прокси-метод для удобства)
     */
    public double cos(double x) {
        return baseFunctions.cos(x);
    }
}

package itmo.st.math;

public class SystemFunction {
    /**
     * Вычисляет значение системы функций:
     * x <= 0: (((((cos(x) + sin(x)) * cot(x)) / tan(x)) ^ 2) + ((tan(x) * (cos(x) -
     * csc(x))) - (((cot(x) + csc(x)) / csc(x)) + ((cos(x) / cot(x)) / sec(x)))))
     * x > 0: (((((log_10(x) / log_3(x)) - log_5(x)) ^ 3) * (log_2(x) - log_2(x))) *
     * ln(x))
     */
    public static double calculate(double x) {
        if (x <= 0) {
            return calculateTrigonometric(x);
        } else {
            return calculateLogarithmic(x);
        }
    }

    /**
     * Вычисляет тригонометрическую часть системы функций
     */
    public static double calculateTrigonometric(double x) {
        try {
            double part1 = calculateTrigPart1(x);
            double part2 = calculateTrigPart2(x);
            return part1 + part2;
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Error calculating trigonometric function: " + e.getMessage());
        }
    }

    /**
     * Вычисляет первую часть тригонометрической функции: (((cos(x) + sin(x)) *
     * cot(x)) / tan(x)) ^ 2
     */
    public static double calculateTrigPart1(double x) {
        double cosX = TrigonometricFunctions.cos(x);
        double sinX = TrigonometricFunctions.sin(x);
        double cotX = TrigonometricFunctions.cot(x);
        double tanX = TrigonometricFunctions.tan(x);

        double sum = cosX + sinX;
        double product = sum * cotX;
        double division = product / tanX;
        return Math.pow(division, 2);
    }

    /**
     * Вычисляет вторую часть тригонометрической функции:
     * (tan(x) * (cos(x) - csc(x))) - (((cot(x) + csc(x)) / csc(x)) + ((cos(x) /
     * cot(x)) / sec(x)))
     */
    public static double calculateTrigPart2(double x) {
        double tanX = TrigonometricFunctions.tan(x);
        double cosX = TrigonometricFunctions.cos(x);
        double cscX = TrigonometricFunctions.csc(x);

        double subPart1 = tanX * (cosX - cscX);
        double subPart2 = calculateTrigPart2SubPart2(x);

        return subPart1 - subPart2;
    }

    /**
     * Вычисляет подчасть второй части тригонометрической функции:
     * ((cot(x) + csc(x)) / csc(x)) + ((cos(x) / cot(x)) / sec(x))
     */
    public static double calculateTrigPart2SubPart2(double x) {
        double cotX = TrigonometricFunctions.cot(x);
        double cscX = TrigonometricFunctions.csc(x);
        double cosX = TrigonometricFunctions.cos(x);
        double secX = TrigonometricFunctions.sec(x);

        double fraction1 = (cotX + cscX) / cscX;
        double fraction2 = (cosX / cotX) / secX;

        return fraction1 + fraction2;
    }

    /**
     * Вычисляет логарифмическую часть системы функций
     */
    public static double calculateLogarithmic(double x) {
        try {
            double part1 = calculateLogPart1(x);
            double part2 = calculateLogPart2(x);
            double lnX = LogarithmicFunctions.ln(x);

            return part1 * part2 * lnX;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error calculating logarithmic function: " + e.getMessage());
        }
    }

    /**
     * Вычисляет первую часть логарифмической функции: ((log_10(x) / log_3(x)) -
     * log_5(x)) ^ 3
     */
    public static double calculateLogPart1(double x) {
        double log10X = LogarithmicFunctions.log10(x);
        double log3X = LogarithmicFunctions.log3(x);
        double log5X = LogarithmicFunctions.log5(x);

        double division = log10X / log3X;
        double difference = division - log5X;

        return Math.pow(difference, 3);
    }

    /**
     * Вычисляет вторую часть логарифмической функции: (log_2(x) - log_2(x))
     * Примечание: эта часть всегда равна 0
     */
    public static double calculateLogPart2(double x) {
        double log2X = LogarithmicFunctions.log2(x);
        return log2X - log2X; // Всегда 0
    }
}

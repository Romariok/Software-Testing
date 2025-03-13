package itmo.st.math;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SystemFunction {
    private static final double EPSILON = 1e-5;
    private TrigonometricFunctions trigonometricFunctions;
    private LogarithmicFunctions logarithmicFunctions;
    public static final double PI = 3.14159265358979323846;

    public SystemFunction(TrigonometricFunctions trigonometricFunctions, LogarithmicFunctions logarithmicFunctions) {
        this.trigonometricFunctions = trigonometricFunctions;
        this.logarithmicFunctions = logarithmicFunctions;
    }

    public SystemFunction() {
        this.trigonometricFunctions = new TrigonometricFunctions();
        this.logarithmicFunctions = new LogarithmicFunctions();
    }

    /**
     * Вычисляет значение системы функций:
     * x <= 0: (((((cos(x) + sin(x)) * cot(x)) / tan(x)) ^ 2) + ((tan(x) * (cos(x) -
     * csc(x))) - (((cot(x) + csc(x)) / csc(x)) + ((cos(x) / cot(x)) / sec(x)))))
     * x > 0: (((((log_10(x) / log_3(x)) - log_5(x)) ^ 3) * (log_2(x) - log_2(x))) *
     * ln(x))
     */
    public double calculate(double x) {
        if (x <= 0) {
            return calculateTrigonometric(x);
        } else {
            return calculateLogarithmic(x);
        }
    }

    /**
     * Вычисляет тригонометрическую часть системы функций
     */
    public double calculateTrigonometric(double x) {
        try {
            if (isMultipleOfPiOver2(x)) {
                throw new IllegalArgumentException("Invalid input: x cannot be a multiple of π/2");
            }
            double part1 = calculateTrigPart1(x);
            double part2 = calculateTrigPart2(x);
            return part1 + part2;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error calculating trigonometric function: " + e.getMessage());
        }
    }

    private boolean isMultipleOfPiOver2(double x) {
        double factor = x / (PI / 2);
        return Math.abs(factor - Math.round(factor)) < EPSILON;
    }

    /**
     * Вычисляет первую часть тригонометрической функции: (((cos(x) + sin(x)) *
     * cot(x)) / tan(x)) ^ 2
     */
    public double calculateTrigPart1(double x) {
        double cosX = trigonometricFunctions.cos(x);
        double sinX = trigonometricFunctions.sin(x);
        double cotX = trigonometricFunctions.cot(x);
        double tanX = trigonometricFunctions.tan(x);

        double sum = cosX + sinX;
        double product = sum * cotX;
        double division = product / tanX;
        return division * division;
    }

    /**
     * Вычисляет вторую часть тригонометрической функции:
     * (tan(x) * (cos(x) - csc(x))) - (((cot(x) + csc(x)) / csc(x)) + ((cos(x) /
     * cot(x)) / sec(x)))
     */
    public double calculateTrigPart2(double x) {
        double tanX = trigonometricFunctions.tan(x);
        double cosX = trigonometricFunctions.cos(x);
        double cscX = trigonometricFunctions.csc(x);

        double subPart1 = tanX * (cosX - cscX);
        double subPart2 = calculateTrigPart2SubPart2(x);

        return subPart1 - subPart2;
    }

    /**
     * Вычисляет подчасть второй части тригонометрической функции:
     * ((cot(x) + csc(x)) / csc(x)) + ((cos(x) / cot(x)) / sec(x))
     */
    public double calculateTrigPart2SubPart2(double x) {
        double cotX = trigonometricFunctions.cot(x);
        double cscX = trigonometricFunctions.csc(x);
        double cosX = trigonometricFunctions.cos(x);
        double secX = trigonometricFunctions.sec(x);

        double fraction1 = (cotX + cscX) / cscX;
        double fraction2 = (cosX / cotX) / secX;

        return fraction1 + fraction2;
    }

    /**
     * Вычисляет логарифмическую часть системы функций
     */
    public double calculateLogarithmic(double x) {
        try {
            if (x == 1)
                throw new IllegalArgumentException("Function is not defined at " + x);
            double part1 = calculateLogPart1(x);
            double part2 = calculateLogPart2(x);
            double lnX = logarithmicFunctions.ln(x);

            return part1 * part2 * lnX;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error calculating logarithmic function: " + e.getMessage());
        }
    }

    /**
     * Вычисляет первую часть логарифмической функции: ((log_10(x) / log_3(x)) -
     * log_5(x)) ^ 3
     */
    public double calculateLogPart1(double x) {
        double log10X = logarithmicFunctions.log10(x);
        double log3X = logarithmicFunctions.log3(x);
        double log5X = logarithmicFunctions.log5(x);

        double division = log10X / log3X;
        double difference = division - log5X;

        return Math.pow(difference, 3);
    }

    /**
     * Вычисляет вторую часть логарифмической функции: (log_2(x) - log_2(x))
     * Примечание: эта часть всегда равна 0
     */
    public double calculateLogPart2(double x) {
        double log2X = logarithmicFunctions.log2(x);
        return log2X - log2X; // Всегда 0
    }

    /**
     * Генерирует CSV-файл с результатами вычисления выбранной функции
     * на заданном интервале с указанным шагом
     */
    public void generateCSVForFunction(String functionName, double start, double end, double step, String filePath) {
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            StringBuilder sb = new StringBuilder();
            sb.append("x;y\n"); // Заголовок
            
            for (double x = start; x <= end; x += step) {
                double y = 0;
                try {
                    // Выбор функции для вычисления
                    switch (functionName) {
                        case "sin":
                            y = trigonometricFunctions.sin(x);
                            break;
                        case "cos":
                            y = trigonometricFunctions.cos(x);
                            break;
                        case "tan":
                            y = trigonometricFunctions.tan(x);
                            break;
                        case "cot":
                            y = trigonometricFunctions.cot(x);
                            break;
                        case "sec":
                            y = trigonometricFunctions.sec(x);
                            break;
                        case "csc":
                            y = trigonometricFunctions.csc(x);
                            break;
                        case "ln":
                            y = logarithmicFunctions.ln(x);
                            break;
                        case "log2":
                            y = logarithmicFunctions.log2(x);
                            break;
                        case "log3":
                            y = logarithmicFunctions.log3(x);
                            break;
                        case "log5":
                            y = logarithmicFunctions.log5(x);
                            break;
                        case "log10":
                            y = logarithmicFunctions.log10(x);
                            break;
                        case "system":
                            y = calculate(x);
                            break;
                        default:
                            throw new IllegalArgumentException("Неизвестная функция: " + functionName);
                    }
                    sb.append(x).append(";").append(y).append("\n");
                } catch (Exception e) {
                    System.out.println("Функция не определена при x = " + x + ": " + e.getMessage());
                }
            }
            
            writer.write(sb.toString());
            System.out.println("CSV-файл создан успешно: " + filePath);
            
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка при создании файла: " + e.getMessage());
        }
    }
}

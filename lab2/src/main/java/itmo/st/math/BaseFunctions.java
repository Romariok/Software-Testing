package itmo.st.math;

public class BaseFunctions {
    protected static final double EPSILON = 1e-7;
    private static final int MAX_TERMS = 50; // Максимальное количество членов ряда.
    private static final double LN_2 = 0.69314718d;

    /**
     * Вычисляет косинус через разложение в ряд Тейлора
     * cos(x) = 1 - x^2/2! + x^4/4! - x^6/6! + ...
     */
    public double cos(double x) {
        // Приведение x к диапазону [0, 2π)
        x = normalizeAngle(x);

        double result = 1.0;
        double term = 1.0;
        double x2 = x * x;
        int sign = -1;

        for (int i = 1; i <= MAX_TERMS; i++) {
            term = term * x2 / ((2 * i - 1) * (2 * i));
            result += sign * term;
            sign = -sign;

            if (Math.abs(term) < EPSILON) {
                break;
            }
        }

        return result;
    }

    /**
     * Нормализует угол в диапазон [0, 2π)
     */
    public double normalizeAngle(double x) {
        x = x % (2 * Math.PI);
        if (x < 0) {
            x += 2 * Math.PI;
        }
        return x;
    }

    /**
     * Вычисляет натуральный логарифм через разложение в ряд Тейлора
     * ln(1+x) = x - x^2/2 + x^3/3 - x^4/4 + ...
     * Для x > 0, используем ln(x) = ln((1+(x-1)) = ln(1+(x-1))
     */
    public double ln(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("Logarithm is defined only for positive numbers");
        }

        // Для значений близких к 1 используем ряд напрямую
        if (x >= 0.5 && x <= 1.5) {
            return lnNearOne(x - 1);
        }
        // Для больших значений используем свойство ln(a*b) = ln(a) + ln(b)
        else if (x > 1.5) {
            return lnLargeValue(x);
        }
        // Для малых значений используем свойство ln(1/x) = -ln(x)
        else { // x < 0.5
            return -ln(1 / x);
        }
    }

    /**
     * Вычисляет ln(1+z) для z близких к 0 через ряд Тейлора
     */
    public double lnNearOne(double z) {
        double result = 0;
        double term = z;
        double z_power = z;

        for (int i = 1; i <= MAX_TERMS; i++) {
            result += term;
            z_power *= -z;
            term = z_power / (i + 1);

            if (Math.abs(term) < EPSILON) {
                break;
            }
        }

        return result;
    }

    /**
     * Вычисляет ln(x) для больших значений x через свойство ln(a*b) = ln(a) + ln(b)
     */
    public double lnLargeValue(double x) {
        // Находим k такое, что x/2^k будет близко к 1
        int k = 0;
        double reduced_x = x;

        while (reduced_x >= 1.5) {
            reduced_x /= 2;
            k++;
        }

        // ln(x) = ln(x/2^k * 2^k) = ln(x/2^k) + ln(2^k) = ln(x/2^k) + k*ln(2)
        return ln(reduced_x) + k * LN_2;
    }
}

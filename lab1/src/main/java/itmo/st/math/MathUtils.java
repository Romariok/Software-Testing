package itmo.st.math;

public class MathUtils {
    public static double arccosSeries(double x, int terms) {
        if (!(x > -1 && x < 1))
            throw new IllegalArgumentException("x must be in (-1,1)");

        double result = Math.PI / 2; // First term π/2

        for (int n = 0; n < terms; n++) {
            // Calculate (2n)!
            double factorial2n = 1;
            for (int i = 1; i <= 2 * n; i++) {
                factorial2n *= i;
            }

            // Calculate (n!)²
            double factorialn = 1;
            for (int i = 1; i <= n; i++) {
                factorialn *= i;
            }
            double factorialnSquared = factorialn * factorialn;

            // Calculate 4^n
            double fourPowN = Math.pow(4, n);

            // Calculate x^(2n+1)
            double xPow = Math.pow(x, 2 * n + 1);

            // Calculate the nth term
            double term = (factorial2n * xPow) / (fourPowN * factorialnSquared * (2 * n + 1));

            result -= term; // Subtract according to the formula
        }
        if (Double.isNaN(result)) {
            throw new ArithmeticException("Calculation resulted in NaN - likely due to numerical overflow");
        }
        return result;
    }
}

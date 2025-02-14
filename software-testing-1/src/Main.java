import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathUtils {
    public static double arccosSeries(double x, int terms) {
        if (x < -1 || x > 1) throw new IllegalArgumentException("x must be in [-1,1]");
        doubled sum = Math.PI / 2;
        double factorial = 1;
        double power = x;
        for (int n = 0; n < terms; n++) {
            factorial *= (2 * n + 1) * (2 * n + 2);
            power *= x * x;
            sum -= (factorial / (Math.pow(2, 2 * n + 1) * Math.pow(n + 1, 2))) * (power / (2 * n + 3));
        }
        return sum;
    }
}

class MathUtilsTest {
    @Test
    void testArccosSeries() {
        assertEquals(Math.acos(0), MathUtils.arccosSeries(0, 20), 1e-7);
        assertEquals(Math.acos(0.5), MathUtils.arccosSeries(0.5, 8), 1e-7);
        assertEquals(Math.acos(-0.5), MathUtils.arccosSeries(-0.5, 20), 1e-7);
        assertEquals(Math.acos(1), MathUtils.arccosSeries(1, 20), 1e-7);
        assertEquals(Math.acos(-1), MathUtils.arccosSeries(-1, 20), 1e-7);
    }
}

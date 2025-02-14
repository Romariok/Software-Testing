import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathUtils {
    public static double arccosSeries(double x, int terms) {
        if (x < -1 || x > 1) throw new IllegalArgumentException("x must be in [-1,1]");
        
        double result = Math.PI / 2;  // First term π/2
        
        for (int n = 0; n < terms; n++) {
            // Calculate (2n)!
            double factorial2n = 1;
            for (int i = 1; i <= 2*n; i++) {
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
            double xPow = Math.pow(x, 2*n + 1);
            
            // Calculate the nth term
            double term = (factorial2n * xPow) / (fourPowN * factorialnSquared * (2*n + 1));
            
            result -= term;  // Subtract according to the formula
        }
        
        return result;
    }
}

class MathUtilsTest {
    @Test
    void testArccosSeries() {
        assertEquals(Math.acos(0), MathUtils.arccosSeries(0, 85), 1e-6);
        assertEquals(Math.acos(0.25), MathUtils.arccosSeries(0.25, 85), 1e-6);
        assertEquals(Math.acos(-0.25), MathUtils.arccosSeries(-0.25, 85), 1e-6);
        assertEquals(Math.acos(0.52), MathUtils.arccosSeries(0.52, 85), 1e-6);
        assertEquals(Math.acos(-0.52), MathUtils.arccosSeries(-0.52, 85), 1e-6);
        assertEquals(Math.acos(0.75), MathUtils.arccosSeries(0.75, 85), 1e-6);
        assertEquals(Math.acos(-0.75), MathUtils.arccosSeries(-0.75, 85), 1e-6);
        assertEquals(Math.acos(0.95), MathUtils.arccosSeries(0.95, 85), 1e-6);
        assertEquals(Math.acos(-0.95), MathUtils.arccosSeries(-0.95, 85), 1e-6);
        assertEquals(Math.acos(-1), MathUtils.arccosSeries(-1, 85), 1e-2);
        assertEquals(Math.acos(1), MathUtils.arccosSeries(1, 85), 1e-2);
    }
}

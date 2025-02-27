package itmo.st;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;
import itmo.st.math.MathUtils;
import java.util.Arrays;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {
    private record TestParams(double value, int terms, double delta) {}
    
    static Stream<TestParams> arccosTestParameters() {
        double[] values = {0, 0.5, -0.5, 0.75, -0.75, 0.9, -0.9};
        int[] terms = {30, 60};
        double[] deltas = {1e-3, 1e-4};
        
        return Arrays.stream(values)
            .boxed()
            .flatMap(value -> Arrays.stream(terms)
                .boxed()
                .flatMap(term -> Arrays.stream(deltas)
                    .boxed()
                    .map(delta -> new TestParams(value, term, delta))));
    }

    @ParameterizedTest
    @MethodSource("arccosTestParameters")
    void testArccosSeries(TestParams params) {
        double expected = Math.acos(params.value());
        double actual = MathUtils.arccosSeries(params.value(), params.terms());
        assertEquals(expected, actual, params.delta());
    }

    @Test
    void testArccosSeriesEdgeCases() {
        assertThrows(IllegalArgumentException.class, 
            () -> MathUtils.arccosSeries(1, 50));

        assertThrows(IllegalArgumentException.class, 
            () -> MathUtils.arccosSeries(-1, 50));

        assertThrows(IllegalArgumentException.class, 
            () -> MathUtils.arccosSeries(1.1, 50));

        assertThrows(IllegalArgumentException.class, 
            () -> MathUtils.arccosSeries(-1.1, 50));
    }

    @Test
    void testArccosSeriesNaNHandling() {
        // For values close to ±1 with high number of terms, we expect NaN
        assertThrows(ArithmeticException.class, 
            () -> MathUtils.arccosSeries(0.99999, 100));
        assertThrows(ArithmeticException.class, 
            () -> MathUtils.arccosSeries(-0.99999, 100));
    }

    static Stream<Double> edgeValuesProvider() {
        return Stream.of(0.9, -0.9, 0.95, -0.95);
    }

    @ParameterizedTest
    @MethodSource("edgeValuesProvider")
    void testArccosSeriesLowTermsPrecisionOnCloserToEdges(double value) {
        int lowTerms = 10;
        double highPrecision = 1e-6;
        
        double expected = Math.acos(value);
        double actual = MathUtils.arccosSeries(value, lowTerms);
        
        double difference = Math.abs(expected - actual);
        assertTrue(difference > highPrecision, 
            "With only " + lowTerms + " terms and value " + value + 
            ", precision should be worse than " + highPrecision + 
            " but got " + difference);
    }
}
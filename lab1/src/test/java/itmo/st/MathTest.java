package itmo.st;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import itmo.st.math.MathUtils;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathUtilsTest {
    private record TestParams(double value, int terms, double delta) {}
    
    static Stream<TestParams> arccosTestParameters() {
        double[] values = {0, 0.005, -0.005, 0.25, -0.25, 0.52, -0.52, 0.75, -0.75, 0.95, -0.95, -1, 1};
        int[] terms = {10, 30, 50, 85, 100};
        double[] deltas = {1e-4, 1e-6, 1e-8};
        
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
}
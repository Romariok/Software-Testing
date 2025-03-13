package itmo.st.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import itmo.st.math.*;
import static org.junit.jupiter.api.Assertions.*;

public class SystemFunctionTest {
   private static final double DELTA = 0.00001;

   private SystemFunction systemFunction;

   @BeforeEach
   void setUp() {
      systemFunction = new SystemFunction();
   }

   @ParameterizedTest(name = "{index} - System function with invalid x = {0}")
   @CsvFileSource(resources = "/dataset/SystemInvalidTest.csv", delimiter = ';')
   void testInvalidSystemArgumentsExceptIllegalArgumentException(double x) {
      assertThrows(IllegalArgumentException.class, () -> systemFunction.calculate(x));

   }

   @ParameterizedTest(name = "{index} - System function with valid x {0} = {1} ")
   @CsvFileSource(resources = "/dataset/SystemValidTest.csv", delimiter = ';')
   void testValidSystemArguments(double x, double expected) {
      double result = systemFunction.calculate(x);
      assertEquals(expected, result, DELTA);
   }

}

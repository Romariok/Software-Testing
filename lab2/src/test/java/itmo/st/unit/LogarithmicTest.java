package itmo.st.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import itmo.st.math.LogarithmicFunctions;

import static org.junit.jupiter.api.Assertions.*;

public class LogarithmicTest {
   private static final double DELTA = 0.0001;

   private LogarithmicFunctions logarithmicFunctions;

   @BeforeEach
   void setUp() {
      logarithmicFunctions = new LogarithmicFunctions();
   }

   @CsvFileSource(resources = "/dataset/lnTestDataset.csv", delimiter = ';')
   @ParameterizedTest(name = "{index} - Test ln {0} = {1}")
   void testLn(double input, double expected) {
      assertEquals(expected, logarithmicFunctions.ln(input), DELTA);
   }

   @CsvFileSource(resources = "/dataset/logTestDataset.csv", delimiter = ';')
   @ParameterizedTest(name = "{index} - Test log {0} with base {1} = {2}")
   void testLog(double input, double base, double expected) {
      assertEquals(expected, logarithmicFunctions.log(base, input), DELTA);
   }

   @CsvFileSource(resources = "/dataset/invalidLnTestDataset.csv", delimiter = ';')
   @ParameterizedTest(name = "{index} - Test ln with invalid input {0}")
   void testLnWithInvalidInput(double input) {
      assertThrows(IllegalArgumentException.class, () -> logarithmicFunctions.ln(input));
   }

   @CsvFileSource(resources = "/dataset/invalidLogTestDataset.csv", delimiter = ';')
   @ParameterizedTest(name = "{index} - Test log with invalid input {0} and base {1}")
   void testLogWithInvalidInput(double input, double base) {
      assertThrows(IllegalArgumentException.class, () -> logarithmicFunctions.log(base, input));
   }
}

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
   
}

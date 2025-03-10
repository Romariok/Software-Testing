package itmo.st.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.*;

import itmo.st.math.TrigonometricFunctions;

public class TrigonometricTest {
   private static final double DELTA = 0.0001;

   private TrigonometricFunctions trigonometricFunctions;

   @BeforeEach
   void setUp() {
      trigonometricFunctions = new TrigonometricFunctions();
   }

   @CsvFileSource(resources = "/dataset/cosTestDataset.csv", delimiter = ';')
   @ParameterizedTest(name = "{index} - Test cos {0} = {1}")
   void testCos(double input, double expected) {
      assertEquals(expected, trigonometricFunctions.cos(input), DELTA);
   }

   @CsvFileSource(resources = "/dataset/sinTestDataset.csv", delimiter = ';')
   @ParameterizedTest(name = "{index} - Test sin {0} = {1}")
   void testSin(double input, double expected) {
      assertEquals(expected, trigonometricFunctions.sin(input), DELTA);
   }

   @CsvFileSource(resources = "/dataset/tanTestDataset.csv", delimiter = ';')
   @ParameterizedTest(name = "{index} - Test tan {0} = {1}")
   void testTan(double input, double expected) {
      assertEquals(expected, trigonometricFunctions.tan(input), DELTA);
   }

   @CsvFileSource(resources = "/dataset/cotTestDataset.csv", delimiter = ';')
   @ParameterizedTest(name = "{index} - Test cot {0} = {1}")
   void testCot(double input, double expected) {
      assertEquals(expected, trigonometricFunctions.cot(input), DELTA);
   }

   @CsvFileSource(resources = "/dataset/secTestDataset.csv", delimiter = ';')
   @ParameterizedTest(name = "{index} - Test sec {0} = {1}")
   void testSec(double input, double expected) {
      assertEquals(expected, trigonometricFunctions.sec(input), DELTA);
   }

   @CsvFileSource(resources = "/dataset/cscTestDataset.csv", delimiter = ';')
   @ParameterizedTest(name = "{index} - Test csc {0} = {1}")
   void testCsc(double input, double expected) {
      assertEquals(expected, trigonometricFunctions.csc(input), DELTA);
   }
}

package itmo.st.integration;

import itmo.st.math.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LogarithmicTest {
   private static final double DELTA = 0.0001;

   @Mock
   private BaseFunctions baseFunctions;

   @InjectMocks
   private LogarithmicFunctions logarithmicFunctions;

   @BeforeEach
   void setUp() {
      MockitoAnnotations.openMocks(this);
   }

   @DisplayName("Test log integration with ln")
   @Test
   void testLogIntegrationWithLn() {
      double x = 4.5232;
      double base = 2.0;
      when(baseFunctions.ln(x)).thenReturn(Math.log(x));
      when(baseFunctions.ln(base)).thenReturn(Math.log(base));

      double expected = Math.log(x) / Math.log(base);
      assertEquals(expected, logarithmicFunctions.log(base, x), DELTA);
      verify(baseFunctions, times(1)).ln(x);
      verify(baseFunctions, times(1)).ln(base);
   }

   
}

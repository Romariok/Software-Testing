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

public class SystemFunctionTest {
   private static final double DELTA = 0.0001;
   private static final double X_POSITIVE = 10.0;
   private static final double X_NEGATIVE = -10.0;
   private static final double EXPECTED_POSITIVE = 0;
   private static final double EXPECTED_NEGATIVE = 2.5239;

   @Mock
   private TrigonometricFunctions trigonometricFunctions;

   @Mock
   private LogarithmicFunctions logarithmicFunctions;

   @InjectMocks
   private SystemFunction systemFunction;

   @BeforeEach
   void setUp() {
      MockitoAnnotations.openMocks(this);
   }

   @DisplayName("Test system function integration when x > 0")
   @Test
   void testSystemFunctionIntegrationWithXGreaterThanZero() {
      when(logarithmicFunctions.log10(X_POSITIVE)).thenReturn(Math.log10(X_POSITIVE));
      when(logarithmicFunctions.log3(X_POSITIVE)).thenReturn(Math.log(X_POSITIVE) / Math.log(3));
      when(logarithmicFunctions.log5(X_POSITIVE)).thenReturn(Math.log(X_POSITIVE) / Math.log(5));
      when(logarithmicFunctions.log2(X_POSITIVE)).thenReturn(Math.log(X_POSITIVE) / Math.log(2));
      when(logarithmicFunctions.ln(X_POSITIVE)).thenReturn(Math.log(X_POSITIVE));

      assertEquals(EXPECTED_POSITIVE, systemFunction.calculate(X_POSITIVE), DELTA);

      verify(logarithmicFunctions, times(1)).log10(X_POSITIVE);
      verify(logarithmicFunctions, times(1)).log3(X_POSITIVE);
      verify(logarithmicFunctions, times(1)).log5(X_POSITIVE);
      verify(logarithmicFunctions, times(1)).log2(X_POSITIVE);
      verify(logarithmicFunctions, times(1)).ln(X_POSITIVE);
   }

   @DisplayName("Test system function integration when x <= 0")
   @Test
   void testSystemFunctionIntegrationWithXLessThanOrEqualToZero() {
      when(trigonometricFunctions.cos(X_NEGATIVE)).thenReturn(Math.cos(X_NEGATIVE));
      when(trigonometricFunctions.sin(X_NEGATIVE)).thenReturn(Math.sin(X_NEGATIVE));
      when(trigonometricFunctions.cot(X_NEGATIVE)).thenReturn(1.0 / Math.tan(X_NEGATIVE));
      when(trigonometricFunctions.tan(X_NEGATIVE)).thenReturn(Math.tan(X_NEGATIVE));
      when(trigonometricFunctions.csc(X_NEGATIVE)).thenReturn(1.0 / Math.sin(X_NEGATIVE));
      when(trigonometricFunctions.sec(X_NEGATIVE)).thenReturn(1.0 / Math.cos(X_NEGATIVE));

      assertEquals(EXPECTED_NEGATIVE, systemFunction.calculate(X_NEGATIVE), DELTA);

      verify(trigonometricFunctions, times(3)).cos(X_NEGATIVE);
      verify(trigonometricFunctions, times(1)).sin(X_NEGATIVE);
      verify(trigonometricFunctions, times(2)).cot(X_NEGATIVE);
      verify(trigonometricFunctions, times(2)).tan(X_NEGATIVE);
      verify(trigonometricFunctions, times(2)).csc(X_NEGATIVE);
      verify(trigonometricFunctions, times(1)).sec(X_NEGATIVE);
   }
}
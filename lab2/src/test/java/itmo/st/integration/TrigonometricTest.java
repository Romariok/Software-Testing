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

public class TrigonometricTest {

   private static final double DELTA = 0.0001;

   private static final double ANGLE_30_DEG = Math.PI / 6;
   private static final double ANGLE_60_DEG = Math.PI / 3;
   private static final double SIN_TEST_INPUT = -Math.PI / 2; // -90 градусов
   private static final double SIN_TEST_COS_ARG = -Math.PI; // -180 градусов
   private static final double SIN_TEST_EXPECTED = 1.0;
   private static final double COS_30_DEG = Math.sqrt(3) / 2;
   private static final double SIN_30_DEG = 0.5;
   private static final double COS_60_DEG = 0.5;
   private static final double SIN_60_DEG = Math.sqrt(3) / 2;

   @Mock
   private BaseFunctions baseFunctions;

   @InjectMocks
   private TrigonometricFunctions trigonometricFunctions;

   @BeforeEach
   void setUp() {
      MockitoAnnotations.openMocks(this);
   }

   @DisplayName("Test sin integration with cos")
   @Test
   void testSinIntegrationWithCos() {
      when(baseFunctions.cos(SIN_TEST_COS_ARG)).thenReturn(SIN_TEST_EXPECTED);
      assertEquals(SIN_TEST_EXPECTED, trigonometricFunctions.sin(SIN_TEST_INPUT), DELTA);
      verify(baseFunctions, times(1)).cos(SIN_TEST_COS_ARG);
   }

   @DisplayName("Test tan integration with sin and cos")
   @Test
   void testTanIntegrationWithSinAndCos() {
      when(baseFunctions.cos(ANGLE_30_DEG)).thenReturn(COS_30_DEG);

      TrigonometricFunctions spyTrig = spy(trigonometricFunctions);
      doReturn(SIN_30_DEG).when(spyTrig).sin(ANGLE_30_DEG);

      double expected = SIN_30_DEG / COS_30_DEG;
      assertEquals(expected, spyTrig.tan(ANGLE_30_DEG), DELTA);

      verify(baseFunctions, times(1)).cos(ANGLE_30_DEG);
      verify(spyTrig, times(1)).sin(ANGLE_30_DEG);
   }

   @DisplayName("Test cot integration with sin and cos")
   @Test
   void testCotIntegrationWithSinAndCos() {
      when(baseFunctions.cos(ANGLE_60_DEG)).thenReturn(COS_60_DEG);

      TrigonometricFunctions spyTrig = spy(trigonometricFunctions);
      doReturn(SIN_60_DEG).when(spyTrig).sin(ANGLE_60_DEG);

      double expected = COS_60_DEG / SIN_60_DEG;
      assertEquals(expected, spyTrig.cot(ANGLE_60_DEG), DELTA);

      verify(baseFunctions, times(1)).cos(ANGLE_60_DEG);
      verify(spyTrig, times(1)).sin(ANGLE_60_DEG);
   }

   @DisplayName("Test csc integration with sin")
   @Test
   void testCscIntegrationWithSin() {
      TrigonometricFunctions spyTrig = spy(trigonometricFunctions);
      doReturn(SIN_30_DEG).when(spyTrig).sin(ANGLE_30_DEG);

      double expected = 1.0 / SIN_30_DEG;
      assertEquals(expected, spyTrig.csc(ANGLE_30_DEG), DELTA);

      verify(spyTrig, times(1)).sin(ANGLE_30_DEG);
   }

   @DisplayName("Test sec integration with cos")
   @Test
   void testSecIntegrationWithCos() {
      when(baseFunctions.cos(ANGLE_60_DEG)).thenReturn(COS_60_DEG);

      double expected = 1.0 / COS_60_DEG;
      assertEquals(expected, trigonometricFunctions.sec(ANGLE_60_DEG), DELTA);

      verify(baseFunctions, times(1)).cos(ANGLE_60_DEG);
   }
}

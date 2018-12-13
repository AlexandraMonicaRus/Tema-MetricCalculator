package ro.siit.calculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MetricCalculatorTest {

    private MetricCalculator metricCalculator;

    @Before
    public void setUp() {
        metricCalculator = new MetricCalculator();
    }

    @Test(expected = NullPointerException.class)
    public void testCalculateWithNullExpression() {
        metricCalculator.calculate(null, "m");
    }

    @Test(expected = NumberFormatException.class)
    public void testCalculateWithEmptyExpression() {
        metricCalculator.calculate("", "m");
    }

    @Test
    public void testCalculateValidExpression() {
        double result = metricCalculator.calculate("2cm + 6m - 10mm", "mm");
        assertEquals(6010, result, 0.07);
    }


}
package ro.siit.calculator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetricCalculator {
    private static final String OPERATOR_SEQUENCE = "+-";
    private static Logger LOG = Logger.getLogger(MetricCalculator.class.getName());

    public double calculate(String expression, String inputMetric) {

        String metricExpression;
        double result = 0;
        try {
            //remove white spaces from expression
            metricExpression = expression.replaceAll("\\s+","");

            String[] operandArray = metricExpression.split("[+-]");
            List<String> operandList = new LinkedList<>(Arrays.asList(operandArray));
            List<String> operatorList = getOperatorList(metricExpression);

            for (int i = 0; i < operandList.size(); i++) {
                String operand = operandList.get(i);
                double convertedOperand = convertToInputMetric(operand, inputMetric);

                if (i == 0) {
                    result = convertedOperand;
                } else {
                    result = addOrSubstract(result, convertedOperand, operatorList.get(i-1));
                }
            }

        } catch(NullPointerException | NumberFormatException exception) {
            LOG.log(Level.SEVERE, exception.getMessage());
            throw exception;
        }

        return result;
    }

    private double addOrSubstract(double x, double y, String operator) {
        if (operator.equals("+")) {
            return x + y;
        } else {
            return x - y;
        }
    }

    private double convertToInputMetric(String operand, String inputMetric) throws NullPointerException, NumberFormatException  {
        double number = getNumber(operand);
        String metric = getMetric(operand);

        double convertedOperand = 0;
        if (inputMetric.equals(metric)) {
            return number;
        } else {
            if(inputMetric.equals("mm")) {
                if (metric.equals("cm")) {
                    convertedOperand = number * 10;
                }
                if (metric.equals("dm")) {
                    convertedOperand = number * 100;
                }
                if (metric.equals("m")) {
                    convertedOperand = number * 1000;
                }
            }

            if (inputMetric.equals("cm")) {
                if (metric.equals("mm")) {
                    convertedOperand = number / 10;
                }
                if (metric.equals("dm")) {
                    convertedOperand = number * 10;
                }
                if(metric.equals("m")) {
                    convertedOperand = number * 100;
                }
            }

            if (inputMetric.equals("dm")) {
                if (metric.equals("mm")) {
                    convertedOperand = number / 100;
                }
                if (metric.equals("cm")) {
                    convertedOperand = number / 10;
                }
                if(metric.equals("m")) {
                    convertedOperand = number * 10;
                }
            }

            if (inputMetric.equals("m")) {
                if (metric.equals("mm")) {
                    convertedOperand = number / 1000;
                }
                if (metric.equals("cm")) {
                    convertedOperand = number / 100;
                }
                if(metric.equals("dm")) {
                    convertedOperand = number / 10;
                }
            }
        }

        return convertedOperand;
    }

    private List<String> getOperatorList(String expression) {
        List<String> operatorList = new LinkedList<>();
        for (char operator : expression.toCharArray()) {
            if (OPERATOR_SEQUENCE.contains("" + operator)) {
                operatorList.add(String.valueOf(operator));
            }
        }

        return operatorList;
    }

    private double getNumber(String operand) throws NullPointerException, NumberFormatException {
        String number = operand.replaceAll("[^\\d.]", "");

        return Double.valueOf(number);
    }

    private String getMetric(String operand) throws NullPointerException, NumberFormatException {
        return operand.replaceAll("\\d","");
    }

    public static void main(String[] args)
    {
        MetricCalculator metricCalculator = new MetricCalculator();
        double result = metricCalculator.calculate("2cm + 6m - 10mm", "m");
        System.out.println("Te result is : " +  result + "m");
    }
}

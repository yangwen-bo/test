package main.java.moneyDemo;

import java.math.BigDecimal;

/**
 * @Author yangwen-bo
 * @Date 2020/6/2.
 * @Version 1.0
 */
public class MoneyUtils {
    public static double add(double v1, double v2) {
        return (BigDecimal.valueOf(v1).add(BigDecimal.valueOf(v2))).doubleValue();
    }

    public static double multiply(double v1, double v2) {
        return (BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(v2))).doubleValue();
    }

    public static double subtract(double v1, double v2) {
        return (BigDecimal.valueOf(v1).subtract(BigDecimal.valueOf(v2))).doubleValue();
    }

    public static double divide(double v1, double v2, int bit, int roundingMode) {
        return (BigDecimal.valueOf(v1).divide(BigDecimal.valueOf(v2), bit, roundingMode)).doubleValue();
    }
}

package main.java.moneyDemo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yangwen-bo
 * @Date 2020/6/2.
 * @Version 1.0
 *
 *
 */
public class MoneyDemo {
    /**
     * 每期应还本金
     * @param moneyType 币种（因为日元取整，特殊处理）
     * @param baseAmount 分期的本金
     * @param stagePeriod 分期期数
     * @param percent 比例（比例  *　stagePeriod ）
     * @return
     */
    private static List<Object> calcAmount(String moneyType, double baseAmount, int stagePeriod, int percent) {
        List<Object> result = new ArrayList<Object>();
        double freeRepayAmount = baseAmount;
        if ("392".equals( moneyType )) {
            for (int i = stagePeriod; i > 0; i--) {
                if (i == 1) {
                    result.add( (int) freeRepayAmount );
                    break;
                }

                int perAmount = (int) MoneyUtils.divide( baseAmount, i, 0, BigDecimal.ROUND_HALF_UP );
                int perAmountPercent = (int) MoneyUtils.multiply( perAmount, (percent * 1.00D) );
                int freeRepayPerAmount = (int) MoneyUtils.divide( perAmountPercent, 100.00D, 0, BigDecimal.ROUND_FLOOR );
                result.add( freeRepayPerAmount );
                baseAmount = MoneyUtils.subtract( baseAmount, perAmount );
                freeRepayAmount = MoneyUtils.subtract( freeRepayAmount, freeRepayPerAmount );
            }
        } else {
            for (int i = stagePeriod; i > 0; i--) {
                if (i == 1) {
                    result.add( freeRepayAmount );
                    break;
                }
                double perAmount = MoneyUtils.divide( baseAmount, i, 2, BigDecimal.ROUND_HALF_UP );
                int perAmountPercent = (int) MoneyUtils.multiply( perAmount, (percent * 1.00D) );
                double freeRepayPerAmount = MoneyUtils.divide( perAmountPercent, 100.00D, 2, BigDecimal.ROUND_FLOOR );
                result.add( freeRepayPerAmount );
                baseAmount = MoneyUtils.subtract( baseAmount, perAmount );
                freeRepayAmount = MoneyUtils.subtract( freeRepayAmount, freeRepayPerAmount );
            }
        }

        return result;
    }

    /**
     * 计算每期手续费
     * @param moneyType 币种（因为日元取整，特殊处理）
     * @param totalFee  总手续费
     * @param stagePeriod 分期期数
     * @return
     */
    private static List<Object> calcFee(String moneyType, double totalFee, int stagePeriod) {
        List<Object> result = new ArrayList<Object>();
        if ("392".equals( moneyType )) {
            for (int i = stagePeriod; i > 0; i--) {
                if (i == 1) {
                    result.add( (int) totalFee );
                    break;
                }

                int perFee = (int) MoneyUtils.divide( totalFee, i, 0, BigDecimal.ROUND_FLOOR );
                result.add( perFee );
                totalFee = MoneyUtils.subtract( totalFee, perFee );
            }
        } else {
            for (int i = stagePeriod; i > 0; i--) {
                if (i == 1) {
                    result.add( totalFee );
                    break;
                }
                double perFee = MoneyUtils.divide( totalFee, i, 2, BigDecimal.ROUND_FLOOR );
                result.add( perFee );
                totalFee = MoneyUtils.subtract( totalFee, perFee );
            }
        }

        return result;
    }

    public static void main(String[] args) {
        /*
        moneyType：币种（因为日元取整，特殊处理）
        baseAmount： 分期的本金
        stagePeriod： 分期期数
        percent： 比例（比例  *　stagePeriod ）
        totalFee：总手续费
         */
        String moneyType = "";
        double baseAmount = 600.55;
        int stagePeriod =3;
        int percent =3;
        double totalFee =24.5;
        List<Object> result = calcAmount( moneyType, baseAmount, stagePeriod, percent );
//        List<Object> result = calcFee(moneyType,totalFee,stagePeriod);
        System.out.println(result.get( 0 ));

//        for (Object object : result) {
//            System.out.println( object );
//        }
    }
}

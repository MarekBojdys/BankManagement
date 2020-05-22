package pl.marekbojdys.bankmanagement.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class CurrencyCalcUtil {

    private static final int ONE_HUNDRED = 100;
    private static final int NEGLIGIBLE_ROUNDING = 10;
    private static final int TWO_PLACES_AFTER_COMMA = 2;

    private CurrencyCalcUtil(){
    }

    public static String getAmountDividedBy100(final Integer amount){
        final BigDecimal bigDecimal = new BigDecimal(amount).divide(new BigDecimal(ONE_HUNDRED), NEGLIGIBLE_ROUNDING,
                RoundingMode.HALF_EVEN);
        return bigDecimal.setScale(TWO_PLACES_AFTER_COMMA, RoundingMode.HALF_EVEN).toString();
    }
}

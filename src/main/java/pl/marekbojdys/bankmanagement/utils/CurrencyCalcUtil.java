package pl.marekbojdys.bankmanagement.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class CurrencyCalcUtil {


    private static final int TWO_PLACES_AFTER_COMMA = 2;

    private CurrencyCalcUtil(){
    }

    public static BigDecimal standardizeAmount(final BigDecimal amount){
        return amount.setScale(TWO_PLACES_AFTER_COMMA, RoundingMode.FLOOR);
    }
}

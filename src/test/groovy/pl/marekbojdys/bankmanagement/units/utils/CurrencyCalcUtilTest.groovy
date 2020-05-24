package pl.marekbojdys.bankmanagement.units.utils


import pl.marekbojdys.bankmanagement.utils.CurrencyCalcUtil
import spock.lang.Specification

class CurrencyCalcUtilTest extends Specification {

    def "Standardize Amount test"() {
        when: "The CurrencyCalcUtil runs 'standardizeAmount' method"
        def result = CurrencyCalcUtil.standardizeAmount(amount)

        then: "result should be as #expectedResult"
        result == expectedResult

        where:
            amount                      || expectedResult
            new BigDecimal("0.011")     || new BigDecimal("0.01")
            new BigDecimal("1.23")      || new BigDecimal("1.23")
            new BigDecimal("1.567")     || new BigDecimal("1.56")
            new BigDecimal("20.123456") || new BigDecimal("20.12")
            new BigDecimal("1")         || new BigDecimal("1.00")
            new BigDecimal("1.5")       || new BigDecimal("1.50")
    }
}

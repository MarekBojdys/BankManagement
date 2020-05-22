package pl.marekbojdys.bankmanagement.units.utils


import pl.marekbojdys.bankmanagement.utils.CurrencyCalcUtil
import spock.lang.Specification

class CurrencyCalcUtilTest extends Specification{

    def "DividedBy100 test" () {
        when: "The CurrencyCalcUtil runs 'getAmountDividedBy100' method"
        def result = CurrencyCalcUtil.getAmountDividedBy100(amount)

        then: "result should be as #expectedResult"
            result == expectedResult
        where:
            amount || expectedResult
            1      || "0.01"
            11     || "0.11"
            123    || "1.23"
            1234   || "12.34"
    }
}

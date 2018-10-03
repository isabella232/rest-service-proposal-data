package gov.nsf.psm.proposaldata.utility;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Set;

import gov.nsf.psm.foundation.exception.CommonUtilException;

/**
 * Big Decimal Utilities for the PSM application.
 *
 */
public class BigDecimalUtil {

    private BigDecimalUtil() {
        super();
    }

    /**
     * Checks if input1 > input2
     * 
     * @param input1
     * @param input2
     * @return
     */

    public static boolean isGreaterThan(BigDecimal input1, BigDecimal input2) {
        boolean status = false;
        if (input1.compareTo(input2) > 0) {
            status = true;
        }
        return status;
    }

    /**
     * Checks if input1 < input2
     * 
     * @param input1
     * @param input2
     * @return
     */
    public static boolean isLessThan(BigDecimal input1, BigDecimal input2) {
        return input1.compareTo(input2) < 0;
    }

    /**
     * Checks if input1 == input2
     * 
     * @param input1
     * @param input2
     * @return
     */
    public static boolean isEqual(BigDecimal input1, BigDecimal input2) {
        return input1.compareTo(input2) == 0;
    }

    /**
     * Checks if input1 != input2
     * 
     * @param input1
     * @param input2
     * @return
     */
    public static boolean isNotEqual(BigDecimal input1, BigDecimal input2) {
        return !(input1.compareTo(input2) == 0);
    }

    /**
     * Checks if input1 >= input2
     * 
     * @param input1
     * @param input2
     * @return
     */
    public static boolean isGreaterThanOrEqual(BigDecimal input1, BigDecimal input2) {
        return isGreaterThan(input1, input2) || isEqual(input1, input2);
    }

    /**
     * Checks if input1 <= input2
     * 
     * @param input1
     * @param input2
     * @return
     */
    public static boolean isLessThanOrEqual(BigDecimal input1, BigDecimal input2) {
        return isLessThan(input1, input2) || (input1.compareTo(input2) == 0);
    }

    /**
     * Checks if input is negative number.
     * 
     * @param input
     * @return
     */
    public static boolean isNegative(BigDecimal input) {
        return BigDecimalUtil.isLessThan(input, BigDecimal.ZERO);
    }

    /**
     * Checks if input is positive number.
     * 
     * @param input
     * @return
     */
    public static boolean isPositive(BigDecimal input) {
        return BigDecimalUtil.isGreaterThan(input, BigDecimal.ZERO);
    }

    /**
     * Rounds PSM $ inputs.
     * 
     * @param input
     * @return
     */
    public static BigDecimal roundBigDecimal(BigDecimal input) {
        return (input == null) ? BigDecimal.valueOf(0) : input.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Divide PSM $ inputs.
     * 
     * @param numerator
     * @param denominator
     * @return
     */
    public static BigDecimal divide(BigDecimal numerator, BigDecimal denominator) {
        if (denominator.compareTo(BigDecimal.ZERO) == 0) { // divide by zero,
                                                           // return zero.
            return BigDecimal.ZERO;
        }
        return numerator.divide(denominator, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Returns the sum number in the numbers Set.
     *
     * @param numbers
     *            the numbers to calculate the sum.
     * @return the sum of the numbers.
     */
    public static BigDecimal sum(Set<BigDecimal> numbers) {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (BigDecimal bigDecimal : numbers) {
            sum = sum.add(bigDecimal);
        }
        return sum;
    }

    public static BigDecimal setDecimalWithScale(BigDecimal value) {

        if (value == null) {
            return null;
        }
        return value.setScale(5, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal setDecimalWithScale2(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static String getString(BigDecimal value) {
        return setDecimalWithScale(value).toString();
    }

    public static BigDecimal stringToBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Tests if two {@link BigDecimal} objects are equal in numerical value.
     */
    public static boolean areEqual(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) == 0;
    }

    /**
     * Tests if a {@link BigDecimal} is equal to 1 in numerical value.
     */
    public static boolean isOne(BigDecimal x) {
        return areEqual(x, BigDecimal.ONE);
    }

    /**
     * Tests if a {@link BigDecimal} is equal to 0 in numerical value.
     */
    public static boolean isZero(BigDecimal x) {
        return areEqual(x, BigDecimal.ZERO);
    }

    /**
     * Tests if a {@link BigDecimal} is equal to -1 in numerical value.
     */
    public static boolean isMinusOne(BigDecimal x) {
        return areEqual(x, BigDecimal.ONE.negate());
    }

    /**
     * Safely divides two {@link BigDecimal} objects without the risk of an
     * {@link ArithmeticException} in the case that an exact result can't be
     * computed.
     */
    public static BigDecimal safeDivide(BigDecimal dividend, BigDecimal divisor) {
        return dividend.divide(divisor, MathContext.DECIMAL32);
    }

    /**
     * Tests if a {@link String} is valid double value or not.
     */
    public static boolean isDouble(String input) throws CommonUtilException {
        boolean status = false;
        try {
            Double.parseDouble(input);
            status = true;
        } catch (Exception e) {
            throw new CommonUtilException("Got Error while checking isDouble or Not in BigDecimalUtil", e);
        }
        return status;
    }

    /**
     * Determines the actual amount given the percentage and the base.
     * 
     * @param percentage
     * @param amount
     * @return
     */
    public static BigDecimal getAmountFromPercentage(BigDecimal percentage, BigDecimal amount) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal hundred = BigDecimal.valueOf(100);
        BigDecimal pctDecimal;

        if (percentage != BigDecimal.ZERO) {
            pctDecimal = divide(percentage, hundred);
            result = pctDecimal.multiply(amount);
        }
        return roundBigDecimal(result);
    }

    /**
     * This method checks input, if it is null it returns 0
     * 
     * @param input
     * @return new BigDecimal(0.0)
     */
    public static BigDecimal ifNullReturnZero(BigDecimal input) {
        return (input == null) ? BigDecimal.valueOf(0) : input;
    }
}

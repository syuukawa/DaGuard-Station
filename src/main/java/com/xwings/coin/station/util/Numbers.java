package com.xwings.coin.station.util;

import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Map;

public final class Numbers {
    public static final long LONG_NULL_VAL = -1L;
    public static final int INT_NULL_VAL = -1;
    public static final String DIGITS_LETTER = "[a-zA-Z0-9]+";

    private static ThreadLocal<DecimalFormat> decimalFormatThreadLocal = ThreadLocal.withInitial(() -> {
        final DecimalFormat decimalFormat = new DecimalFormat("0");

        decimalFormat.setMaximumFractionDigits(310);

        return decimalFormat;
    });

    public static String stringWithoutScientific(double num) {
        return decimalFormatThreadLocal.get().format(num);
    }

    public static long longValue(Object o) {
        if (o == null) {
            return LONG_NULL_VAL;
        }

        return ((Number) o).longValue();
    }

    public static long longValue(Map<String, Object> map, String key) {
        if (CollectionUtils.isEmpty(map)) {
            return LONG_NULL_VAL;
        }

        return longValue(map.get(key));
    }


    public static int intValue(Object o) {
        if (o == null) {
            return INT_NULL_VAL;
        }

        return ((Number) o).intValue();
    }

    public static double doubleValue(Object o) {
        if (o == null) {
            return 0.0;
        }

        return ((Number) o).doubleValue();
    }

    public static float floatValue(Object o) {
        if (o == null) {
            return 0;
        }

        return ((Number) o).floatValue();
    }

    public static BigInteger toBigInteger(Object o) {

        return BigInteger.valueOf(longValue(o));
    }

    /**
     * @param hexString 0xFFF or FFF
     * @return
     */
    public static BigInteger hexToBigInteger(String hexString) {
        final String hex = hexString.startsWith("0x") ?
                hexString.replaceAll("0x", "") : hexString;

        return new BigInteger(hex, 16);
    }

    public static double roundDouble(double value, int decimalNumber) {
        final StringBuilder stringBuilder = new StringBuilder("0.0");

        for (int i = 1; i < decimalNumber; i++) {
            stringBuilder.append('0');
        }

        final DecimalFormat df = new DecimalFormat(stringBuilder.toString());

        return Double.parseDouble(df.format(value));
    }

    public static boolean isNullOrPositive(Number number) {
        return number == null || number.doubleValue() > 0;
    }

    public static boolean isLengthBetween(String str, int minLength, int maxLength) {
        return str.length() >= minLength && str.length() <= maxLength;
    }

    public static boolean isNumberOrCharacter(String str) {
        return str.matches(DIGITS_LETTER);
    }

    public static boolean containsAnyOfChars(String str, CharSequence... chars) {
        for (final CharSequence charSequence : chars) {
            if (str.contains(charSequence)) {
                return true;
            }
        }

        return false;
    }

    private Numbers() {
        throw new AssertionError("No instances for you!");
    }

}
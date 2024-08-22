package org.onespark;

import java.util.Map;
import java.util.regex.Pattern;

public class RomanNumeralUtils {

    private final static String VALID_ROMAN_NUMERAL_REGEX = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
    private final static Map<Character, Integer> ROMAN_NUMERALS_MAP = Map.ofEntries(
            Map.entry('I', 1),
            Map.entry('V', 5),
            Map.entry('X', 10),
            Map.entry('L', 50),
            Map.entry('C', 100),
            Map.entry('D', 500),
            Map.entry('M', 1000)
    );

    /**
     * Convert roman numeral to integer
     *
     * @param input Roman numeral string to be converted.
     * @return Integer value
     */
    public static int romanNumeralToInt(String input) {
        if (isValidRomanNumeral(input)) {
            var result = 0;
            for (var i = 0; i < input.length(); i++) {
                if (i > 0 && ROMAN_NUMERALS_MAP.get(input.charAt(i)) > ROMAN_NUMERALS_MAP.get(input.charAt(i - 1))) {
                    result += ROMAN_NUMERALS_MAP.get(input.charAt(i)) - 2 * ROMAN_NUMERALS_MAP.get(input.charAt(i - 1));
                } else {
                    result += ROMAN_NUMERALS_MAP.get(input.charAt(i));
                }
            }
            return result;
        }
        throw new IllegalArgumentException("Invalid roman numeral: " + input);
    }

    /**
     * Validate if a given string is  valid roman numeral expression.
     *
     * @param input The input to be validated
     * @return true if it's valid, false if it's not a valid roman numeral
     */
    private static boolean isValidRomanNumeral(String input) {
        var pattern = Pattern.compile(VALID_ROMAN_NUMERAL_REGEX);
        if (input == null || input.isEmpty()) {
            return false;
        }
        return pattern.matcher(input).matches();
    }
}

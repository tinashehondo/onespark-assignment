package org.onespark;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RomanNumeralUtilsTest {

    @Test
    public void convertValidRomanNumeralToInt() {
        var romanNumeral = "VIII";

        var actualValue = RomanNumeralUtils.romanNumeralToInt(romanNumeral);
        Assertions.assertEquals(8, actualValue);
    }
    @Test
    public void convertValidRomanNumeralToIntMoreThanThreeTimes() {
        var romanNumeral = "XXXIX";

        var actualValue = RomanNumeralUtils.romanNumeralToInt(romanNumeral);
        Assertions.assertEquals(39, actualValue);
    }
    @Test
    public void convertInalidRomanNumeralToIntNoneValidRepeat() {
        var romanNumeral = "DDD";
        var exception = assertThrows(IllegalArgumentException.class, () -> RomanNumeralUtils.romanNumeralToInt(romanNumeral));
        Assertions.assertEquals("Invalid roman numeral: DDD", exception.getMessage());
    }
    @Test
    public void convertInalidRomanNumeralToIntSubtractionOfI() {
        var romanNumeral = "IM";
        var exception = assertThrows(IllegalArgumentException.class, () -> RomanNumeralUtils.romanNumeralToInt(romanNumeral));
        Assertions.assertEquals("Invalid roman numeral: IM", exception.getMessage());
    }
    @Test
    public void convertValidRomanNumeralWithSubtractionToInt() {
        var romanNumeral = "XL";

        var actualValue = RomanNumeralUtils.romanNumeralToInt(romanNumeral);
        Assertions.assertEquals(40, actualValue);
    }

    @Test
    public void convertInValidRomanNumeralToInt() {
        var romanNumeral = "VIIIII";

        var exception = assertThrows(IllegalArgumentException.class, () -> RomanNumeralUtils.romanNumeralToInt(romanNumeral));

        Assertions.assertEquals("Invalid roman numeral: VIIIII", exception.getMessage());
    }
}

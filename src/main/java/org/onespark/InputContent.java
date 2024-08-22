package org.onespark;

import java.util.List;
import java.util.Map;

public record InputContent(Map<String, String> igUnitToRomanNumeralMap,
                           Map<String, List<String>> questionsMap,
                           Map<String, Float> metalElementsValueMap) {
}

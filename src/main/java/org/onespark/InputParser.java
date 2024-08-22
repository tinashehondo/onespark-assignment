package org.onespark;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.onespark.RomanNumeralUtils.romanNumeralToInt;

public class InputParser {

    private final static String IS = "is";
    private final static String CREDITS = "credits";
    private final static String SPACE_REGEX = "\\s+";
    private final static String QUESTION_MARK = "?";

    public InputContent parse(String inputText) {
        final var igUnitToRomanNumeralsMap = new LinkedHashMap<String, String>();
        final var questionsMap = new LinkedHashMap<String, List<String>>();
        final var metalElementsValueMap = new LinkedHashMap<String, Float>();

        final var commands = inputText.split("\n");
        for (var command : commands) {
            processLine(command, igUnitToRomanNumeralsMap, questionsMap, metalElementsValueMap);
        }
        return new InputContent(igUnitToRomanNumeralsMap, questionsMap, metalElementsValueMap);
    }

    /**
     * Method to process input text line.
     */
    private void processLine(String line,
                             Map<String, String> igUnitsToRmanNumeralMap,
                             Map<String, List<String>> questionsCommandsMap,
                             Map<String, Float> metalElementsValueMap) {
        if (line.endsWith(QUESTION_MARK)) {
            questionsCommandsMap.put(line, getRomanNumeralsAndMetalElementsFromQuestion(line));
            return;
        }
        // Process non-question
        var arr = line.split(SPACE_REGEX);
        if (arr.length == 3 && arr[1].equalsIgnoreCase(IS)) {
            igUnitsToRmanNumeralMap.put(arr[0], arr[2]);
        } else if (line.toLowerCase().endsWith(CREDITS)) {
            calculateValueOfASingleMetalElement(arr, igUnitsToRmanNumeralMap, metalElementsValueMap);
        }
    }

    /**
     * Calculates the value of a single metal element and adds it to map metalElementsValueMap.
     * For example: {Gold=15400, Iron=300, Silver=120}
     */
    private void calculateValueOfASingleMetalElement(String[] words,
                                                     Map<String, String> commandRomanNumeralMap,
                                                     Map<String, Float> metalElementsValueMap) {
        int splitIndex = 0;
        float creditValue = 0;
        String element = null;
        var igUnits = new String[]{};

        for (int i = 0; i < words.length; i++) {
            if (words[i].equalsIgnoreCase(CREDITS)) {
                creditValue = Integer.parseInt(words[i - 1]);
            }
            if (words[i].equalsIgnoreCase(IS)) {
                splitIndex = i - 1;
                element = words[i - 1];
            }
            igUnits = Arrays.copyOfRange(words, 0, splitIndex);
        }

        var metalCountInRomanNumeral = Arrays.stream(igUnits)
                .map(commandRomanNumeralMap::get)
                .collect(Collectors.joining());

        var valueOfMetalElement = creditValue / romanNumeralToInt(metalCountInRomanNumeral);
        metalElementsValueMap.put(element, valueOfMetalElement);
    }


    private List<String> getRomanNumeralsAndMetalElementsFromQuestion(String question) {
        var questionWordList = Arrays.stream(question.split(SPACE_REGEX)).toList();
        int startIndex = 0, endIndex = 0;
        for (var i = 0; i < questionWordList.size(); i++) {
            if (questionWordList.get(i).equalsIgnoreCase(IS)) {
                startIndex = i + 1;
            } else if (questionWordList.get(i).equals(QUESTION_MARK)) {
                endIndex = i;
            }
        }
        var array = questionWordList.toArray(new String[0]);
        return Arrays.stream(Arrays.copyOfRange(array, startIndex, endIndex))
                .toList();
    }
}

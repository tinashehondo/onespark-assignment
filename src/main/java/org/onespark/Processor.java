package org.onespark;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.onespark.RomanNumeralUtils.romanNumeralToInt;

public class Processor {

    private final static String ERROR_MESSAGE = "I have no idea what you are talking about";
    private final static String HOW_MUCH_QUESTION = "how much";
    private final static String HOW_MANY_QUESTION = "how many";
    private final InputParser inputParser;

    public Processor(InputParser inputParser) {
        this.inputParser = inputParser;
    }

    public List<String> process(String inputText) {
        if (inputText == null || inputText.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be empty");
        }
        final var inputContent = inputParser.parse(inputText);
        return inputContent.questionsMap().entrySet()
                .stream()
                .map(entry -> answerQuestion(inputContent, entry))
                .toList();
    }

    private String answerQuestion(InputContent content, Map.Entry<String, List<String>> entry) {
        if (entry.getKey().toLowerCase().startsWith(HOW_MUCH_QUESTION)) {
            return convertGalacticUnitsToRomanNumeral(content, entry);
        } else if (entry.getKey().toLowerCase().startsWith(HOW_MANY_QUESTION)) {
            return findValueOfMetalElement(content, entry);
        }
        return ERROR_MESSAGE;
    }

    /**
     * Method to process questions seeking the value of roman numeral and returns the result.
     */
    private String convertGalacticUnitsToRomanNumeral(InputContent content, Map.Entry<String, List<String>> entry) {
        final var valueInRomanNumeral = entry.getValue().stream().map(content.igUnitToRomanNumeralMap()::get)
                .collect(Collectors.joining());
        try {
            int numericValue = romanNumeralToInt(valueInRomanNumeral);
            return String.format("%s is %s", String.join(" ", entry.getValue()), numericValue);
        } catch (IllegalArgumentException e) {
            return ERROR_MESSAGE;
        }
    }

    /**
     * Method to handle questions seeking the value of any quantity of metals and returns the result.
     */
    private String findValueOfMetalElement(InputContent content, Map.Entry<String, List<String>> entry) {
        var valueInRomanNumeral = new StringBuilder();
        var metalElement = "";

        for (var token : entry.getValue()) {
            if (content.igUnitToRomanNumeralMap().containsKey(token)) {
                valueInRomanNumeral.append(content.igUnitToRomanNumeralMap().get(token));
            } else if (content.metalElementsValueMap().containsKey(token)) {
                metalElement = token;
            } else {
                return ERROR_MESSAGE;
            }
        }

        try {
            var romanNumeralIntValue = romanNumeralToInt(valueInRomanNumeral.toString());
            var metalElementValue = (int) (romanNumeralIntValue * content.metalElementsValueMap().get(metalElement));
            return String.format("%s is %s Credits", String.join(" ", entry.getValue()), metalElementValue);
        } catch (IllegalArgumentException e) {
            return ERROR_MESSAGE;
        }
    }
}

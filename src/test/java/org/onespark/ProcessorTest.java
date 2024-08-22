package org.onespark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProcessorTest {

    private Processor processor;

    @BeforeEach
    public void setUp() {
        this.processor = new Processor(new InputParser());
    }

    @Test
    public void testValidInput() {
        final var inputText = """
                glob is I
                prok is V
                pish is X
                tegj is L
                glob glob Silver is 34 Credits
                glob prok Gold is 57800 Credits
                pish pish Iron is 3910 Credits
                how much is pish tegj glob glob ?
                how many Credits is glob prok Silver ?
                how many Credits is glob prok Gold ?
                how many Credits is glob prok Iron ?
                how much wood could a woodchuck chuck if a woodchuck could chuck wood?
                """;
        final var results = processor.process(inputText);
        assertEquals(5, results.size());
        assertEquals("pish tegj glob glob is 42", results.get(0));
        assertEquals("glob prok Silver is 68 Credits", results.get(1));
        assertEquals("glob prok Gold is 57800 Credits", results.get(2));
        assertEquals("glob prok Iron is 782 Credits", results.get(3));
        assertEquals("I have no idea what you are talking about", results.get(4));
    }

    @Test
    public void testInvalidInput() {
        final var inputText = "";
        final var exception = assertThrows(IllegalArgumentException.class, () -> processor.process(inputText));
        assertEquals("Input text cannot be empty", exception.getMessage());
    }
}

package org.onespark;

public class Application {

    public static void main(String[] args) {
        //TODO could use different methods of getting the input
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
        final var responses = new Processor(new InputParser()).process(inputText);

        responses.forEach(System.out::println);
    }
}

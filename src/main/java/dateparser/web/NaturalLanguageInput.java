package dateparser.web;

/**
 * Created by nikolayivanov on 2/1/17.
 */
public class NaturalLanguageInput {
    private String text;

    public NaturalLanguageInput() {
    }

    public NaturalLanguageInput(String input) {
        this.text = input;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

package dateparser;

/**
 * Created by nikolayivanov on 1/28/17.
 */
public class NGram {
    private String value;
    private int numberOfWords;
    private IntegerRange tokenRange;
    private IntegerRange indexRange;

    public NGram(String value, int numberOfWords, IntegerRange tokenRange) {
        this.value = value;
        this.numberOfWords = numberOfWords;
        this.tokenRange = tokenRange;
    }

    public IntegerRange getTokenRange() {
        return tokenRange;
    }

    public void setTokenRange(IntegerRange tokenRange) {
        this.tokenRange = tokenRange;
    }

    public IntegerRange getIndexRange() {
        return indexRange;
    }

    public void setIndexRange(IntegerRange indexRange) {
        this.indexRange = indexRange;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "NGram{" +
                "value='" + value + '\'' +
                ", numberOfWords=" + numberOfWords +
                ", tokenRange=" + tokenRange +
                ", indexRange=" + indexRange +
                '}';
    }
}

package dateparser;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by nikolayivanov on 1/28/17.
 */
public abstract class ParserResult {
    protected IntegerRange indexRange;

    @JsonIgnore
    protected IntegerRange tokenRange;

    protected String role;

    public ParserResult(IntegerRange indexRange, IntegerRange tokenRange) {
        this.indexRange = indexRange;
        this.tokenRange = tokenRange;
        this.role = "None";
    }

    public IntegerRange getIndexRange() {
        return indexRange;
    }

    public void setIndexRange(IntegerRange indexRange) {
        this.indexRange = indexRange;
    }

    public IntegerRange getTokenRange() {
        return tokenRange;
    }

    public void setTokenRange(IntegerRange tokenRange) {
        this.tokenRange = tokenRange;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public abstract String getLabel();

    @Override
    public String toString() {
        return "ParserResult{" +
                "indexRange=" + indexRange +
                ", tokenRange=" + tokenRange +
                ", role=" + role +
                '}';
    }
}

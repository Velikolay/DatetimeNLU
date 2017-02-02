package dateparser.triptype;

import dateparser.IntegerRange;
import dateparser.ParserResult;

/**
 * Created by nikolayivanov on 2/2/17.
 */
public class TriptypeParserResult extends ParserResult {
    private String triptype;

    public TriptypeParserResult(String triptype, IntegerRange indexRange, IntegerRange tokenRange) {
        super(indexRange, tokenRange);
        this.triptype = triptype;
    }

    public String getTriptype() {
        return triptype;
    }

    public void setTriptype(String triptype) {
        this.triptype = triptype;
    }

    @Override
    public String getLabel() {
        return "en:triptype:" + triptype;
    }
}

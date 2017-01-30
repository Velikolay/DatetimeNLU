package dateparser.places;

import dateparser.IntegerRange;
import dateparser.ParserResult;

/**
 * Created by nikolayivanov on 1/28/17.
 */
public class PlaceParserResult extends ParserResult {
    private PlaceInfo placeInfo;

    public PlaceParserResult(PlaceInfo placeInfo, IntegerRange indexRange, IntegerRange tokenRange) {
        super(indexRange, tokenRange);
        this.placeInfo = placeInfo;
    }

    public PlaceInfo getPlaceInfo() {
        return placeInfo;
    }

    public void setPlaceInfo(PlaceInfo placeInfo) {
        this.placeInfo = placeInfo;
    }

    @Override
    public String getLabel() {
        return "en:place";
    }

    @Override
    public String toString() {
        return "PlaceParserResult{" +
                "placeInfo=" + placeInfo +
                '}';
    }
}

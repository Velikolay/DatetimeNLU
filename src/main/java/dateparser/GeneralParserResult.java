package dateparser;

import dateparser.places.PlaceParserResult;

import java.util.List;

/**
 * Created by nikolayivanov on 1/21/17.
 */
public class GeneralParserResult {
    private List<DateParserResult> dates;
    private List<PlaceParserResult> places;

    public GeneralParserResult() {
    }

    public GeneralParserResult(List<DateParserResult> dates, List<PlaceParserResult> places) {
        this.dates = dates;
        this.places = places;
    }

    public List<DateParserResult> getDates() {
        return dates;
    }

    public void setDates(List<DateParserResult> dates) {
        this.dates = dates;
    }

    public List<PlaceParserResult> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceParserResult> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "GeneralParserResult{" +
                "dates=" + dates +
                ", places=" + places +
                '}';
    }
}

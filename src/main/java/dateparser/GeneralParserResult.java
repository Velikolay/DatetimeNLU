package dateparser;

import dateparser.places.PlaceParserResult;
import dateparser.triptype.TriptypeParserResult;

import java.util.List;

/**
 * Created by nikolayivanov on 1/21/17.
 */
public class GeneralParserResult {
    private List<DateParserResult> dates;
    private List<PlaceParserResult> places;
    private List<TriptypeParserResult> triptypes;

    public GeneralParserResult() {
    }

    public GeneralParserResult(List<DateParserResult> dates, List<PlaceParserResult> places, List<TriptypeParserResult> triptypes) {
        this.dates = dates;
        this.places = places;
        this.triptypes = triptypes;
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

    public List<TriptypeParserResult> getTriptypes() {
        return triptypes;
    }

    public void setTriptypes(List<TriptypeParserResult> triptypes) {
        this.triptypes = triptypes;
    }

    @Override
    public String toString() {
        return "GeneralParserResult{" +
                "dates=" + dates +
                ", places=" + places +
                ", triptypes=" + triptypes +
                '}';
    }
}

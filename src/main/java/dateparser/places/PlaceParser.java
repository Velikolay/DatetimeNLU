package dateparser.places;

import dateparser.KeywordEntityParserUtils;
import dateparser.NGram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolayivanov on 1/20/17.
 */
public class PlaceParser {

    private static List<PlaceParserResult> extractPlaces(List<List<NGram>> ngrams, PlaceDataSource placeDataSource) {
        List<PlaceParserResult> placeResults = new ArrayList<>();
        for (List<NGram> igrams : ngrams) {
            for (NGram igram : igrams) {
                PlaceInfo placeInfo = placeDataSource.getPlaceInfo(igram.getValue());
                if(!placeInfo.isEmpty()) {
                    PlaceParserResult result = new PlaceParserResult(placeInfo, igram.getIndexRange(), igram.getTokenRange());

                    List<Integer> substringResults = KeywordEntityParserUtils.findOverlayResults(result, placeResults);
                    if(!substringResults.isEmpty()) {
                        for(Integer idx: substringResults) {
                            placeResults.remove(idx.intValue());
                        }
                    }

                    List<Integer> generalizations = getGeneralizationsFromList(result, placeResults);
                    if (!generalizations.isEmpty()) {
                        for(Integer idx: generalizations) {
                            placeResults.remove(idx.intValue());
                        }
                    }

                    if (getSpecificationsFromList(result, placeResults).isEmpty()) {
                        placeResults.add(result);
                    }
                }
            }
        }
        return placeResults;
    }

    private static List<Integer> getSpecificationsFromList(PlaceParserResult result, List<PlaceParserResult> currentPlaceResults) {
        List<Integer> specificationIdx = new ArrayList<>();
        for(int i=currentPlaceResults.size() - 1; i>=0; i--) {
            if (PlaceInfo.isGeneralization(result.getPlaceInfo(), currentPlaceResults.get(i).getPlaceInfo())) {
                specificationIdx.add(i);
            }
        }
        return specificationIdx;
    }

    private static List<Integer> getGeneralizationsFromList(PlaceParserResult result, List<PlaceParserResult> currentPlaceResults) {
        List<Integer> generalizationIdx = new ArrayList<>();
        for(int i=currentPlaceResults.size() - 1; i>=0; i--) {
            if (PlaceInfo.isGeneralization(currentPlaceResults.get(i).getPlaceInfo(), result.getPlaceInfo())) {
                generalizationIdx.add(i);
            }
        }
        return generalizationIdx;
    }

    public static List<PlaceParserResult> parse(String text, PlaceDataSource placeDataSource) throws IOException {
        List<List<NGram>> ngrams = KeywordEntityParserUtils.generateNGrams(text, 4);
        return extractPlaces(ngrams, placeDataSource);
    }

    public static void main(String[] args) {
        try {
            PlaceDataSource pds = new PlaceDataSource();
            pds.initialize();
            System.out.println(parse("siem reap cambodia the flight from atlanta to los angeles june, 1st through the eighth.", pds));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

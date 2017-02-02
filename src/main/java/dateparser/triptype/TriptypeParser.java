package dateparser.triptype;

import dateparser.KeywordEntityParserUtils;
import dateparser.NGram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by nikolayivanov on 2/2/17.
 */
public class TriptypeParser {
    private static final String RESOURCES_FOLDER = "src/main/resources/triptype";
    private static final String TRIPTYPE_DIRECT = RESOURCES_FOLDER + File.separator + "direct";
    private static final String TRIPTYPE_ONEWAY = RESOURCES_FOLDER + File.separator + "oneway";
    //private static final String TRIPTYPE_ROUNDTRIP = RESOURCES_FOLDER + File.separator + "roundtrip";

    public static List<TriptypeParserResult> parse(String text) throws FileNotFoundException {
        List<TriptypeParserResult> triptypeResults = new ArrayList<>();
        Map<String, String> triptypeDataSources = new HashMap<>();
        triptypeDataSources.put("direct", TRIPTYPE_DIRECT);
        triptypeDataSources.put("oneway", TRIPTYPE_ONEWAY);

        List<List<NGram>> ngrams = KeywordEntityParserUtils.generateNGrams(text, 3);
        for (Map.Entry<String, String> entry : triptypeDataSources.entrySet()) {
            String triptype = entry.getKey();
            Set<String> keywords = KeywordEntityParserUtils.readEntityValues(entry.getValue());
            for (List<NGram> igrams : ngrams) {
                for (NGram igram : igrams) {
                    if(keywords.contains(igram.getValue())) {
                        TriptypeParserResult result = new TriptypeParserResult(triptype, igram.getIndexRange(), igram.getTokenRange());
                        List<Integer> overlayResults = KeywordEntityParserUtils.findOverlayResults(result, triptypeResults);
                        if(!overlayResults.isEmpty()) {
                            for(Integer idx: overlayResults) {
                                triptypeResults.remove(idx.intValue());
                            }
                        }
                        triptypeResults.add(result);
                    }
                }
            }
        }
        return triptypeResults;
    }
}

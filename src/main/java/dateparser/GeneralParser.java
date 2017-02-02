package dateparser;

import dateparser.places.PlaceDataSource;
import dateparser.places.PlaceParser;
import dateparser.places.PlaceParserResult;
import dateparser.places.PlaceRoleTagger;
import dateparser.triptype.TriptypeParser;
import dateparser.triptype.TriptypeParserResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nikolayivanov on 1/21/17.
 */
public class GeneralParser {

    private static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private static void testParser(String pathToDataSetFile) throws IOException {
        String json = readFile(pathToDataSetFile, Charset.defaultCharset());
        JSONArray arr = new JSONArray(json);
        List<String> taggedData = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject jsonObj = (JSONObject)arr.get(i);
            String text = jsonObj.getString("text");
            System.out.println(text);
            GeneralParserResult res = parse(text);
            System.out.println(res);
            List<ParserResult> allResults = new ArrayList<>();
            allResults.addAll(res.getDates());
            allResults.addAll(res.getPlaces());
            taggedData.add(getTaggedText(text, allResults));
        }
        for(String taggedSent: taggedData) {
            System.out.println("FlightSearch\t" + taggedSent);
        }
    }

    private static String getTaggedText(String text, List<ParserResult> results) {
        return getTaggedText(text, 0, text.length(), results);
    }

    private static String getTaggedText(String text, int start, int stop, List<ParserResult> results) {
        if (start >= text.length()) {
            return "";
        }
        ParserResult usedResult = null;
        for(ParserResult res: results) {
            if(res.getIndexRange().getStart() >= start && res.getIndexRange().getStop() <= stop) {
                usedResult = res;
                break;
            }
        }
        if(usedResult != null) {
            results.remove(usedResult);
            return getTaggedText(text, start, usedResult.getIndexRange().getStart(), results) +
                    usedResult.getLabel() +
                    getTaggedText(text, usedResult.getIndexRange().getStop() + 1, stop, results);
        } else {
            return text.substring(start, stop);
        }
    }

    private static void tagRoles(String text, List<ParserResult> results) {
        String taggedText = getTaggedText(text, new ArrayList<>(results)).toLowerCase();
        results.sort((ParserResult o1, ParserResult o2)->o1.getIndexRange().getStart() - o2.getIndexRange().getStart());
        String[] words = taggedText.replaceAll("[.,!?]", " ").split("\\s+");
        List<String> tokens = Arrays.stream(words).collect(Collectors.toList());
        System.out.println(tokens);
        int resIdx = 0;
        PlaceRoleTagger tagger = new PlaceRoleTagger();
        List<String> roleTags = tagger.tagTokens(tokens);
        for (int i = 0; i < roleTags.size(); i++) {
            if(resIdx < results.size() &&
                    tokens.get(i).equals(results.get(resIdx).getLabel())) {
                results.get(resIdx).setRole(roleTags.get(i));
                resIdx++;
            }
        }
    }

    public static GeneralParserResult parse(String text) throws IOException {
        List<DateParserResult> dpResult = ParseDate.parse(text);
        PlaceDataSource pds = new PlaceDataSource();
        pds.initialize();
        List<PlaceParserResult> ppResult = PlaceParser.parse(text, pds);
        List<TriptypeParserResult> tpResult = TriptypeParser.parse(text);
        List<ParserResult> allResults = new ArrayList<>();
        allResults.addAll(dpResult);
        allResults.addAll(ppResult);
        allResults.addAll(tpResult);
        tagRoles(text, allResults);
        GeneralParserResult gpResult = new GeneralParserResult(dpResult, ppResult, tpResult);
        return gpResult;
    }

    public static void main(String[] args) {
        try {
            testParser("src/main/resources/test.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

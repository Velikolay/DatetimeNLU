package dateparser;

import dateparser.places.PlaceDataSource;
import dateparser.places.PlaceParser;
import dateparser.places.PlaceParserResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        for (int i = 0; i < arr.length(); i++) {
            JSONObject jsonObj = (JSONObject)arr.get(i);
            String text = jsonObj.getString("text");
            System.out.println(text);
            GeneralParserResult res = parse(text);
            System.out.println(res);
            List<ParserResult> allResults = new ArrayList<>();
            allResults.addAll(res.getDates());
            allResults.addAll(res.getPlaces());
            System.out.println(replaceParseResults(text, allResults));
        }
    }

    private static String replaceParseResults(String text, List<ParserResult> results) {
        return replaceParseResults(text, 0, text.length(), results);
    }

    private static String replaceParseResults(String text, int start, int stop, List<ParserResult> results) {
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
            return replaceParseResults(text, start, usedResult.getIndexRange().getStart(), results) +
                    usedResult.getLabel() +
                    replaceParseResults(text, usedResult.getIndexRange().getStop() + 1, stop, results);
        } else {
            return text.substring(start, stop);
        }
    }

    public static GeneralParserResult parse(String text) throws IOException {
        List<DateParserResult> dpResult = ParseDate.parse(text);
        PlaceDataSource pds = new PlaceDataSource();
        pds.initialize();
        List<PlaceParserResult> ppResult = PlaceParser.parse(text, pds);
        GeneralParserResult gpResult = new GeneralParserResult(dpResult, ppResult);
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

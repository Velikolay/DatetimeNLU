package dateparser.places;

import dateparser.IntegerRange;
import dateparser.NGram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nikolayivanov on 1/20/17.
 */
public class PlaceParser {
    private static List<NGram> ngrams(int n, String[] words) {
        List<NGram> ngrams = new ArrayList<>();
        for (int i = 0; i < words.length - n + 1; i++) {
            ngrams.add(new NGram(concat(words, i, i + n), n, new IntegerRange(i, i + n - 1)));
        }
        return ngrams;
    }

    private static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }

    private static List<List<NGram>> generateNGrams(String text, int n) {
        text = text.toLowerCase();
        System.out.println(text);
        String[] words = text.replaceAll("[.,!?]", " ").split("\\s+");
        List<String> wordsList = Arrays.stream(words).collect(Collectors.toList());
        System.out.println(wordsList);
        List<List<NGram>> ngrams = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            List<NGram> igrams = new ArrayList<>();
            ngrams.add(igrams);
            int nextNGramStart = 0;
            for (NGram ngram : ngrams(i, words)) {
                List<IntegerRange> indexRanges = generateNGramIndexRanges(text, nextNGramStart, words, ngram);
                nextNGramStart = indexRanges.get(0).getStop();
                ngram.setIndexRange(new IntegerRange(indexRanges.get(0).getStart(), indexRanges.get(indexRanges.size() - 1).getStop()));
                igrams.add(ngram);
            }
        }
        return ngrams;
    }

    private static List<IntegerRange> generateNGramIndexRanges(String text, int from, String[] tokens, NGram ngram) {
        List<IntegerRange> ranges = new ArrayList<>();
        for (int i = ngram.getTokenRange().getStart(); i <= ngram.getTokenRange().getStop(); i++) {
            String startToken = tokens[i];
            int startIndex = text.indexOf(startToken, from);
            int stopIndex = startIndex + startToken.length() - 1;
            //System.out.println("Start: " + startIndex + ", Stop: " + stopIndex);
            ranges.add(new IntegerRange(startIndex, stopIndex));
            from = stopIndex;
        }
        return ranges;
    }

    private static List<PlaceParserResult> extractPlaces(List<List<NGram>> ngrams, PlaceDataSource placeDataSource) {
        List<PlaceParserResult> placeResults = new ArrayList<>();
        for (List<NGram> igrams : ngrams) {
            for (NGram igram : igrams) {
                PlaceInfo placeInfo = placeDataSource.getPlaceInfo(igram.getValue());
                if(!placeInfo.isEmpty()) {
                    PlaceParserResult result = new PlaceParserResult(placeInfo, igram.getIndexRange(), igram.getTokenRange());

                    List<Integer> substringResults = findSubstringResults(result, placeResults);
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

    private static List<Integer> findSubstringResults(PlaceParserResult result, List<PlaceParserResult> searchResults) {
        List<Integer> substringResultIndexes = new ArrayList<>();
        for(int i=searchResults.size() - 1; i>=0; i--) {
            if(searchResults.get(i).getIndexRange().getStart() >= result.getIndexRange().getStart() &&
                    searchResults.get(i).getIndexRange().getStop() <= result.getIndexRange().getStop()) {
                substringResultIndexes.add(i);
            }
        }
        return substringResultIndexes;
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
        List<List<NGram>> ngrams = generateNGrams(text, 4);
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

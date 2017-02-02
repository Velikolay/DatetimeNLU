package dateparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nikolayivanov on 2/2/17.
 */
public class KeywordEntityParserUtils {
    public static List<String> textTokanization(String text) {
        String[] words = text.replaceAll("[.,!?]", " ").split("\\s+");
        return Arrays.stream(words).collect(Collectors.toList());
    }

    public static List<List<NGram>> generateNGrams(String text, int n) {
        text = text.toLowerCase();
        List<String> wordsList = textTokanization(text);
        System.out.println(wordsList);
        List<List<NGram>> ngrams = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            List<NGram> igrams = new ArrayList<>();
            ngrams.add(igrams);
            int nextNGramStart = 0;
            for (NGram ngram : ngrams(i, wordsList)) {
                List<IntegerRange> indexRanges = generateNGramIndexRanges(text, nextNGramStart, wordsList, ngram);
                nextNGramStart = indexRanges.get(0).getStop();
                ngram.setIndexRange(new IntegerRange(indexRanges.get(0).getStart(), indexRanges.get(indexRanges.size() - 1).getStop()));
                igrams.add(ngram);
            }
        }
        return ngrams;
    }

    public static List<Integer> findOverlayResults(ParserResult result, List<? extends ParserResult> searchResults) {
        List<Integer> substringResultIndexes = new ArrayList<>();
        for(int i=searchResults.size() - 1; i>=0; i--) {
            if(searchResults.get(i).getIndexRange().getStart() >= result.getIndexRange().getStart() &&
                    searchResults.get(i).getIndexRange().getStop() <= result.getIndexRange().getStop()) {
                substringResultIndexes.add(i);
            }
        }
        return substringResultIndexes;
    }

    public static Set<String> readEntityValues(String fileName) throws FileNotFoundException {
        Set<String> res = new HashSet<>();
        FileReader in = new FileReader(fileName);
        CsvReader csvReader = new CsvReader(new BufferedReader(in));
        List<List<String>> csvTable = csvReader.readRecords();
        for(List<String> row: csvTable) {
            if(!res.contains(row.get(0).toLowerCase())) {
                res.add(row.get(0).toLowerCase());
            }
        }
        return res;
    }

    private static List<NGram> ngrams(int n, List<String> words) {
        List<NGram> ngrams = new ArrayList<>();
        for (int i = 0; i < words.size() - n + 1; i++) {
            ngrams.add(new NGram(concat(words, i, i + n), n, new IntegerRange(i, i + n - 1)));
        }
        return ngrams;
    }

    private static String concat(List<String> words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words.get(i));
        return sb.toString();
    }

    private static List<IntegerRange> generateNGramIndexRanges(String text, int from, List<String> tokens, NGram ngram) {
        List<IntegerRange> ranges = new ArrayList<>();
        for (int i = ngram.getTokenRange().getStart(); i <= ngram.getTokenRange().getStop(); i++) {
            String startToken = tokens.get(i);
            int startIndex = text.indexOf(startToken, from);
            int stopIndex = startIndex + startToken.length() - 1;
            //System.out.println("Start: " + startIndex + ", Stop: " + stopIndex);
            ranges.add(new IntegerRange(startIndex, stopIndex));
            from = stopIndex;
        }
        return ranges;
    }

}

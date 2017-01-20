package dateparser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ParseDate {

    private static List<DateParserResult> parse(String input) throws IOException {
        //long start = System.currentTimeMillis();
        CharStream stream = new ANTLRNoCaseInputStream(input);
        DateLexer lexer = new DateLexer(stream);
        CommonTokenStream cts = new CommonTokenStream(lexer);
        cts.fill();
        List<Token> tokens = cts.getTokens();
        //System.out.println(tokens);
        //System.out.println("Lexer time: " + String.valueOf(System.currentTimeMillis() - start));

        List<DateParserResult> parserResults = parseRecursive(tokens, false);
        return parserResults;
    }

    private static List<DateParserResult> parseRecursive(List<Token> tokens, boolean stopOnFirstResult) {
        if (tokens.isEmpty()) {
            return Collections.emptyList();
        }
        List<DateParserResult> parserResults = new ArrayList<>();
        int wordTokenIndex = 0;
        Token nextToken = tokens.get(0);
        while (nextToken.getType() != Token.EOF) {
            tokens = tokens.subList(wordTokenIndex, tokens.size());
            DateParserTokenSource dateParserTokenSource = new DateParserTokenSource(tokens);
            CommonTokenStream ctss = new CommonTokenStream(dateParserTokenSource);
            DateParser parser = new DateParser(ctss);
            DateParserListenerImpl extractor = new DateParserListenerImpl();
            parser.addParseListener(extractor);
            long start = System.currentTimeMillis();
            DateParser.ParseContext parseContext = parser.parse();
            //System.out.println("Single parse time: " + String.valueOf(System.currentTimeMillis() - start));

            RecognitionException recogEx = getRecognitionException(parseContext);
            if(recogEx != null) {
                //System.out.println(recogEx);
                //System.out.println("Offending token:" + recogEx.getOffendingToken());
                if(recogEx.getOffendingToken().getTokenIndex() > 0) {
                    // Remove tokens from the back
                    List<Token> tokensTemp = new ArrayList<>(tokens.subList(0, recogEx.getOffendingToken().getTokenIndex() - 1));
                    tokensTemp.add(new CommonToken(Token.EOF));
                    //System.out.println(tokensTemp);
                    List<DateParserResult> backwardsDeductionResults = parseRecursive(tokensTemp, true);
                    if (!backwardsDeductionResults.isEmpty()) {
                        if (stopOnFirstResult) {
                            return backwardsDeductionResults;
                        }
                        DateParserResult bdr = backwardsDeductionResults.get(0);
                        parserResults.add(bdr);
                        if (bdr.getStopTokenIndex() + 1 < tokens.size()) {
                            wordTokenIndex = getIndexOfTheNextWordTokenIndex(tokens, bdr.getStopTokenIndex() + 1);
                            nextToken = dateParserTokenSource.getToken(wordTokenIndex);
                            continue;
                        }
                    }
                }
                wordTokenIndex = getIndexOfTheNextWordTokenIndex(tokens);
                nextToken = dateParserTokenSource.getToken(wordTokenIndex);
            } else {
                wordTokenIndex = dateParserTokenSource.nextTokenIndex();
                Token tokenAfterStop = dateParserTokenSource.getToken(wordTokenIndex - 1);
                if (tokenAfterStop.getType() == DateLexer.WHITE_SPACE
                        || tokenAfterStop.getType() == DateLexer.COMMA
                        || tokenAfterStop.getType() == DateLexer.DOT
                        || tokenAfterStop.getType() == Token.EOF) {
                    DateParserResult result = new DateParserResult(
                            parseContext.getStart().getStartIndex(),
                            parseContext.getStart().getTokenIndex(),
                            parseContext.getStop().getStopIndex(),
                            parseContext.getStop().getTokenIndex(),
                            extractor.getLocalDateTime());
                    parserResults.add(result);
                    if(stopOnFirstResult) {
                        return parserResults;
                    }
                }
                nextToken = dateParserTokenSource.nextTokenLookup();
            }
        }
        return parserResults;
    }

    private static RecognitionException getRecognitionException(ParserRuleContext ctx) {
        if(ctx.exception != null) {
            return ctx.exception;
        }

        for (ParseTree pt: ctx.children) {
            if(pt instanceof ParserRuleContext) {
                ParserRuleContext pcChild = (ParserRuleContext) pt;
                RecognitionException ex = getRecognitionException(pcChild);
                if(ex != null) {
                    return ex;
                }
            }
        }
        return null;
    }

    private static int getIndexOfTheNextWordTokenIndex(List<Token> tokens) {
        return getIndexOfTheNextWordTokenIndex(tokens, 0);
    }

    private static int getIndexOfTheNextWordTokenIndex(List<Token> tokens, int start) {
        for (int i = start; i < tokens.size(); i++) {
            if(tokens.get(i).getType() == DateLexer.WHITE_SPACE) {
                return i + 1;
            }
        }
        return tokens.size() - 1;
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private static void findDates(String pathToDataSetFile) throws IOException {
        String json = readFile(pathToDataSetFile, Charset.defaultCharset());
        JSONArray arr = new JSONArray(json);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject jsonObj = (JSONObject)arr.get(i);
            String text = jsonObj.getString("text");
            System.out.println(text);
            System.out.println(parse(text));
        }
    }

    public static void main(String[] args) {
        String input1 = "I'm flying 22nd  Feb 2022 and I want a ticket next Friday so come on Mar, garet";
        String input = "tuesday next week";
        //String input = "mar gsad";
        String input2 = "7th of jan, 22gasd";
        //String input = "03/12 next year"; // needs to be locale specific
        //String input = "in 9th days from now";
        //String input = "last mon.";
        //String input = "yesterday";
        try {
            System.out.println(parse(input));
            //System.out.println(parse(input2));
            long start = System.currentTimeMillis();
            //findDates("src/main/resources/test.json");
            System.out.println(System.currentTimeMillis() - start);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
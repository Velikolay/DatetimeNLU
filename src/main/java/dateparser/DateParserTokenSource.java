package dateparser;

import org.antlr.v4.runtime.*;

import java.util.ArrayList;
import java.util.List;

public class DateParserTokenSource implements TokenSource {
    private static final Token EOF_TOKEN = new CommonToken(Token.EOF);

    private List<Token> _tokens;
    private int _index = 0;
    private TokenFactory<?> _factory;

  
    public DateParserTokenSource(List<Token> tokens) {
        _tokens = new ArrayList<>(tokens);
    }

    @Override
    public Token nextToken() {
        return _tokens.size() > _index ? _tokens.get(_index++) : EOF_TOKEN;
    }

    public Token getToken(int idx) {
        return _tokens.get(idx);
    }

    public int nextTokenIndex() {
        return _index;
    }

    public Token nextTokenLookup() {
        return _tokens.size() > _index ? _tokens.get(_index) : EOF_TOKEN;
    }

    @Override
    public int getLine() {
        return this._tokens.get(_index).getLine();
    }

    @Override
    public int getCharPositionInLine() {
        return this._tokens.get(_index).getCharPositionInLine();
    }

    @Override
    public CharStream getInputStream() {
        return this._tokens.get(_index).getInputStream();
    }

    @Override
    public String getSourceName() {
        return "DateParser";
    }

    @Override
    public void setTokenFactory(TokenFactory<?> tokenFactory) {
        this._factory = tokenFactory;
    }

    @Override
    public TokenFactory<?> getTokenFactory() {
        return this._factory;
    }

}

package dateparser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;


class ANTLRNoCaseInputStream extends ANTLRInputStream {

    ANTLRNoCaseInputStream(String input) {
        super(input);
    }

    @Override
    public int LA(int i) {
        if (i == 0) return 0;
        if (i < 0) i++;
        if ((p + i - 1) >= n) return CharStream.EOF;
        return Character.toLowerCase(data[p + i - 1]);
    }
}
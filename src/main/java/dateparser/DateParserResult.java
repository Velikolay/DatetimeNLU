package dateparser;

import java.time.LocalDateTime;

/**
 * Created by nikolayivanov on 1/15/17.
 */
public class DateParserResult extends ParserResult {
    private LocalDateTime dateTime;

    public DateParserResult( LocalDateTime dateTime, IntegerRange indexRange, IntegerRange tokenRange) {
        super(indexRange, tokenRange);
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String getLabel() {
        return "en:date";
    }

    @Override
    public String toString() {
        return "DateParserResult{" +
                "dateTime=" + dateTime +
                '}';
    }
}

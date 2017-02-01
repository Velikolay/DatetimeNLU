package dateparser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by nikolayivanov on 1/15/17.
 */
public class DateParserResult extends ParserResult {
    @JsonIgnore
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

    @JsonProperty("datetime") // or whatever name you need in JSON
    private String getDateTimeISOString() {
        return this.dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
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

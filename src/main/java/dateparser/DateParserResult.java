package dateparser;

import java.time.LocalDateTime;

/**
 * Created by nikolayivanov on 1/15/17.
 */
public class DateParserResult {
    private int startIndex;
    private int startTokenIndex;
    private int stopIndex;
    private int stopTokenIndex;
    private LocalDateTime dateTime;

    public DateParserResult() {
    }

    public DateParserResult(int startIndex, int startTokenIndex, int stopIndex, int stopTokenIndex, LocalDateTime dateTime) {
        this.startIndex = startIndex;
        this.startTokenIndex = startTokenIndex;
        this.stopIndex = stopIndex;
        this.stopTokenIndex = stopTokenIndex;
        this.dateTime = dateTime;
    }

    public int getStartTokenIndex() {
        return startTokenIndex;
    }

    public void setStartTokenIndex(int startTokenIndex) {
        this.startTokenIndex = startTokenIndex;
    }

    public int getStopTokenIndex() {
        return stopTokenIndex;
    }

    public void setStopTokenIndex(int stopTokenIndex) {
        this.stopTokenIndex = stopTokenIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getStopIndex() {
        return stopIndex;
    }

    public void setStopIndex(int stopIndex) {
        this.stopIndex = stopIndex;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "DateParserResult{" +
                "startIndex=" + startIndex +
                ", stopIndex=" + stopIndex +
                ", dateTime=" + dateTime +
                '}';
    }

}

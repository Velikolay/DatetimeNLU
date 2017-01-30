package dateparser;

/**
 * Created by nikolayivanov on 1/28/17.
 */
public class IntegerRange {
    private int start;
    private int stop;

    public IntegerRange(int start, int stop) {
        this.start = start;
        this.stop = stop;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    @Override
    public String toString() {
        return "IntegerRange{" +
                "start=" + start +
                ", stop=" + stop +
                '}';
    }
}

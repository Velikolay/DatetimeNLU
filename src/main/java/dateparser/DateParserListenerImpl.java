package dateparser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoUnit.YEARS;

/**
 * Created by nikolayivanov on 1/4/17.
 */
class DateParserListenerImpl extends DateParserBaseListener {

    private int number;
    private int year;
    private int monthNum;
    private int dayNum;
    private DayOfWeek dayOfWeek;
    private Month month;
    private LocalDateTime dateTime;

    public DateParserListenerImpl() {
        this.dateTime = LocalDateTime.now(Clock.systemUTC());
    }

    @Override
    public void enterParse(DateParser.ParseContext ctx) {
        super.enterParse(ctx);
    }

    @Override
    public void exitParse(DateParser.ParseContext ctx) {
        super.exitParse(ctx);
    }

    @Override
    public void enterSpecific_date(DateParser.Specific_dateContext ctx) {
        super.enterSpecific_date(ctx);
    }

    @Override
    public void exitSpecific_date(DateParser.Specific_dateContext ctx) {
        super.exitSpecific_date(ctx);
    }

    @Override
    public void enterToday(DateParser.TodayContext ctx) {
        super.enterToday(ctx);
    }

    @Override
    public void exitToday(DateParser.TodayContext ctx) {
        super.exitToday(ctx);
    }

    @Override
    public void enterTomorrow(DateParser.TomorrowContext ctx) {
        super.enterTomorrow(ctx);
    }

    @Override
    public void exitTomorrow(DateParser.TomorrowContext ctx) {
        this.dateTime = this.dateTime.plusDays(1);
        super.exitTomorrow(ctx);
    }

    @Override
    public void enterYesterday(DateParser.YesterdayContext ctx) {
        this.dateTime = this.dateTime.minusDays(1);
        super.enterYesterday(ctx);
    }

    @Override
    public void exitYesterday(DateParser.YesterdayContext ctx) {
        super.exitYesterday(ctx);
    }


    @Override
    public void enterRelative_days_from_now(DateParser.Relative_days_from_nowContext ctx) {
        super.enterRelative_days_from_now(ctx);
    }

    @Override
    public void exitRelative_days_from_now(DateParser.Relative_days_from_nowContext ctx) {
        this.dateTime = this.dateTime.plusDays(this.number);
        super.exitRelative_days_from_now(ctx);
    }

    @Override
    public void enterRelative_date(DateParser.Relative_dateContext ctx) {
        super.enterRelative_date(ctx);
    }

    @Override
    public void exitRelative_date(DateParser.Relative_dateContext ctx) {
        super.exitRelative_date(ctx);
    }

    @Override
    public void enterNext_week(DateParser.Next_weekContext ctx) {
        super.enterNext_week(ctx);
    }

    @Override
    public void exitNext_week(DateParser.Next_weekContext ctx) {
        this.dateTime = this.dateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        super.exitNext_week(ctx);
    }

    @Override
    public void enterLast_week(DateParser.Last_weekContext ctx) {
        super.enterLast_week(ctx);
    }

    @Override
    public void exitLast_week(DateParser.Last_weekContext ctx) {
        int comp = this.dateTime.getDayOfWeek().compareTo(DayOfWeek.MONDAY);
        if (comp >= 0) {
            this.dateTime = this.dateTime.minusWeeks(1);
        }
        this.dateTime = this.dateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        super.exitLast_week(ctx);
    }

    @Override
    public void enterThis_week(DateParser.This_weekContext ctx) {
        super.enterThis_week(ctx);
    }

    @Override
    public void exitThis_week(DateParser.This_weekContext ctx) {
        this.dateTime = this.dateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        super.exitThis_week(ctx);
    }

    @Override
    public void enterDay_of_neighbour_week(DateParser.Day_of_neighbour_weekContext ctx) {
        super.enterDay_of_neighbour_week(ctx);
    }

    @Override
    public void exitDay_of_neighbour_week(DateParser.Day_of_neighbour_weekContext ctx) {
        if (this.dayOfWeek != null) {
            // We are starting from Monday of the specified neighbouring week
            this.dateTime = this.dateTime.with(TemporalAdjusters.nextOrSame(this.dayOfWeek));
        }
        super.exitDay_of_neighbour_week(ctx);
    }

    @Override
    public void enterNext_day_of_week(DateParser.Next_day_of_weekContext ctx) {
        //System.out.println("next day of week enter");
        super.enterNext_day_of_week(ctx);
    }

    @Override
    public void exitNext_day_of_week(DateParser.Next_day_of_weekContext ctx) {
        if (this.dayOfWeek != null) {
            this.dateTime = this.dateTime.with(TemporalAdjusters.next(this.dayOfWeek));
        }
        //System.out.println("next day of week exit");
        super.exitNext_day_of_week(ctx);
    }

    @Override
    public void enterPrevious_day_of_week(DateParser.Previous_day_of_weekContext ctx) {
        super.enterPrevious_day_of_week(ctx);
    }

    @Override
    public void exitPrevious_day_of_week(DateParser.Previous_day_of_weekContext ctx) {
        if (this.dayOfWeek != null) {
            this.dateTime = this.dateTime.with(TemporalAdjusters.previous(this.dayOfWeek));
        }
        super.exitPrevious_day_of_week(ctx);
    }

    @Override
    public void enterThis_day_of_week(DateParser.This_day_of_weekContext ctx) {
        super.enterThis_day_of_week(ctx);
    }

    @Override
    public void exitThis_day_of_week(DateParser.This_day_of_weekContext ctx) {
        if (this.dayOfWeek != null) {
            int comp = this.dateTime.getDayOfWeek().compareTo(this.dayOfWeek);
            if (comp < 0) {
                this.dateTime = this.dateTime.with(TemporalAdjusters.next(this.dayOfWeek));
            } else if (comp > 0) {
                this.dateTime = this.dateTime.with(TemporalAdjusters.previous(this.dayOfWeek));
            }
        }
        super.exitThis_day_of_week(ctx);
    }

    @Override
    public void enterSpelled_month_day(DateParser.Spelled_month_dayContext ctx) {
        super.enterSpelled_month_day(ctx);
    }

    @Override
    public void exitSpelled_month_day(DateParser.Spelled_month_dayContext ctx) {
        MonthDay monthDay = MonthDay.of(this.month, this.number);
        this.dateTime = this.dateTime.with(nextMonthDay(monthDay));
        super.exitSpelled_month_day(ctx);
    }

    private static TemporalAdjuster nextMonthDay(MonthDay monthDay) {
        return (temporal) -> {
            int day = temporal.get(DAY_OF_MONTH);
            int month = temporal.get(MONTH_OF_YEAR);
            int targetDay = monthDay.getDayOfMonth();
            int targetMonth = monthDay.getMonthValue();
            return !MonthDay.of(month, day).isAfter(monthDay)
                    ? temporal.with(MONTH_OF_YEAR, targetMonth).with(DAY_OF_MONTH, targetDay)
                    : temporal.with(MONTH_OF_YEAR, targetMonth).with(DAY_OF_MONTH, targetDay).plus(1, YEARS);
        };
    }

    @Override
    public void enterSpelled_one_to_thirty_one(DateParser.Spelled_one_to_thirty_oneContext ctx) {
        super.enterSpelled_one_to_thirty_one(ctx);
    }

    @Override
    public void exitSpelled_one_to_thirty_one(DateParser.Spelled_one_to_thirty_oneContext ctx) {
        this.number = ctx.numVal;
        //System.out.println(this.number);
        super.exitSpelled_one_to_thirty_one(ctx);
    }

    @Override
    public void enterSpelled_first_to_thirty_first(DateParser.Spelled_first_to_thirty_firstContext ctx) {
        super.enterSpelled_first_to_thirty_first(ctx);
    }

    @Override
    public void exitSpelled_first_to_thirty_first(DateParser.Spelled_first_to_thirty_firstContext ctx) {
        this.number = ctx.numVal;
        //System.out.println(this.number);
        super.exitSpelled_first_to_thirty_first(ctx);
    }

    @Override
    public void enterDay_of_week(DateParser.Day_of_weekContext ctx) {
        //System.out.println("day of week enter");
        super.enterDay_of_week(ctx);
    }

    @Override
    public void exitDay_of_week(DateParser.Day_of_weekContext ctx) {
        //System.out.println("day of week exit");
        this.dayOfWeek = ctx.dow;
        //System.out.println("Day of week: " + this.dayOfWeek);
        super.exitDay_of_week(ctx);
    }

    @Override
    public void enterSpelled_month(DateParser.Spelled_monthContext ctx) {
        super.enterSpelled_month(ctx);
    }

    @Override
    public void exitSpelled_month(DateParser.Spelled_monthContext ctx) {
        this.month = ctx.mnt;
        //System.out.println(this.month);
        MonthDay monthDay = MonthDay.of(this.month, 1);
        this.dateTime = this.dateTime.with(nextMonthDay(monthDay));
        super.exitSpelled_month(ctx);
    }

    @Override
    public void enterInt_01_to_31_optional_prefix(DateParser.Int_01_to_31_optional_prefixContext ctx) {
        super.enterInt_01_to_31_optional_prefix(ctx);
    }

    @Override
    public void exitInt_01_to_31_optional_prefix(DateParser.Int_01_to_31_optional_prefixContext ctx) {
        this.number = parseNumberNoOptionalPrefix(ctx.getText());
        //System.out.println(this.number);
        super.exitInt_01_to_31_optional_prefix(ctx);
    }

    @Override
    public void enterNext_year(DateParser.Next_yearContext ctx) {
        super.enterNext_year(ctx);
    }

    @Override
    public void exitNext_year(DateParser.Next_yearContext ctx) {
        this.year = LocalDateTime.now(Clock.systemUTC()).getYear() + 1;
        super.exitNext_year(ctx);
    }

    @Override
    public void enterLast_year(DateParser.Last_yearContext ctx) {
        super.enterLast_year(ctx);
    }

    @Override
    public void exitLast_year(DateParser.Last_yearContext ctx) {
        this.year = LocalDateTime.now(Clock.systemUTC()).getYear() - 1;
        super.exitLast_year(ctx);
    }

    @Override
    public void enterThis_year(DateParser.This_yearContext ctx) {
        super.enterThis_year(ctx);
    }

    @Override
    public void exitThis_year(DateParser.This_yearContext ctx) {
        this.year = LocalDateTime.now(Clock.systemUTC()).getYear();
        super.exitThis_year(ctx);
    }

    @Override
    public void enterMonth_day_year(DateParser.Month_day_yearContext ctx) {
        super.enterMonth_day_year(ctx);
    }

    @Override
    public void exitMonth_day_year(DateParser.Month_day_yearContext ctx) {
        this.dateTime = this.dateTime.withYear(this.year);
        super.exitMonth_day_year(ctx);
    }

    @Override
    public void enterFormat_full_date(DateParser.Format_full_dateContext ctx) {
        super.enterFormat_full_date(ctx);
    }

    @Override
    public void exitFormat_full_date(DateParser.Format_full_dateContext ctx) {
        this.dateTime = this.dateTime.withDayOfMonth(this.dayNum).withYear(this.year).withMonth(this.monthNum);
        super.exitFormat_full_date(ctx);
    }

    @Override
    public void enterFormat_month_day(DateParser.Format_month_dayContext ctx) {
        super.enterFormat_month_day(ctx);
    }

    @Override
    public void exitFormat_month_day(DateParser.Format_month_dayContext ctx) {
        this.dateTime = this.dateTime.withDayOfMonth(this.dayNum).withMonth(this.monthNum);
        super.exitFormat_month_day(ctx);
    }

    @Override
    public void enterMonth_number(DateParser.Month_numberContext ctx) {
        super.enterMonth_number(ctx);
    }

    @Override
    public void exitMonth_number(DateParser.Month_numberContext ctx) {
        this.monthNum = parseNumberNoOptionalPrefix(ctx.getText());
        //System.out.println(this.monthNum);
        super.exitMonth_number(ctx);
    }

    @Override
    public void enterDay_number(DateParser.Day_numberContext ctx) {
        super.enterDay_number(ctx);
    }

    @Override
    public void exitDay_number(DateParser.Day_numberContext ctx) {
        this.dayNum = parseNumberNoOptionalPrefix(ctx.getText());
        //System.out.println(this.dayNum);
        super.exitDay_number(ctx);
    }

    @Override
    public void enterShort_year(DateParser.Short_yearContext ctx) {
        super.enterShort_year(ctx);
    }

    @Override
    public void exitShort_year(DateParser.Short_yearContext ctx) {
        int yearSuffix = parseNumberNoOptionalPrefix(ctx.getText());
        this.year = 2000 + yearSuffix;
        //System.out.println(this.year);
        super.exitShort_year(ctx);
    }

    @Override
    public void enterFull_year(DateParser.Full_yearContext ctx) {
        super.enterFull_year(ctx);
    }

    private boolean isFullYear(String s) {
        return isFullYear(s,10);
    }

    private boolean isFullYear(String s, int radix) {
        if(s.length() != 4) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

    @Override
    public void exitFull_year(DateParser.Full_yearContext ctx) {
        String fourDigit = ctx.getText();
        //System.out.println(fourDigit);
        if (this.isFullYear(fourDigit)) {
            this.year = Integer.valueOf(fourDigit);
        }
        this.dateTime = this.dateTime.withMonth(1).withDayOfMonth(1).withYear(this.year);
        //System.out.println(this.year);
        super.exitFull_year(ctx);
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        super.enterEveryRule(ctx);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        super.exitEveryRule(ctx);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        super.visitTerminal(node);
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        super.visitErrorNode(node);
    }

    private int parseNumberNoOptionalPrefix(String number) {
        if (!number.startsWith("0")) {
            return Integer.valueOf(number);
        } else {
            return Integer.valueOf(number.substring(1));
        }
    }

    public LocalDateTime getLocalDateTime() {
        return this.dateTime;
    }
}

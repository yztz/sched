package top.yztz.sched.pojo;

import java.util.ArrayList;
import java.util.List;

import top.yztz.sched.persistence.FieldName;

public class Date {
    public static final int ALL_WEEK = 0b1111_1111_1111_1111_1111;
    public static final int SINGLE_WEEK = 0b0101_0101_0101_0101_0101;
    public static final int DOUBLE_WEEK = 0b1010_1010_1010_1010_1010;

    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;

    /* bitmap */
    @FieldName("cweek")
    private int weeks;
    @FieldName("cday")
    private int day;
    @FieldName("cstart")
    private int startTime;
    @FieldName("cend")
    private int endTime;

    public Date() {

    }

    public List<Integer> getWeeks() {
        return parseWeek(weeks);
    }

    public void setWeeks(int bitmap) {
        this.weeks = bitmap;
    }

    public void setWeeks(List<Integer> weeks) {
        this.weeks = week2bitmap(weeks);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getTimeLen() {
        return endTime - startTime + 1;
    }

    @Override
    public String toString() {
        return "Date{" +
                "weeks=" + weeks +
                ", day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public static List<Integer> parseWeek(int i) {
        List<Integer> ans = new ArrayList<>();

        for (int week = 1; week <= 20; week++) {
            if ((i >> (week - 1)) % 2 == 1) ans.add(week);
        }

        return ans;
    }

    public static int week2bitmap(int week) {
        return 1 << (week - 1);
    }

    public static int week2bitmap(List<Integer> weeks) {
        int ans = 0;
        for (int week : weeks) {
            ans |= 1 << (week - 1);
        }

        return ans;
    }

    public static int week2bitmap(int[] weeks) {
        int ans = 0;
        for (int week : weeks) {
            ans |= 1 << (week - 1);
        }

        return ans;
    }
}
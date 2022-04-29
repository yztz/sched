package top.yztz.sched.persistence;

import static top.yztz.sched.persistence.SqlHelper.DB_NAME;
import static top.yztz.sched.persistence.SqlHelper.TABLE_COURSE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.StringBuilderPrinter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import top.yztz.sched.pojo.Course;
import top.yztz.sched.pojo.Date;
import top.yztz.sched.utils.DBUtils;
import top.yztz.sched.utils.DateUtils;

public class DataHelper {
    private static final String TAG = "DataHelper";
    private static SqlHelper helper;
    private static SQLiteDatabase db;

    private static final String[] queryColumns =
            {"cid", "cname", "cteacher", "cday", "cstart", "cend", "cplace", "cweek"};

    public static void DBInit(Context context) {
        helper = new SqlHelper(context, DB_NAME, null, 5);
        db = helper.getWritableDatabase();
    }

    public static List<Course> getCoursesToday() {
        return getCoursesByWeekAndDay(DateUtils.weekNo, DateUtils.dayOfWeek);
    }

    public static List<Course> getCoursesTomorrow() {
        return getCoursesByWeekAndDay(DateUtils.t_weekNo, DateUtils.t_dayOfWeek);
    }

    public static List<Course> getCoursesByWeekAndDay(int week, int day) {
        week = Date.week2bitmap(week);
        return getCourses(week, day);
    }

    public static List<Course> getCoursesByWeek(int week) {
        week = Date.week2bitmap(week);
        return getCourses(week, -1);
    }

    public static List<Course> getCoursesByWeeks(List<Integer> weeks) {
        int bitmap = Date.week2bitmap(weeks);
        return getCourses(bitmap, -1);
    }

    @SuppressLint("DefaultLocale")
    private static List<Course> getCourses(int bitmap, int dayOfWeek) {
        Rule rule = new Rule();
        if (bitmap != -1)
            rule.addRule(String.format("(%d & ((~(cweek&%d))&(cweek|%d))) = 0", bitmap, bitmap, bitmap));
        if (dayOfWeek != -1)
            rule.addRule(String.format("cday = %d", dayOfWeek));


        Cursor cursor = db.query(TABLE_COURSE,
                queryColumns,
                rule.toString(),
                null, null, null, null);
        List<Course> ans = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            ans.add(DBUtils.parseCursor(cursor, Course.class));
        }
        Log.d(TAG, "getCoursesByWeekBitmap: " + ans.size());
        return ans;
    }

    static class Rule {
        private StringBuilder sb = new StringBuilder();
        private int ruleNum = 0;

        Rule addRule(String rule) {
            if (ruleNum == 0) {
                sb.append(rule);
            } else {
                sb.append(" AND ").append(rule);
            }
            ruleNum++;

            return this;
        }

        @Override
        public String toString() {
            return sb.toString();
        }
    }






}

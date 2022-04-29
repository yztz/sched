package top.yztz.sched.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import java.time.DayOfWeek;

import top.yztz.sched.utils.DBUtils;
import top.yztz.sched.pojo.Date;

public class SqlHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "schedlib";
    public static final String TABLE_COURSE = "course";

    public SqlHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE  `course` (\n" +
                "  `cid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "  `cname` int(3) NOT NULL,\n" +
                "  `cteacher` varchar(255) NOT NULL,\n" + // 其实可以分表
                "  `cday` int(2) NOT NULL,\n" +
                "  `cstart` int(2) NOT NULL,\n" +
                "  `cend`  int(2) NOT NULL,\n" +
                "  `cplace` varchar(255) NOT NULL,\n" +
                "  `cweek` int(4) DEFAULT 0 \n" + // bitmap
                ")";
        db.execSQL(sql);
        // This is only test data
        ContentValues values = new ContentValues();
        db.insert(TABLE_COURSE, null,
                DBUtils.buildCourse("安卓", "赵丽娜",Date.TUESDAY, "三教", 10, 12, Date.ALL_WEEK));
        db.insert(TABLE_COURSE, null,
                DBUtils.buildCourse("创新实践", "龚晓君",Date.WEDNESDAY, "三教", 3, 4, Date.ALL_WEEK));
        db.insert(TABLE_COURSE, null,
                DBUtils.buildCourse("形式与政策", "史维",Date.WEDNESDAY, "六教", 6, 7, Date.SINGLE_WEEK));
        db.insert(TABLE_COURSE, null,
                DBUtils.buildCourse("国际关系", "方建中",Date.TUESDAY, "七教", 6, 7, Date.ALL_WEEK));
        db.insert(TABLE_COURSE, null,
                DBUtils.buildCourse("不知名课程1", "不知名老师1", Date.FRIDAY, "十二教", 1, 2, Date.week2bitmap(new int[]{1, 2, 3})));
        db.insert(TABLE_COURSE, null,
                DBUtils.buildCourse("不知名课程2", "不知名老师2", Date.MONDAY, "三教", 3, 5, Date.ALL_WEEK));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        onCreate(sqLiteDatabase);
    }
}

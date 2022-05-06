package top.yztz.sched.utils;

import android.annotation.SuppressLint;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import top.yztz.sched.config.Config;

@SuppressLint({"SimpleDateFormat", "DefaultLocale"})
public class DateUtils {
    // require SDK 26
    private static LocalDate start;
    private static LocalDate today;
    private static LocalDate tomorrow;

    public static final int weekNo;
    public static final int t_weekNo;
    public static final int dayOfWeek;
    public static final int t_dayOfWeek;


    // 以后通过配置保存在sharedPreference


    static {
        start = LocalDate.parse(Config.startWeek);
        today = LocalDate.now();
//        today = LocalDate.parse("2022-04-26"); test
        tomorrow = today.plus(1, ChronoUnit.DAYS);

        weekNo = (int) start.until(today, ChronoUnit.WEEKS);
        t_weekNo = (int) start.until(tomorrow, ChronoUnit.WEEKS);
        dayOfWeek = today.getDayOfWeek().getValue();
        t_dayOfWeek = tomorrow.getDayOfWeek().getValue();
    }


}

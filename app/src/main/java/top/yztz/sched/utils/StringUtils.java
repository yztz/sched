package top.yztz.sched.utils;

import android.annotation.SuppressLint;

import androidx.annotation.IntRange;

@SuppressLint("DefaultLocale")
public class StringUtils {
    private static final char[] num2zh = {
            ' ', '一', '二', '三', '四', '五', '六', '日',
    };

    public static String week(int week) {
        return String.format("第%d周", week);
    }

    public static String dayOfWeek(int day) {
        return String.format("星期%c", num2zh[day]);
    }


    public static String firstToUpperCase(String str) {
        char[] chs = str.toCharArray();
        if (97 <= chs[0] && chs[0] <= 122) {
            chs[0] ^= 32;
        }
        return String.valueOf(chs);
    }
}

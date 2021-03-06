package top.yztz.sched.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import androidx.annotation.IntRange;

import java.util.ArrayList;
import java.util.List;

import top.yztz.sched.config.Config;

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

    public static List<Integer> str2weeks(String str) {
        if (TextUtils.isEmpty(str)) return null;
        List<Integer> ans = new ArrayList<>();
        String[] patterns = str.split(",");
        for (String pattern : patterns) {
            try {
                int i = Integer.parseInt(pattern.trim());
                if (i <= 0 || i > Config.WEEK_NUM) return null;
                ans.add(i);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return ans;
    }

    public static String firstToUpperCase(String str) {
        char[] chs = str.toCharArray();
        if (97 <= chs[0] && chs[0] <= 122) {
            chs[0] ^= 32;
        }
        return String.valueOf(chs);
    }
}

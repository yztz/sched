package top.yztz.sched.config;

import top.yztz.sched.R;

public class Config {
    public static final int MAX_TIME_LEN = 12;
    public static final String[][] TIME_TABLE = new String[MAX_TIME_LEN + 1][2];
    public static final int[] CARD_COLORS = {
            R.color.blue,
            R.color.pink,
            R.color.grass,
            R.color.orange,
    };

    public static String startWeek = "2022-02-21";

    static {
        TIME_TABLE[1] = new String[]{"8:05", "8:50"};
        TIME_TABLE[2] = new String[]{"8:55", "9:40"};
        TIME_TABLE[3] = new String[]{"10:00", "10:45"};
        TIME_TABLE[4] = new String[]{"10:50", "11:35"};
        TIME_TABLE[5] = new String[]{"11:40", "12:25"};
        TIME_TABLE[6] = new String[]{"13:30", "14:15"};
        TIME_TABLE[7] = new String[]{"14:20", "15:05"};
        TIME_TABLE[8] = new String[]{"15:15", "16:00"};
        TIME_TABLE[9] = new String[]{"16:05", "16:50"};
        TIME_TABLE[10] = new String[]{"18:30", "19:15"};
        TIME_TABLE[11] = new String[]{"19:20", "20:05"};
        TIME_TABLE[12] = new String[]{"20:10", "20:55"};
    }



}

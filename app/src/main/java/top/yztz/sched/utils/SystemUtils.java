package top.yztz.sched.utils;

import android.app.Service;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class SystemUtils {

    public static void vibrate(Context context, long mills) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(mills, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}

package top.yztz.sched.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import top.yztz.sched.pojo.Course;

public class ConfigHelper {
    private static final String TAG = "ConfigHelper";

    private static SharedPreferences courseSp;
    private static SharedPreferences.Editor courseSpEditor;

    public static void configInit(Context context) {
        courseSp = context.getSharedPreferences("course", Context.MODE_PRIVATE);
        courseSpEditor = courseSp.edit();
    }

    public static void putTemporaryCourse(Course course) {
        if (null == course) {
            Log.d(TAG, "putTemporaryCourse: course is null");
            return;
        }

        courseSpEditor.putString(String.valueOf(course.getId()), JSONObject.toJSONString(course));
        courseSpEditor.apply();
    }

    public static Course getTemporaryCourse(Course course) {
        if (null == course) {
            Log.d(TAG, "removeTemporaryCourse: course is null");
            return null;
        }
        String serializedCourse = courseSp.getString(String.valueOf(course.getId()), "");
        if (TextUtils.isEmpty(serializedCourse))
            return null;
        return JSONObject.parseObject(serializedCourse, Course.class);
    }

    public static void removeTemporaryCourse(Course course) {
        if (null == course) {
            Log.d(TAG, "removeTemporaryCourse: course is null");
            return;
        }
        courseSpEditor.remove(String.valueOf(course.getId()));
        courseSpEditor.apply();
    }

}

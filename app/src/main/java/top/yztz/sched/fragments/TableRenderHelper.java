package top.yztz.sched.fragments;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.transition.Transition;
import androidx.transition.TransitionInflater;
import androidx.transition.TransitionManager;

import java.util.List;
import java.util.Random;

import top.yztz.sched.R;
import top.yztz.sched.config.Config;
import top.yztz.sched.interfaces.CourseViewConstructor;
import top.yztz.sched.pojo.Course;
import top.yztz.sched.pojo.Date;
import top.yztz.sched.views.CourseView;

public class TableRenderHelper {
    private static final String TAG = "TableRenderHelp";
    private static int pre_color = -1;
    private static final Random random = new Random();

    private static int getRandomColor(Context context) {
        int n = Config.CARD_COLORS.length;
        int idx;
        while ((idx = random.nextInt(n)) == pre_color);
        pre_color = idx;
        return context.getColor(Config.CARD_COLORS[idx]);
    }

    public static void renderColumn(ViewGroup canvas, List<Course> courses, int unitHeight, CourseViewConstructor cc) {
        renderTable(canvas, courses, unitHeight, -1, true, cc);
    }

    public static void renderTable(ViewGroup canvas, List<Course> courses, int unitHeight, int unitWidth, CourseViewConstructor cc) {
        renderTable(canvas, courses, unitHeight, unitWidth, false, cc);
    }

    public static void renderTable(ViewGroup canvas, List<Course> courses, int unitHeight, int unitWidth, boolean singleCol, CourseViewConstructor cc) {
        Context context = canvas.getContext();
        Log.d(TAG, "renderTable: unitHeight = " + unitHeight);
        Transition trans = TransitionInflater.from(context).inflateTransition(R.transition.table_trans);
        TransitionManager.beginDelayedTransition(canvas, trans);
        canvas.removeAllViews();

        int width = singleCol ?  ViewGroup.LayoutParams.MATCH_PARENT : unitWidth;
        for (Course course : courses) {
            CourseView cv = new CourseView(context, course);
            // 随机颜色
            cv.setBgColor(getRandomColor(context));
            Date date = course.getDate();
            int dayOfWeek = date.getDay();
            int timeLen = date.getTimeLen();
            FrameLayout.LayoutParams pl = new FrameLayout.LayoutParams(
                    width,
                    timeLen * unitHeight);

            int left = singleCol ? 0 : (dayOfWeek - 1) * unitWidth;
            int top = (date.getStartTime() - 1) * unitHeight;
            pl.setMargins(left, top, 0, 0);
            if (cc != null) cc.process(cv);
            canvas.addView(cv, pl);
        }
    }
}

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

    public static void renderTable(ViewGroup canvas, List<Course> courses, int unitHeight) {
        Context context = canvas.getContext();
        Log.d(TAG, "renderTable: unitHeight = " + unitHeight);
        Transition trans = TransitionInflater.from(context).inflateTransition(R.transition.table_trans);
        TransitionManager.beginDelayedTransition(canvas, trans);
        canvas.removeAllViews();

        for (Course course : courses) {
            CourseView cv = new CourseView(context, course);
            cv.setBgColor(getRandomColor(context));
            Date date = course.getDate();
            FrameLayout.LayoutParams pl = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    date.getTimeLen() * unitHeight);
            pl.setMargins(0, (date.getStartTime() - 1) * unitHeight, 0, 0);
            canvas.addView(cv, pl);
        }
    }
}

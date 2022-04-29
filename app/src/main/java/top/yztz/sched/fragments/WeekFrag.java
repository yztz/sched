package top.yztz.sched.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import top.yztz.sched.R;
import top.yztz.sched.config.Config;
import top.yztz.sched.persistence.DataHelper;
import top.yztz.sched.pojo.Course;
import top.yztz.sched.utils.DateUtils;
import top.yztz.sched.utils.StringUtils;
import top.yztz.sched.views.TimeView;

public class WeekFrag extends Fragment {
    private Context context;
    private LinearLayout llTime, llDayOfWeek;
    private FrameLayout flContainer;

    private int unitHeight, unitWidth;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_landscape_body, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llTime = view.findViewById(R.id.time);
        llDayOfWeek = view.findViewById(R.id.day_of_week);
        flContainer = view.findViewById(R.id.content);

        initTimeNames();
        initDayNames();

        llTime.post(() -> {
            unitHeight = llTime.getChildAt(0).getHeight();
            llDayOfWeek.post(() -> {
                unitWidth = llDayOfWeek.getChildAt(0).getWidth();
                showWeek(DateUtils.weekNo);
            });
        });
    }

    private void showWeek(int weekNo) {
        List<Course> courses = DataHelper.getCoursesByWeek(weekNo);
        TableRenderHelper.renderTable(flContainer, courses, unitHeight, unitWidth, cv -> {
            cv.setTextSizeRatio(0.5f);
            cv.setRadius(10);
            cv.setPadding(2);
        });
    }

    private void initTimeNames() {
        for (int i = 1; i <= Config.MAX_TIME_LEN; i++) {
            TimeView tv = new TimeView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0);
            lp.gravity = Gravity.CENTER;
            lp.weight = 1;
            tv.setNoSize(15);
            tv.setLayoutParams(lp);
            tv.setNo(i);
            tv.hideDetail();
            llTime.addView(tv);
        }
    }

    private void initDayNames() {
        for (int i = 1; i <= 7; i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.gravity = Gravity.CENTER;
            lp.weight = 1;
            TextView tv = new TextView(context);
            if (i == DateUtils.dayOfWeek) {
                tv.setTextColor(context.getColor(R.color.black));
                tv.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            } else {
                tv.setTextColor(context.getColor(R.color.grey));
            }
            tv.setText(StringUtils.dayOfWeek(i));
            tv.setTextSize(13);
            tv.setGravity(Gravity.CENTER);
            llDayOfWeek.addView(tv, lp);
        }
    }
}

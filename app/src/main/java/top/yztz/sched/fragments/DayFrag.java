package top.yztz.sched.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import top.yztz.sched.interfaces.TouchEventListener;
import top.yztz.sched.persistence.DataHelper;
import top.yztz.sched.pojo.Course;
import top.yztz.sched.utils.DateUtils;
import top.yztz.sched.utils.StringUtils;
import top.yztz.sched.views.ActionbarView;
import top.yztz.sched.views.TimeView;

public class DayFrag extends Fragment implements ActionbarView.DayListener, TouchEventListener {
    private static final String TAG = "DayFrag";
    private LinearLayout llTime;
    private FrameLayout flContainer;
    private TextView tvWeek, tvDayOfWeek;
    private int unitHeight;

    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_portrait_body, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llTime = view.findViewById(R.id.time);
        flContainer = view.findViewById(R.id.content);
        tvWeek = view.findViewById(R.id.week);
        tvDayOfWeek = view.findViewById(R.id.day_of_week);

        initTimeNames();

        llTime.post(()->{
            this.unitHeight = llTime.getChildAt(0).getHeight();
            onDayChange(true);
        });

    }


    private void initTimeNames() {
        for (int i = 1; i <= Config.MAX_TIME_LEN; i++) {
            TimeView tv = new TimeView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.gravity = Gravity.CENTER;
            lp.weight = 1;
            tv.setLayoutParams(lp);
            tv.setNo(i);
            llTime.addView(tv);
        }
    }


    @Override
    public void onDayChange(boolean today) {
        toggleDate(today);
        toggleTable(today);
    }

    private void toggleDate(boolean today) {
        if (today) {
            tvWeek.setText(StringUtils.week(DateUtils.weekNo));
            tvDayOfWeek.setText(StringUtils.dayOfWeek(DateUtils.dayOfWeek));
        } else {
            tvWeek.setText(StringUtils.week(DateUtils.t_weekNo));
            tvDayOfWeek.setText(StringUtils.dayOfWeek(DateUtils.t_dayOfWeek));
        }
    }

    private void toggleTable(boolean today) {
        List<Course> courses = today ? DataHelper.getCoursesToday() : DataHelper.getCoursesTomorrow();
        flContainer.post(() -> {
            TableRenderHelper.renderTable(flContainer, courses, unitHeight);
        });
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return true;
    }
}









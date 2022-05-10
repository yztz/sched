package top.yztz.sched.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import top.yztz.sched.R;
import top.yztz.sched.interfaces.ConfirmCallback;
import top.yztz.sched.pojo.Course;
import top.yztz.sched.utils.DateUtils;
import top.yztz.sched.views.CourseForm;
import top.yztz.sched.views.WeekList;

public class SelectFrag extends Fragment implements CourseForm.StateListener, WeekList.StateListener {
    private static final String TAG = "SelectFrag";
    
    private FrameLayout flPanelContainer;
    private CourseForm courseForm;
    private WeekList weekList;
    private Context context;

    // 用于装载各类面板的统一布局参数
    private final FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_landscape_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flPanelContainer = view.findViewById(R.id.panel_container);
        initWeekList();
        initFormView();

        showWeekList(null);
    }

    private void initWeekList() {
        weekList = new WeekList(context);
        // 监听状态变化
        weekList.setStateListener(this);
    }


    private void initFormView() {
        courseForm = new CourseForm(context);
        // 监听状态变化
        courseForm.setStateListener(this);
    }

    public void quit(ConfirmCallback callback) {
        courseForm.quit(confirmed -> {
            if(null != callback) callback.callback(confirmed);
        });
    }

    /* 显示周次选择器 */
    public void showWeekList(ConfirmCallback callback) {
        courseForm.quit(confirmed -> {
            if (confirmed) {
                flPanelContainer.removeAllViews();
                flPanelContainer.addView(weekList, flp);
            }
            if (null != callback) callback.callback(confirmed);
        });
    }
    /* 显示表单 */
    public void showFormView(Course course, ConfirmCallback callback) {
        courseForm.quit(confirmed -> {
            if (confirmed) {
                courseForm.setCourse(course);
                flPanelContainer.removeAllViews();
                flPanelContainer.addView(courseForm, flp);
            }
            if (null != callback) callback.callback(confirmed);
        });

    }



    private void showWeek(int weekNo) {
        WeekFrag weekFrag = (WeekFrag) getParentFragmentManager().findFragmentByTag("week_table");
        if (null != weekFrag)
            weekFrag.showWeek(weekNo);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        courseForm.saveCurrentChanges();
    }


    @Override
    public void onSave(Course course) {
        // 保存时需要重新渲染
        showWeek(weekList.getCurrentWeek());
        // 返回weekList
        showWeekList(null);
    }

    @Override
    public void onBack(Course course) {

    }


    @Override
    public void onChange(int weekNo) {
        showWeek(weekNo);
    }
}

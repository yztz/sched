package top.yztz.sched.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import top.yztz.sched.CenterLinearLayoutManager;
import top.yztz.sched.R;
import top.yztz.sched.adapters.WeekAdapter;
import top.yztz.sched.interfaces.ConfirmCallback;
import top.yztz.sched.pojo.Course;
import top.yztz.sched.utils.DateUtils;
import top.yztz.sched.views.ConfirmDialog;
import top.yztz.sched.views.CourseFormView;
import top.yztz.sched.views.CourseView;

public class SelectFrag extends Fragment implements WeekAdapter.OnWeekClickListener {
    private FrameLayout flPanelContainer;
    private RecyclerView rvList;
    private CourseFormView formView;
    private Context context;


    // 用于装载各类面板的统一布局参数
    private FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
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
        scrollToCurrentWeek(DateUtils.weekNo);
    }


    private void initWeekList() {
        rvList = new RecyclerView(context);
        CenterLinearLayoutManager manager = new CenterLinearLayoutManager(context);
        rvList.setLayoutManager(manager);
        rvList.setAdapter(new WeekAdapter(context, this));
        rvList.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    private void initFormView() {
        formView = new CourseFormView(context);
    }


    public void showWeekList(ConfirmCallback callback) {
        System.out.println("showWeekList CALLED........");
        formView.quit(confirmed -> {
            if (confirmed) {
                flPanelContainer.removeAllViews();
                flPanelContainer.addView(rvList, flp);
            }
            if (null != callback) callback.callback(confirmed);
        });
    }

    public void showFormView(Course course, ConfirmCallback callback) {
        formView.quit(confirmed -> {
            if (confirmed) {
                formView.setCourse(course);
                flPanelContainer.removeAllViews();
                flPanelContainer.addView(formView, flp);
            }
            if (null != callback) callback.callback(confirmed);
        });

    }

    private void scrollToCurrentWeek(int weekNo) {
        // 延后等待装载组件完成，使动画明显
        rvList.postDelayed(()-> rvList.smoothScrollToPosition(weekNo - 1), 200);
    }


    // 刷新课表
    @Override
    public void onClick(int weekNo) {
        scrollToCurrentWeek(weekNo);
        WeekFrag weekFrag = (WeekFrag) getParentFragmentManager().findFragmentByTag("week_table");
        if (null != weekFrag)
            weekFrag.showWeek(weekNo);
    }

}

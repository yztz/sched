package top.yztz.sched.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import top.yztz.sched.CenterLinearLayoutManager;
import top.yztz.sched.adapters.WeekAdapter;
import top.yztz.sched.utils.DateUtils;

public class WeekList extends LinearLayout implements WeekAdapter.OnWeekClickListener {
    private RecyclerView rvList;

    // 这里adapter只是桥梁作用，应该是无状态的，所以currentWeek信息应该是当前view持有
    private WeekAdapter adapter;

    private int currentWeek = DateUtils.weekNo;

    private StateListener listener;

    public WeekList(Context context) {
        this(context, null);
    }

    public WeekList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setStateListener(StateListener listener) {
        this.listener = listener;
    }


    public void setCurrentWeek(int weekNo) {
        this.currentWeek = weekNo;
        adapter.setCurrentWeek(weekNo);
        scrollToCurrentWeek(weekNo);
    }

    public int getCurrentWeek() {
        return currentWeek;
    }

    private void init(Context context) {
        rvList = new RecyclerView(context);
        CenterLinearLayoutManager manager = new CenterLinearLayoutManager(context);
        adapter = new WeekAdapter(context, currentWeek);
        // 设置点击监听
        adapter.setOnWeekClickListener(this);
        rvList.setLayoutManager(manager);
        rvList.setAdapter(adapter);
        // 关闭过度滑动动画
        rvList.setOverScrollMode(View.OVER_SCROLL_NEVER);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(rvList, lp);

        rvList.postDelayed(()->scrollToCurrentWeek(DateUtils.weekNo), 200);
    }

    private void scrollToCurrentWeek(int weekNo) {
        // 延后等待装载组件完成，使动画明显
        rvList.smoothScrollToPosition(weekNo - 1);
    }

    // 监听weekList事件，点击刷新课表
    @Override
    public void onClick(int weekNo) {
        adapter.setCurrentWeek(weekNo);
        scrollToCurrentWeek(weekNo);
        if (null != listener) listener.onChange(weekNo);
    }

    public interface StateListener {
        void onChange(int weekNo);
    }
}

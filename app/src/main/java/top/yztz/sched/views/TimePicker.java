package top.yztz.sched.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import top.yztz.sched.CenterLinearLayoutManager;
import top.yztz.sched.adapters.TimeAdapter;

public class TimePicker extends LinearLayout {
    private RecyclerView recyclerView;
    private CenterLinearLayoutManager layoutManager;
    private TimeAdapter adapter;

    private LinearSnapHelper snapHelper = new LinearSnapHelper();

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        recyclerView = new RecyclerView(context);
        // 上下留白
        recyclerView.setPadding(0, 20, 0, 26);
        recyclerView.setClipToPadding(false);
        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        lp.gravity = Gravity.CENTER;
        addView(recyclerView, lp);

        adapter = new TimeAdapter(context);
        layoutManager = new CenterLinearLayoutManager(context);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) { // 滑动停止
                    int pos = ((RecyclerView.LayoutParams)snapHelper.findSnapView(layoutManager).getLayoutParams()).getViewAdapterPosition();
                    adapter.setCurrentTime(pos + 1);
                }
            }
        });

    }

    public void setTime(int time) {
        adapter.setCurrentTime(time);
        recyclerView.smoothScrollToPosition(time - 1);
    }

    public int getTime() {
        return adapter.getCurrentTime();
    }
}

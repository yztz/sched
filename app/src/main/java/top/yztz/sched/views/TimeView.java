package top.yztz.sched.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import top.yztz.sched.R;
import top.yztz.sched.config.Config;

public class TimeView extends LinearLayout {
    private TextView tvNo, tvStart, tvEnd;
    private View root;
    private int no;

    public TimeView(Context context) {
        this(context, null);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        root = inflate(getContext(), R.layout.layout_time, this);
        tvNo = root.findViewById(R.id.no);
        tvStart = root.findViewById(R.id.range_start);
        tvEnd = root.findViewById(R.id.range_end);
    }

    public void setNo(int no) {
        this.no = no;
        tvNo.setText(String.valueOf(no));
        tvStart.setText(Config.TIME_TABLE[no][0]);
        tvEnd.setText(Config.TIME_TABLE[no][1]);
        if (no % 2 == 1)
            root.setBackgroundResource(R.color.light_blue);
        else
            root.setBackgroundResource(R.color.white);
    }

    public int getNo() {
        return no;
    }
}

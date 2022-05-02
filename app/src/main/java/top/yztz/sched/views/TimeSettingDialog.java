package top.yztz.sched.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

import java.util.List;

import top.yztz.sched.R;
import top.yztz.sched.pojo.Date;
import top.yztz.sched.utils.StringUtils;


public class TimeSettingDialog extends Dialog implements View.OnClickListener {
    private EditText mCustom;
    private RadioGroup mBtnGroup;
    private RadioButton mBtnSingle, mBtnDouble, mBtnAll, mBtnCustom;
    private Button mBtnConfirm, mBtnCancel;

    private TimePicker startPicker, endPicker;
    private SingleSelectToggleGroup mDayOfWeekGroup;

    private onDismissListener listener;

    // 默认显示值
    private final Date defaultDate = new Date(0, Date.MONDAY, 1, 2);
    private final Date backup = new Date();
    private Date date;

    public TimeSettingDialog(@NonNull Context context, onDismissListener listener) {
        super(context);
        this.listener = listener;
        setCancelable(false);
    }

    public void show(Date date) {
        setDate(date);
        backup.copyFrom(date);
        super.show();
    }

    @Override
    public void show() {
        show(defaultDate);
    }

    private void setDate(Date date) {
        this.date = date;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (date.isSingleWeek()) { // 单周
            mBtnSingle.setChecked(true);
        } else if (date.isDoubleWeek()) { // 双周
            mBtnDouble.setChecked(true);
        } else if (date.isAllWeek()) {
            mBtnAll.setChecked(true);
        } else {
            List<Integer> weeks = date.getWeeks();
            if (weeks.size() == 0) return;
            StringBuilder sb = new StringBuilder();
            for (int week : weeks) sb.append(week).append(",");
            sb.setLength(sb.length() - 1); // 去除最后一个逗号
            mCustom.setText(sb.toString());
            mBtnCustom.setChecked(true);
        }
        mDayOfWeekGroup.check(date.getDay());
        startPicker.setTime(date.getStartTime());
        endPicker.setTime(date.getEndTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.layout_view_timesetting);
        //设置宽度
        WindowManager m = getWindow().getWindowManager();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Display d = m.getDefaultDisplay();
        Point size = new Point();
        d.getSize(size);
        p.width = (int) (size.x * 0.5);
        getWindow().setAttributes(p);

        mCustom = findViewById(R.id.custom_content);
        mBtnGroup = findViewById(R.id.btn_group);
        mBtnSingle = findViewById(R.id.btn_single);
        mBtnDouble = findViewById(R.id.btn_double);
        mBtnAll = findViewById(R.id.btn_all);
        mBtnCustom = findViewById(R.id.btn_custom);
        mDayOfWeekGroup = findViewById(R.id.day_of_week);
        startPicker = findViewById(R.id.start);
        endPicker = findViewById(R.id.end);
        mBtnCancel = findViewById(R.id.btn_cancel);
        mBtnConfirm = findViewById(R.id.btn_confirm);

        mBtnConfirm.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

        // 监听周次选择
        mBtnGroup.setOnCheckedChangeListener((group, checkedId) -> {
            System.out.println("CheckedId is " + checkedId);
            switch (checkedId) {
                case R.id.btn_single:
                    date.setWeeks(Date.SINGLE_WEEK);
                    break;
                case R.id.btn_double:
                    date.setWeeks(Date.DOUBLE_WEEK);
                    break;
                case R.id.btn_all:
                    date.setWeeks(Date.ALL_WEEK);
                    break;
                case R.id.btn_custom:
                    // todo:校验
                    String content = mCustom.getText().toString();
                    List<Integer> weeks;
                    if (null != (weeks = StringUtils.str2weeks(content))) date.setWeeks(weeks);
                    break;
            }
        });
        // 监听dayOfWeek
        mDayOfWeekGroup.setOnCheckedChangeListener((group, checkedId) -> {
            System.out.println("checkedId is " + checkedId);
            date.setDay(checkedId);
        });

        // 监听节次变化
        startPicker.setOnTimeChangeListener(newTime -> {
            date.setStartTime(newTime);
        });
        endPicker.setOnTimeChangeListener(newTime -> {
            date.setEndTime(newTime);
        });


        // 单击编辑框时自动选中
//        mCustom.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) mBtnCustom.setChecked(true);
//        });

    }

    @Override
    public void onClick(View v) {
        if (v == mBtnCancel) {
            date.copyFrom(backup);
            dismiss();
            if (listener != null) listener.onDismiss(false);
        } else if (v == mBtnConfirm) { // 确认
            //todo:对值做检查
            dismiss();
            if (listener != null) listener.onDismiss(true);
        }
    }

    public interface onDismissListener {
        void onDismiss(boolean changed);
    }
}

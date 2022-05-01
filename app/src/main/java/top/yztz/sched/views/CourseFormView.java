package top.yztz.sched.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.function.Consumer;

import top.yztz.sched.R;
import top.yztz.sched.fragments.WeekFrag;
import top.yztz.sched.interfaces.ConfirmCallback;
import top.yztz.sched.persistence.DataHelper;
import top.yztz.sched.pojo.Course;
import top.yztz.sched.utils.ToastUtil;

public class CourseFormView extends LinearLayout implements TimeSettingDialog.onDismissListener, View.OnClickListener {
    private static final String TAG = "CourseFormView";
    private EditText mName, mTeacher, mPlace;
    private Button mBtnTime, mBtnSave;
    private Course course;

    private MyTextWatcher nameWatcher, teacherWatcher, placeWatcher;

    // 用于时间相关设置
    private TimeSettingDialog timeDialog;
    // 用于确定是否保留更改
    private ConfirmDialog confirmDialog;

    private boolean changed = false;

    private void setChanged(boolean changed) {
        System.out.println("change is set to " + changed);
        if (changed && this.changed) return;
        if (changed) {
            mBtnSave.setBackgroundColor(getContext().getColor(R.color.blue));
            mBtnSave.setEnabled(true);
        } else {
            mBtnSave.setBackgroundColor(getContext().getColor(R.color.light_blue));
            mBtnSave.setEnabled(false);
        }
        this.changed = changed;
    }

    public CourseFormView(@NonNull Context context) {
        this(context, null);
    }

    public void setCourse(Course course) {
        this.course = course;
        // 这样的设计可以避免TextWatcher重复实例化
        nameWatcher.unbind();
        teacherWatcher.unbind();
        placeWatcher.unbind();

        mName.setText(course.getName());
        mTeacher.setText(course.getTeacher());
        mPlace.setText(course.getPlace());

        nameWatcher.bind(course::setName);
        teacherWatcher.bind(course::setTeacher);
        placeWatcher.bind(course::setPlace);

        setChanged(false);
    }

    public CourseFormView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_view_courseinfo, this);
        mName = findViewById(R.id.name);
        mTeacher = findViewById(R.id.teacher);
        mPlace = findViewById(R.id.place);
        mBtnTime = findViewById(R.id.time_btn);
        mBtnSave = findViewById(R.id.btn_save);

        mBtnTime.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);

        nameWatcher = new MyTextWatcher(mName);
        teacherWatcher = new MyTextWatcher(mTeacher);
        placeWatcher = new MyTextWatcher(mPlace);


        timeDialog = new TimeSettingDialog(context, this);
        confirmDialog = new ConfirmDialog(context);
    }

    private void commit() {
        if (null == course) {
            Log.e(TAG, "commit: course is null");
            return;
        }
        DataHelper.updateCourse(course);
    }

    public void save() {
        if (!changed) return;
        commit();
        setChanged(false);
        ToastUtil.show(getContext(), "保存成功");
    }

    public void quit(ConfirmCallback callback) {
        if (changed) {
            confirmDialog.show(confirmed -> {
                if (confirmed) {
                    setChanged(false);
                    this.course = null;
                }
                callback.callback(confirmed);
            });
        } else {
            callback.callback(true);
        }
    }

//    public boolean isChanged() {
//        return changed;
//    }

    // 时间设置窗口关闭回调
    @Override
    public void onDismiss(boolean changed) {
        if (changed) setChanged(true);
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnTime) {
            timeDialog.show((course != null) ? course.getDate() : null);
        }
        else if (v == mBtnSave) {
            save();
        }
//        else if (v == mBtnBack) {
//
//        }
    }

    class MyTextWatcher implements TextWatcher {
        Consumer<String> valBinder;
        MyTextWatcher(EditText target) {
            target.addTextChangedListener(this);
        }

        void bind(Consumer<String> valBinder) {
            this.valBinder = valBinder;
        }

        void unbind() {
            this.valBinder = null;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(null != valBinder) {
                valBinder.accept(s.toString());
                setChanged(true);
            }
        }
    }

}

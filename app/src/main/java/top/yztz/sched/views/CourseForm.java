package top.yztz.sched.views;

import android.content.Context;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import java.util.function.Consumer;

import top.yztz.sched.R;
import top.yztz.sched.config.Config;
import top.yztz.sched.config.ConfigHelper;
import top.yztz.sched.interfaces.ConfirmCallback;
import top.yztz.sched.persistence.DataHelper;
import top.yztz.sched.pojo.Course;
import top.yztz.sched.utils.ToastUtil;

public class CourseForm extends LinearLayout implements TimeSettingDialog.onDismissListener, View.OnClickListener {
    private static final String TAG = "CourseFormView";
    private EditText mName, mTeacher, mPlace;
    private Button mBtnTime, mBtnSave;

    private Course backup = new Course();
    private Course course;

    private MyTextWatcher nameWatcher, teacherWatcher, placeWatcher;

    // 用于时间相关设置
    private TimeSettingDialog timeDialog;
    // 用于确定是否保留更改
    private ConfirmDialog confirmDialog;

    private boolean changed = false;

    private StateListener stateListener;

    private void setChanged(boolean changed) {
//        System.out.println("change is set to " + changed);
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

    public CourseForm(@NonNull Context context) {
        this(context, null);
    }

    public void setCourse(Course course) {
        this.backup.copyFrom(course);

        this.course = course;
        show();
        setChanged(false);

        Course tmpCourse = ConfigHelper.getTemporaryCourse(course);
        if (null != tmpCourse) { // 存在备份
            // 移除原有备份
            ConfigHelper.removeTemporaryCourse(course);
            this.post(()->{
                Snackbar snackbar = Snackbar.make(findViewById(R.id.snackbar_container), "存在可用备份", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("恢复", v -> {
                    this.course = tmpCourse;
                    // 显示最新备份
                    show();
                    // 已更改
                    setChanged(true);
                });
                snackbar.show();
            });

        }
    }

    public void saveCurrentChanges() {
        if (null != course && changed) {
            ConfigHelper.putTemporaryCourse(course);
        }
    }

    private void show() {
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
    }

    public CourseForm(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setGravity(Gravity.CENTER);
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
        ConfigHelper.removeTemporaryCourse(course);
    }

    public void save() {
        if (!changed) return;
        commit();
        setChanged(false);
        ToastUtil.show(getContext(), "保存成功");
        if (null != stateListener) stateListener.onSave(course);
    }

    public void quit(ConfirmCallback callback) {
        if (changed) {
            // 询问是否确认保存
            confirmDialog.show(confirmed -> {
                if (confirmed) {
                    // 恢复更改前
                    this.course.copyFrom(backup);
                    setChanged(false);
                    // 删除可能存在的备份
                    ConfigHelper.removeTemporaryCourse(course);
                    this.course = null;
                }
                callback.callback(confirmed);
            });
        } else {
            callback.callback(true);
        }
    }

    // 时间设置窗口关闭回调
    @Override
    public void onDismiss(boolean changed) {
        if (changed) setChanged(true);
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnTime) {
            if (null != course)
                timeDialog.show(course.getDate());
            else
                timeDialog.show();
        }
        else if (v == mBtnSave) {
            save();
        }
//        else if (v == mBtnBack) {
//
//        }
    }

    public void setStateListener(StateListener listener) {
        this.stateListener = listener;
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

    public interface StateListener {
        void onSave(Course course);
        // 功能上可能有点重叠，所以没有用到
        void onBack(Course course);
    }

}

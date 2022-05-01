package top.yztz.sched.views;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import top.yztz.sched.R;
import top.yztz.sched.persistence.DataHelper;
import top.yztz.sched.pojo.Course;

public class CourseFormView extends LinearLayout implements TimeSettingDialog.onDismissListener{
    private static final String TAG = "CourseFormView";
    private EditText mName, mTeacher, mPlace;
    private Button mTime;
    private Course course;

    private TimeSettingDialog timeDialog;

    private boolean changed = false;

    public CourseFormView(@NonNull Context context) {
        this(context, (AttributeSet)null);
    }

    public void setCourse(Course course) {
        this.course = course;
        changed = false;
        mName.setText(course.getName());
        mTeacher.setText(course.getTeacher());
        mPlace.setText(course.getPlace());
    }

    public CourseFormView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_view_courseinfo, this);
        mName = findViewById(R.id.name);
        mTeacher = findViewById(R.id.teacher);
        mPlace = findViewById(R.id.place);
        mTime = findViewById(R.id.time_btn);

        timeDialog = new TimeSettingDialog(context, this);
        mTime.setOnClickListener(v-> timeDialog.show(course != null ? course.getDate() : null));

    }

    private void commit() {
        if (null == course) {
            Log.e(TAG, "commit: course is null");
            return;
        }
        DataHelper.updateCourse(course);
        changed = false;
    }

    public void save() {
        if (!changed) return;
        commit();
    }

    public boolean quit() {
        if (changed) {

        }
    }

    public boolean isChanged() {
        return changed;
    }

    // 时间设置窗口关闭回调
    @Override
    public void onDismiss(boolean changed) {
        this.changed |= changed;
    }
}

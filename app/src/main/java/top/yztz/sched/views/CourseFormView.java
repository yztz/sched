package top.yztz.sched.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import top.yztz.sched.R;
import top.yztz.sched.pojo.Course;

public class CourseFormView extends LinearLayout {
    private EditText mName, mTeacher, mPlace;
    private Button mTime;
    private Course course;

    private boolean changed = false;

    public CourseFormView(@NonNull Context context) {
        this(context, (AttributeSet)null);
    }

    public CourseFormView(@NonNull Context context, Course course) {
        this(context, (AttributeSet)null);
        setCourse(course);
    }

    public void setCourse(Course course) {
        this.course = course;
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
    }


    public boolean isChanged() {
        return changed;
    }

}

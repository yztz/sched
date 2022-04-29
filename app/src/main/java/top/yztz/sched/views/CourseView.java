package top.yztz.sched.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import top.yztz.sched.R;
import top.yztz.sched.pojo.Course;

public class CourseView extends LinearLayout {
    private static final String TAG = "CourseView";

    private LinearLayout cvRoot;
    private TextView tvName, tvTeacher, tvPlace;

    public CourseView(@NonNull Context context, Course course) {
        this(context, (AttributeSet) null);
        applyCourse(course);
    }


    private void setTextColor(int textColor) {
        tvName.setTextColor(textColor);
        tvPlace.setTextColor(textColor);
        tvTeacher.setTextColor(textColor);
    }

    public void setBgColor(int backgroundColor) {
        cvRoot.setBackgroundColor(backgroundColor);
    }


    private void applyCourse(Course course) {
        if (course == null) {
            Log.d(TAG, "course is null");
            return;
        }
        tvName.setText(course.getName());
        tvTeacher.setText(course.getTeacher());
        tvPlace.setText(course.getPlace());

    }

    public CourseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.layout_course, this);
        cvRoot = findViewById(R.id.course_container);
        tvName = findViewById(R.id.course_name);
        tvTeacher = findViewById(R.id.course_teacher);
        tvPlace = findViewById(R.id.course_place);

        setTextColor(0xffffffff);
        setBgColor(context.getColor(R.color.primary));
    }


}

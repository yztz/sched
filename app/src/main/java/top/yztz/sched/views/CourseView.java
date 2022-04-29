package top.yztz.sched.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import top.yztz.sched.R;
import top.yztz.sched.pojo.Course;

public class CourseView extends LinearLayout {
    private static final String TAG = "CourseView";
    private Context context;
    private LinearLayout llRoot;
    private CardView cardView;
    private TextView tvName, tvTeacher, tvPlace;

    public CourseView(@NonNull Context context, Course course) {
        this(context, (AttributeSet) null);
        applyCourse(course);
    }

    public void setTextSizeRatio(float ratio) {
        float s1 = tvName.getTextSize() * ratio;
        float s2 = tvTeacher.getTextSize() * ratio;
        float s3 = tvPlace.getTextSize() * ratio;
        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, s1);
        tvTeacher.setTextSize(TypedValue.COMPLEX_UNIT_PX, s2);
        tvPlace.setTextSize(TypedValue.COMPLEX_UNIT_PX, s3);

    }

   public void setBgColor(int color) {
        cardView.setCardBackgroundColor(color);
   }

    private void setTextColor(int textColor) {
        tvName.setTextColor(textColor);
        tvPlace.setTextColor(textColor);
        tvTeacher.setTextColor(textColor);
    }

    public void setRadius(float radius) {
        cardView.setRadius(radius);
    }

    public void setElevation(float elevation) {
        cardView.setCardElevation(elevation);
    }

    public void setPadding(int padding) {
        llRoot.setPadding(padding, padding, padding, padding);
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
        this.context = context;
        View.inflate(context, R.layout.layout_course, this);
        llRoot = findViewById(R.id.course_container);
        cardView = findViewById(R.id.cv_wrapper);
        tvName = findViewById(R.id.course_name);
        tvTeacher = findViewById(R.id.course_teacher);
        tvPlace = findViewById(R.id.course_place);

        cardView.setRadius(0);
        cardView.setCardElevation(0);

        setTextColor(0xffffffff);
        setBgColor(context.getColor(R.color.primary));

    }


}

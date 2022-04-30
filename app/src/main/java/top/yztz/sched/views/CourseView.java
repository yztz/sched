package top.yztz.sched.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Outline;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;
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
    private LinearLayout llRoot = this, llWrapper;
    private TextView tvName, tvTeacher, tvPlace;

    private Course course;

    protected int margin = 0;

    private ObjectAnimator shakeAnim;

    public CourseView(@NonNull Context context, Course course) {
        this(context, (AttributeSet) null);
        shakeAnim = ObjectAnimator.ofFloat(this, "rotation", 0, -6, 0, 6, 0).setDuration(200);
        shakeAnim.setRepeatCount(ValueAnimator.INFINITE);
        shakeAnim.setRepeatMode(ValueAnimator.REVERSE);
        setCourse(course);
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
        setBackgroundColor(color);
   }

    private void setTextColor(int textColor) {
        tvName.setTextColor(textColor);
        tvPlace.setTextColor(textColor);
        tvTeacher.setTextColor(textColor);
    }

    public void setRadius(float radius) {
//        cardView.setRadius(radius);
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
            }
        });
        setClipToOutline(true);
    }


    public void stopShaking() {
        shakeAnim.end();
    }


    public void startShaking() {
        shakeAnim.start();
    }


    public void setMargin(int value) {
        this.margin = value;
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
    }



    private void setCourse(Course course) {
        if (course == null) {
            Log.d(TAG, "course is null");
            return;
        }
        this.course = course;
        tvName.setText(course.getName());
        tvTeacher.setText(course.getTeacher());
        tvPlace.setText(course.getPlace());
    }

    public Course getCourse() {
        return course;
    }

    public CourseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View.inflate(context, R.layout.layout_item_course, this);

        llWrapper = findViewById(R.id.course_wrapper);
        tvName = findViewById(R.id.course_name);
        tvTeacher = findViewById(R.id.course_teacher);
        tvPlace = findViewById(R.id.course_place);

        setTextColor(0xffffffff);
        setBgColor(context.getColor(R.color.primary));

    }


}

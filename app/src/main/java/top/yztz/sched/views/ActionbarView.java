package top.yztz.sched.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import top.yztz.sched.R;
import top.yztz.sched.TextSizeProperty;
import top.yztz.sched.activities.LandscapeActivity;
import top.yztz.sched.utils.WeatherUtils;

public class ActionbarView extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "ActionbarView";
    private View actionbarView;
    private TextView mToday, mTomorrow;
    private ImageView mImgHelp, mWeather;
    private Context context;
    private boolean today = true;

    private DayListener dayListener;

    public ActionbarView(Context context, DayListener dayListener) {
        this(context, (AttributeSet)null);
        this.dayListener = dayListener;
    }

    public ActionbarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(lp);
        actionbarView = LayoutInflater.from(context).inflate(R.layout.layout_actionbar_main, this, true);
        mImgHelp = actionbarView.findViewById(R.id.img_full);
        mToday = actionbarView.findViewById(R.id.today);
        mTomorrow = actionbarView.findViewById(R.id.tomorrow);
        mWeather = actionbarView.findViewById(R.id.weather);

        mImgHelp.setOnClickListener(v -> {
            Intent intent = new Intent(context, LandscapeActivity.class);
            getContext().startActivity(intent);
        });

        mToday.setOnClickListener(this);
        mTomorrow.setOnClickListener(this);

        toggleWeather(true);
    }

    private void toggleWeather(boolean today) {
        WeatherUtils.updateWeather(weather -> {
            int resId = WeatherUtils.getWeatherResID(weather.getForecast().get(today ? 1 : 0).getType());
            Drawable target = context.getDrawable(resId);
            ObjectAnimator animator = ObjectAnimator.ofFloat(mWeather, "alpha", 1, 0).setDuration(150);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mWeather.setBackground(target);
                    ObjectAnimator.ofFloat(mWeather, "alpha", 0, 1)
                                .setDuration(150).start();
                }
            });
            ((Activity)context).runOnUiThread(animator::start);
        });
    }

    private void toggleTitle(boolean today) {
        float smallFontSize = getResources().getDimension(R.dimen.small_title);
        float bigFontSize = getResources().getDimension(R.dimen.big_title);
        TextSizeProperty property = new TextSizeProperty();
        if (today) {
            ObjectAnimator.ofFloat(mTomorrow, property, smallFontSize).setDuration(180).start();
            ObjectAnimator.ofFloat(mToday, property, bigFontSize).setDuration(100).start();
            ObjectAnimator.ofFloat(mTomorrow, "Alpha", 0.7f).setDuration(400).start();
            ObjectAnimator.ofFloat(mToday, "Alpha", 1f).setDuration(400).start();
        } else {
            ObjectAnimator.ofFloat(mTomorrow, property, bigFontSize).setDuration(100).start();
            ObjectAnimator.ofFloat(mToday, property, smallFontSize).setDuration(180).start();
            ObjectAnimator.ofFloat(mTomorrow, "Alpha", 1f).setDuration(400).start();
            ObjectAnimator.ofFloat(mToday, "Alpha", 0.7f).setDuration(400).start();
        }
    }

    public void goToday() {
        if (today) return;
        today = true;
        toggleWeather(true);
        toggleTitle(true);
        if (null != dayListener) dayListener.onDayChange(true);
    }

    public void goTomorrow() {
        if (!today) return;
        today = false;
        toggleWeather(false);
        toggleTitle(false);
        if (null != dayListener) dayListener.onDayChange(false);
    }

    @Override
    public void onClick(View view) {
        if (today && view == mTomorrow) {
            goTomorrow();
        } else if (!today && view == mToday) {
            goToday();
        }
    }

    public interface DayListener {
        void onDayChange(boolean newFlag);
    }

}

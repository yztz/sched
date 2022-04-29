package top.yztz.sched.activities;

import static top.yztz.sched.utils.WeatherUtils.CITY_CODE_HANGZHOU;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.transition.TransitionManager;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Scene;
import android.util.Log;
import android.util.Property;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.stetho.Stetho;


import top.yztz.sched.R;
import top.yztz.sched.TextSizeProperty;
import top.yztz.sched.config.Config;
import top.yztz.sched.fragments.DayFrag;
import top.yztz.sched.persistence.DataHelper;
import top.yztz.sched.utils.WeatherUtils;
import top.yztz.sched.views.ActionbarView;

public class PortraitActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{
    private static final String TAG = "PortraitActivity";
    private ActionbarView actionbarView;
    private final DayFrag dayFrag = new DayFrag();

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portrait);
        // 初始化数据
        DataHelper.DBInit(this);
        Stetho.initializeWithDefaults(this);

        //更换标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionbarView = new ActionbarView(this, dayFrag);
            actionBar.setCustomView(actionbarView);

            Toolbar parent = (Toolbar) actionbarView.getParent();
            parent.setContentInsetsAbsolute(0, 0);
            actionBar.show();
        }
        // 载入fragment
        initFragment();

        gestureDetector = new GestureDetector(this, this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.body, dayFrag, "day_table");
        transaction.commitAllowingStateLoss();
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    private static final float MIN_DISTANCE = 200;

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float deltaX = e2.getX() - e1.getX();
        if (deltaX > MIN_DISTANCE) { // right
            actionbarView.goToday();
        } else if (deltaX < -MIN_DISTANCE) { // left
            actionbarView.goTomorrow();
        }
        return true;
    }
}
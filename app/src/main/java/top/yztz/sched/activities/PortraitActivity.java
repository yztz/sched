package top.yztz.sched.activities;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;


import top.yztz.sched.R;
import top.yztz.sched.fragments.DayFrag;
import top.yztz.sched.persistence.DataHelper;
import top.yztz.sched.views.ActionbarView;

public class PortraitActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private static final String TAG = "PortraitActivity";
    private ActionbarView actionbarView;
    private DayFrag dayFrag;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portrait);
        // 初始化数据
        DataHelper.DBInit(this);

        // 载入fragment
        initFragment();

        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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


        gestureDetector = new GestureDetector(this, this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void initFragment() {
        dayFrag = new DayFrag();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.body, dayFrag, "day_table");
        transaction.commit();
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
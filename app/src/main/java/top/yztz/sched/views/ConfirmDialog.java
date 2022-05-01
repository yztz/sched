package top.yztz.sched.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import top.yztz.sched.R;
import top.yztz.sched.interfaces.ConfirmCallback;

public class ConfirmDialog extends Dialog implements View.OnClickListener {
    private Button mBtnConfirm, mBtnCancel;
    private boolean confirmed = false;
    private ConfirmCallback callback;

    public ConfirmDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void show() {
        show(null);
    }

    public void show(ConfirmCallback callback) {
        this.callback = callback;
        super.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.layout_view_confirmdialog);
        //设置宽度
        WindowManager m = getWindow().getWindowManager();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Display d = m.getDefaultDisplay();
        Point size = new Point();
        d.getSize(size);
        p.width = (int) (size.x * 0.4);
        getWindow().setAttributes(p);

        mBtnCancel = findViewById(R.id.btn_cancel);
        mBtnConfirm = findViewById(R.id.btn_confirm);

        mBtnConfirm.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    @Override
    public void onClick(View v) {
        if (null != callback) {
            callback.callback(v == mBtnConfirm);
        }
        dismiss();
    }
}

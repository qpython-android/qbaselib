package com.quseit.widget;
import android.content.Context;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.View;

public class QCheckBoxPreference extends  CheckBoxPreference implements View.OnLongClickListener {
    public QCheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}

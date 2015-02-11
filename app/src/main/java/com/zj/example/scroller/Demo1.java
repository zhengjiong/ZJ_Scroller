package com.zj.example.scroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * 使用Scroller和scrollTo移動ViewGroup中的內容
 *
 * scrollTo是移動控件裡面的內容,而不是移動控件本身,
 * 看Demo1View的背景和子view的背景和觀察到
 *
 * create by zhengjiong
 * Date: 2015-01-01
 * Time: 19:00
 */
public class Demo1 extends Activity{

    private Demo1View mMyViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo1);

        mMyViewGroup = (Demo1View) findViewById(R.id.viewgroup);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                mMyViewGroup.smoothMoveToBottom();
                break;
        }
    }
}

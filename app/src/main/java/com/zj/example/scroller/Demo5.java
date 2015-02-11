package com.zj.example.scroller;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 *
 * http://blog.csdn.net/qinjuning/article/details/7419207
 */
//带有可以切换屏的Activity
public class Demo5 extends Activity implements OnClickListener {

    private Button bt_scrollLeft;
    private Button bt_scrollRight;
    private Demo5View mulTiViewGroup  ;

    public static int screenWidth  ;  // 屏幕宽度
    public static int scrrenHeight ;  //屏幕高度

    private int curscreen = 0;   // 当前位于第几屏幕  ，共3个"屏幕"， 3个LinearLayout

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获得屏幕分辨率大小
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels ;
        scrrenHeight = metrics.heightPixels;

        System.out.println("screenWidth * scrrenHeight --->" + screenWidth + " * " +scrrenHeight);

        setContentView(R.layout.demo5);

        //获取自定义视图的空间引用
        mulTiViewGroup = (Demo5View)findViewById(R.id.mymultiViewGroup);

        bt_scrollLeft = (Button) findViewById(R.id.bt_scrollLeft);
        bt_scrollRight = (Button) findViewById(R.id.bt_scrollRight);

        bt_scrollLeft.setOnClickListener(this);
        bt_scrollRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.bt_scrollLeft:
                mulTiViewGroup.startMove() ; //下一屏
                break;
            case R.id.bt_scrollRight:
                mulTiViewGroup.stopMove() ; //停止滑动
                break;
        }
    }

}

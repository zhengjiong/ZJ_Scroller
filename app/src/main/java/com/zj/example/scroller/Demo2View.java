package com.zj.example.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * http://blog.csdn.net/bigconvience/article/details/26735705
 *
 * create by zhengjiong
 * Date: 2015-01-01
 * Time: 19:28
 */
public class Demo2View extends ViewGroup{
    private VelocityTracker mTracker;
    private TextView mChildView;

    public Demo2View(Context context) {
        this(context, null);
    }

    public Demo2View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Demo2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mChildView = (TextView) findViewById(R.id.velocity_value);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //MotionEventCompat.getActionMasked(ev);
        int action = event.getAction();

        /**
         * 用法：一般在onTouchEvent事件中被调用，
         * 先在down事件中获取一个VecolityTracker对象，然后在move或up事件中获取速度
         */
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mTracker == null) {

                    mTracker = VelocityTracker.obtain();
                } else {
                    mTracker.clear();
                    mTracker.addMovement(event);
                }
                Log.i("zj", "ACTION_DOWN");
                /**
                 * RawX是相對於手機屏幕左上角的位置,
                 * getX是相對於父控件左上角的位置
                 */
                mChildView.setText("Down X=" + event.getX() + " Y=" + event.getY() +
                        " ,RawX=" + event.getRawX() + " RawY=" + event.getRawY());

                break;
            case MotionEvent.ACTION_MOVE:
                mTracker.addMovement(event);
                //設置計算的單位,1000代表返回值為每秒的像素移動值px/s,1的話就是每毫秒的移動值px/ms
                mTracker.computeCurrentVelocity(1000);

                //从左向右划返回正数，从右向左划返回负数, 从上往下划返回正数，从下往上划返回负数
                Log.i("zj", "XVelocity=" + mTracker.getXVelocity() + " ,YVelocity=" + mTracker.getYVelocity());


                mChildView.setText("Move X=" + event.getX() + ",Y=" + event.getY() + "\n" +
                        "XVelocity=" + mTracker.getXVelocity() + " \nYVelocity=" + mTracker.getYVelocity());

                invalidate();//這個會导致computeScroll方法被执行
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mTracker.recycle();
                mTracker = null;
                Log.i("zj", "ACTION_UP ACTION_CANCEL");
                mChildView.setText("Up or Cancel");
                break;
        }

        return true;//改為true,才能出發後續的move和up事件
    }

    /**
     * ViewGroup中的onLayout是用來確定子View的位置的,并不是確定當前ViewGroup的位置,
     * 如果沒有子view可以不做處理
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View childView = getChildAt(0);

        /**
         * 這裡傳r代表右邊和父控件一樣大.
         *
         * 必須要重寫onMeasure方法,childView.getMeasuredHeight()才能獲取到值.
         */
        childView.layout(100, getPaddingTop(), r, childView.getMeasuredHeight());
    }

    /**
     * onMeasure后會重新調用onlayout方法
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View childView = getChildAt(0);


        /**
         * 1.TextView的layout_height如果設置的是wrap_content,
         * lp.width=-1,lp.height=-2,
         * measuredWidth第一次=0,measuredHeight第一次=0,經過childView.measure之後就會有值出現:measuredWidth=1080,measuredHeight=101
         *
         * 2.TextView的layout_height如果設置的是100dp,
         * lp.width=-1,lp.height=300
         * measuredWidth第一次=0,measuredHeight第一次=0,經過childView.measure之後就會有值出現:measuredWidth=1080,measuredHeight=300
         *
         */
        LayoutParams lp = childView.getLayoutParams();

        int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
        int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);


        childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);

        //經過measure之後,childview.getmeasureWidth和height就會有值出現,不然是0


        /**
         * 2.可以直接調用ViewGroup的measureChild方法,內部實現和上面類似
         */
        //measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        /**
         * 3.可以直接使用measureChildren,測量所有childview的大小
         */
        //measureChildren(widthMeasureSpec, heightMeasureSpec);

        //使用方法3最好!
    }
}

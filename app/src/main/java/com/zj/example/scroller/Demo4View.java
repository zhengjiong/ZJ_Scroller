package com.zj.example.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), x, y, duration);
 * 开始一个动画控制，由(startX , startY)在duration时间内前进(dx,dy)个单位，即到达坐标为(startX+dx , startY+dy)處
 * @author zj
 * @date 2015年1月3日14:42:44
 */
public class Demo4View extends ViewGroup {

    private Context mContext;
    private Scroller mScroller;

    public Demo4View(Context context) {
        this(context, null);
    }

    public Demo4View(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScroller = new Scroller(mContext);
    }

    public void smoothScrollTo(int x, int y, int duration) {

        //开始一个动画控制，由(startX , startY)在duration时间内前进(dx,dy)个单位，即到达坐标为(startX+dx , startY+dy)處
        Log.i("zj", "smoothScrollTo mScroller.getFinalY()=" + mScroller.getFinalY());
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), x, y, duration);

        postInvalidate();
    }

    @Override
    public void computeScroll() {

        // 如果返回true，表示动画还没有结束
        // 因为前面startScroll，所以只有在startScroll完成时 才会为false
        if (mScroller.computeScrollOffset()) {
            Log.i("zj", "mScroller.getCurrY()=" + mScroller.getCurrY());
            /**
             * mScroller.getCurrY()為負值是下滑效果
             * mScroller.getCurrX()為負值是右滑效果
             */
            scrollTo(0, mScroller.getCurrY());

            //刷新View,讓其反復調用computeScroll
            postInvalidate();
        }
        else
            Log.i("zj", "have done the scoller");
    }

    private float mLastionMotionY = 0 ;

    public boolean onTouchEvent(MotionEvent event){
        super.onTouchEvent(event);

        //手指位置地点
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //如果屏幕的动画还没结束，你就按下了，我们就结束该动画
                if(mScroller != null){
                    if(!mScroller.isFinished()){
                        mScroller.abortAnimation();
                    }
                }

                mLastionMotionY = y + mScroller.getFinalY();
                Log.i("zj", "down");
                break ;
            case MotionEvent.ACTION_MOVE:
                int detaY = (int)(mLastionMotionY - y);//滑動的距離
                /**
                 * 下拉的時候, y > mLastionMotionY,
                 * detaY為負值
                 */
                Log.i("zj", "MOVE detaY is " + detaY + " ,scrollY=" + getScrollY());
                smoothScrollTo(0, detaY, 50);
                //scrollBy(0, detaY);
                mLastionMotionY = y ;

                break ;
            case MotionEvent.ACTION_UP:
                Log.i("zj", "up");
                smoothScrollTo(-mScroller.getFinalX(), -mScroller.getFinalY(), 1000);//手指離開的時候還原
                //mScroller.startScroll(getScrollX(), getScrollY(), -getScrollX(), -getScrollY(), 1000);

                postInvalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true ;
    }

    // measure过程
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // 设置该ViewGroup的大小
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //child.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int startLeft = 0; // 每个子视图的起始布局坐标
        int startTop = 0; // 间距设置为10px 相当于 android：marginTop= "0px"
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            child.layout(
                    startLeft,
                    startTop,
                    startLeft + child.getMeasuredWidth(),
                    startTop + child.getMeasuredHeight());

            startTop = startTop + child.getMeasuredHeight() ; //校准每个子View的起始布局位置

        }
    }
}

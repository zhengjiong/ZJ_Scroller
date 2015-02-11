package com.zj.example.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * http://blog.csdn.net/bigconvience/article/details/26735705
 *
 * mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), x, y, duration);
 * 开始一个动画控制，由(startX , startY)在duration时间内前进(dx,dy)个单位，即到达坐标为(startX+dx , startY+dy)處
 *
 * create by zhengjiong
 * Date: 2015-01-01
 * Time: 19:28
 */
public class Demo1View extends ViewGroup{
    private Scroller mScroller;

    public Demo1View(Context context) {
        this(context, null);
    }

    public Demo1View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Demo1View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    /**
     * ViewGroup.computeScroll()被调用时机：
     * 当我们执行invalidate(）或postInvalidate()都会导致这个方法的执行。
     *
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        /**
         * 返回true代表Scroller還沒有滑動結束
         * Call this when you want to know the new location.
         * If it returns true, the animation is not yet finished.
         *
         * 返回值为boolean，true说明滚动尚未完成，false说明滚动已经完成。
         * 这是一个很重要的方法，通常放在View.computeScroll()中，用来判断是否滚动是否结束。
         */
        if (mScroller.computeScrollOffset()) {
            Log.i("zj", "computeScroll CurrY=" + mScroller.getCurrY());

            //返回当前滚动XY方向的偏移,距离原点X方向的绝对值,距离原点Y方向的绝对值
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            postInvalidate();//使其繼續調用onDraw然後調用computeScroll
        }
    }

    public void smoothMoveToBottom() {
        /**
         * 开始一个动画控制，由(startX , startY)在duration时间内前进(dx,dy)个单位，即到达坐标为(startX+dx , startY+dy)處
         *
         * getFinalX()是滚动結束最终停留的坐標位置,剛開始的時候為0, 第二次執行的時候將是-100,第三次是-200
         * 坐標原点是父控件左上角
         */
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), -100, -500, 1500);

        invalidate();//call computeScroll
    }

    /**
     * ViewGroup中的onLayout是用來確定子View的位置,并不是確定當前ViewGroup的位置,
     * 如果沒有子view可以不做處理
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        /**
         * 這樣寫可以不用重寫onMeasure方法,因為這裡已經確定的子view的大小,
         * 要重寫onMeasure方法可以看Demo4View
         */
        getChildAt(0).layout(0, 0, 500, 100);


    }
}

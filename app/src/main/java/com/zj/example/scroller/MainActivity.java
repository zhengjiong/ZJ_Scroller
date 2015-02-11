package com.zj.example.scroller;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 *
 * VelocityTracker:用于对触摸点的速度跟踪，方便获取触摸点的速度。
 * 用法：一般在onTouchEvent事件中被调用，先在down事件中获取一个VecolityTracker对象，然后在move或up事件中获取速度，调用流程可如下列所示：
 *
 * create by zhengjiong
 * Date: 2015-01-01
 * Time: 18:59
 */
public class MainActivity extends ListActivity{

    String[] mDatas = new String[]{
            "demo1 - Scroller",
            "demo2 - VelocityTracker",
            "demo3 - GestureDetector (下拉效果)",
            "demo4 - Scroller (下拉效果)",
            "demo5 - 橫向滑動效果(博客转)"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, mDatas);

        getListView().setAdapter(arrayAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                startActivity(new Intent(this, Demo1.class));
                break;
            case 1:
                startActivity(new Intent(this, Demo2.class));
                break;
            case 2:
                startActivity(new Intent(this, Demo3.class));
                break;
            case 3:
                startActivity(new Intent(this, Demo4.class));
                break;
            case 4:
                startActivity(new Intent(this, Demo5.class));
                break;
        }
    }
}

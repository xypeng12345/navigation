package com.xyp.meyki_bear.navigationtest;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * 项目名称：NavigationTest
 * 类描述：标题导航栏的最外围布局
 * 创建人：meyki-bear
 * 创建时间：2017/2/13 10:06
 * 修改人：meyki-bear
 * 修改时间：2017/2/13 10:06
 * 修改备注：
 */

public class ViewGroupNavgation extends HorizontalScrollView{
    private LineLinearLayout lineLinearLayout;
    public ViewGroupNavgation(Context context) {
        super(context);
        lineLinearLayout=new LineLinearLayout(context);
        initView(context);
    }

    private void initView(Context context) {
        addView(lineLinearLayout);
    }

    public ViewGroupNavgation(Context context, AttributeSet attrs) {
        super(context, attrs);
        lineLinearLayout=new LineLinearLayout(context,attrs);
        initView(context);
    }

    public ViewGroupNavgation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        lineLinearLayout=new LineLinearLayout(context,attrs,defStyleAttr);
        initView(context);
    }
    public void bindViewPager(ViewPager viewPager){
        lineLinearLayout.bindViewPager(viewPager);
    }
}

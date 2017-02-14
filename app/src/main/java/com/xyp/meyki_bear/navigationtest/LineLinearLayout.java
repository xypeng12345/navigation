package com.xyp.meyki_bear.navigationtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 项目名称：NavigationTest
 * 类描述：包裹真实标题与滑块的布局，在第二层
 * 创建人：meyki-bear
 * 创建时间：2017/2/13 10:08
 * 修改人：meyki-bear
 * 修改时间：2017/2/13 10:08
 * 修改备注：
 */

public class LineLinearLayout extends LinearLayout implements ViewPager.OnPageChangeListener {
    //使用到的自定义属性
    private static final int[] mAttr = {
            R.styleable.ViewGroupNavgation_line_color,
            R.styleable.ViewGroupNavgation_line_width,
            R.styleable.ViewGroupNavgation_line_height,

            R.styleable.ViewGroupNavgation_title_text_color,
            R.styleable.ViewGroupNavgation_title_text_size,
            R.styleable.ViewGroupNavgation_title_min_width,
            R.styleable.ViewGroupNavgation_title_height,
            R.styleable.ViewGroupNavgation_title_background};
    //与上面对应的属性下标
    private final int LINE_COLOR=0; //滑块颜色
    private final int LINE_WIDTH=1; //滑块宽度
    private final int LINE_HEIGHT=2; //滑块高度

    private final int TITLE_TEXT_COLOR=3;//标题颜色
    private final int TITLE_TEXT_SIZE=4; //标题字体大小
    private final int TITLE_MIN_WIDTH=5; //标题最小宽度，默认包裹内容
    private final int TITLE_HEIGHT=6;  //标题高度
    private final int TITLE_BACKGROUND=7; //标题的背景颜色


    private LinearLayout linearLayout; //放置标题的linearLayout
    private ArrayList<String> titles;//标题
    private ArrayList<View> titleViews;//标题控件

    //标题显示参数相关
    private int textColor = Color.BLACK; //标题颜色，默认黑色
    private int textSize = 20; //标题字体大小,是默认是50
    private int titleWidth = 400; //标题的最小宽度
    private int titleHeight=0; //标题栏的高度
    private Drawable titleBackground=new ColorDrawable(Color.YELLOW);//标题的背景颜色
    //滑块相关参数
    private View lineView; //滑块
    private int lineColor=Color.BLUE;
    private int lineHeight=2;//滑块的高度
    private int lineWidth=0; //滑块的宽度 ，如果不设置则会随着标题的宽度动态改变
    private boolean isChangeLineWidth; //是否让滑块的宽度随着标题的宽度改变而改变

    private ViewPager viewPager;

    public LineLinearLayout(Context context) {
        super(context);
        initView(context);
    }

    public LineLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public LineLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context){
        initView(context,null);
    }

    /**
     * 自定义控件，没有设置默认style
     * @param context
     * @param attrs
     */
    private void initView(Context context, AttributeSet attrs) {
        if(attrs!=null){
            TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.ViewGroupNavgation);
            lineColor=ta.getColor(mAttr[LINE_COLOR],lineColor); //如果获取XML属性失败就使用默认属性
            lineWidth=ta.getDimensionPixelSize(mAttr[LINE_WIDTH],lineWidth);
            lineHeight=ta.getDimensionPixelSize(mAttr[LINE_HEIGHT],lineHeight);

            textColor=ta.getColor(mAttr[TITLE_TEXT_COLOR],textColor);
            textSize=ta.getDimensionPixelSize(mAttr[TITLE_TEXT_SIZE],textSize);
            titleWidth=ta.getDimensionPixelSize(mAttr[TITLE_MIN_WIDTH],titleWidth);
            titleHeight=ta.getDimensionPixelSize(mAttr[TITLE_HEIGHT],titleHeight);
            titleBackground=ta.getDrawable(mAttr[TITLE_BACKGROUND]);
            if(lineWidth!=0){
                //如果控件的宽度被指定，则滑块的宽度不会随着控件的宽度改变
                isChangeLineWidth=false;
            }else{
                isChangeLineWidth=true;
            }

        }
        this.setOrientation(VERTICAL); //本身是竖直布局

        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(HORIZONTAL); //水平布局
        int height=titleHeight==0?ViewGroup.LayoutParams.WRAP_CONTENT:titleHeight; //如果设置高度了用设置高度，如果没设置用包裹内容
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
        addView(linearLayout, layoutParams);

        //创建与初始化滑块
        lineView = new View(context);
        lineView.setBackgroundColor(lineColor);
        LayoutParams layoutParam = new LayoutParams(lineWidth, lineHeight);
        addView(lineView, layoutParam);
    }

     public void bindViewPager(ViewPager viewPager) {
        if (this.viewPager != null) {
            this.viewPager.removeOnPageChangeListener(this);
        }
        this.viewPager = viewPager;
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null) {
            setTitles(adapter);
        }
        this.viewPager.addOnPageChangeListener(this);
    }

    /**
     * 根据viewPager设置标题内容 adapter是viewPager.getAdapter获取
     *
     * @param adapter
     */
    private void setTitles(PagerAdapter adapter) {
        //创建标题文字
        if (titles == null) {
            titles = new ArrayList<>();
        } else {
            titles.clear();
        }
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            titles.add(adapter.getPageTitle(i).toString());
        }
        //重构标题控件
        if (titleViews == null) {
            titleViews = new ArrayList<>();
        }
        //原有控件重用
        for (int i = 0; i < titleViews.size(); i++) {
            TextView view = (TextView) titleViews.get(i);
            view.setText(titles.get(i));
        }
        //超出控件新增，超出分两种情况，第一种控件多了，第二种标题多了
        int countSize = titles.size() - titleViews.size();
        if (countSize > 0) {//标题多了，新增
            LayoutParams layoutparams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            for (int i = 0; i < titles.size(); i++) {

                TextView tv = new TextView(getContext());//设置TextView的配置参数
                tv.setLayoutParams(layoutparams); //宽包裹内容，高填充父类
                tv.setMinWidth(titleWidth);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(textSize);
                tv.setTextColor(textColor);
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.JELLY_BEAN){//版本16以上使用
                    tv.setBackground(titleBackground);
                }else{ //版本16以下使用
                    tv.setBackgroundDrawable(titleBackground);
                }
                tv.setText(titles.get(i));

                linearLayout.addView(tv);
                titleViews.add(tv);
            }
        } else if (countSize < 0) {
            for (int i = titleViews.size() - 1; i >= count; i--) {//倒序删除多余的控件
                View view = titleViews.get(i);
                linearLayout.removeView(view);
                titleViews.remove(i);
            }
        }

        //初始化滑块，位置与参数
        if (titleViews.size() > 0) {
            linearLayout.post(new Runnable() {
                @Override
                public void run() {

                    int left = titleViews.get(0).getLeft();
                    ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) lineView.getLayoutParams();
                    if(isChangeLineWidth){ //如果随着标题的改变而改变
                        layoutParams.width = titleViews.get(0).getWidth();
                        layoutParams.leftMargin=left;
                    }else{ //如果设置了默认宽度
                        //该宽度不能超过标题中最短的标题的宽度
                        for (int i=0;i<titleViews.size();i++){
                            if(lineWidth>titleViews.get(i).getWidth()){
                                lineWidth=titleViews.get(i).getWidth();
                            }
                        }
                        layoutParams.width=lineWidth;
                        left+=(titleViews.get(0).getWidth()-lineWidth)/2;
                        layoutParams.leftMargin=left;
                    }

                    lineView.setLayoutParams(layoutParams);
                }
            });
        }
    }

    private int currentIndex;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //判断左右滑动
        if (position != currentIndex) {//解决每次滑动完成后会触发一次目标Position的offset=0的情况。
            currentIndex = position;
            return;
        }
        if(isChangeLineWidth){
            scrollLineView(position, positionOffset);
        }else{
            scrollLineViewNoChangeWidth(position, positionOffset);
        }
    }

    private void scrollLineView(final int position, float offset) {
        if (position > titleViews.size()-2) {//size-1是最后一个，如果是最后一个即position=size-1，则不需要滑动
            return;
        }
        //由于ViewPager本身的滑动机制，offset最大值小于1，且具体值不定，导致最后滑动的尺寸不足titleView的尺寸要求，所以做了如下数据处理
        //该处理经测试20次都没有发现出现布局未对齐的情况
        if (offset > 0.9) {
            offset+=0.01;
        }else if(offset>0.97){
            offset+=0.01;
        }
        if(offset>1){
            offset=1;
        }
        //上面是数据处理
        ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) lineView.getLayoutParams();
        layoutParams.leftMargin = (int) (titleViews.get(position).getLeft()+((titleViews.get(position+1).getLeft()-titleViews.get(position).getLeft())) * offset);
        layoutParams.width =(int) (titleViews.get(position).getWidth()+((titleViews.get(position+1).getWidth()-titleViews.get(position).getWidth())) * offset);
        lineView.setLayoutParams(layoutParams);


        HorizontalScrollView scrollView = (HorizontalScrollView) getParent();
        Rect scrollBounds = new Rect();
        scrollView.getHitRect(scrollBounds);
        scrollView.smoothScrollTo(layoutParams.leftMargin, 0);
    }

    private void scrollLineViewNoChangeWidth(final int position,float offset) {
        if (position > titleViews.size()-2) {//size-1是最后一个，如果是最后一个即position=size-1，则不需要滑动
            return;
        }
        //由于ViewPager本身的滑动机制，offset最大值小于1，且具体值不定，导致最后滑动的尺寸不足titleView的尺寸要求，所以做了如下数据处理
        //该处理经测试20次都没有发现出现布局未对齐的情况
        if (offset > 0.9) {
            offset+=0.01;
        }else if(offset>0.97){
            offset+=0.01;
        }
        if(offset>1){
            offset=1;
        }
        //上面是数据处理

        //由于控件不会随着标题长度的改变而改变，所以滑动的对齐位置就从left变为了center，所以需要left+width/2，滑块的位置则是left-width/2;
        ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) lineView.getLayoutParams();
        //当前滑块的位置(由于滑块与标题对齐，且滑块滑动过程中位置在不断变化，所以使用标题的位置)
        int viewLeft = titleViews.get(position).getLeft()+titleViews.get(position).getWidth()/2;
        //滑块的目标位置
        int nextLeft = titleViews.get(position + 1).getLeft()+titleViews.get(position+1).getWidth()/2;

        float i =(int) (viewLeft +((nextLeft - viewLeft)) * offset);//目前要滑动的距离
        layoutParams.leftMargin = (int) i-lineWidth/2;
        layoutParams.width =lineWidth;
        lineView.setLayoutParams(layoutParams);


        HorizontalScrollView scrollView = (HorizontalScrollView) getParent();
        Rect scrollBounds = new Rect();
        scrollView.getHitRect(scrollBounds);
        //由于是居中对齐，scrollView滑动可能导致部分标题显示不完全，所以此处使用标题left作为scrollView的滑动处理
        int z= (int) (titleViews.get(position).getLeft()+((titleViews.get(position+1).getLeft()-titleViews.get(position).getLeft())) * offset);
        scrollView.smoothScrollTo(z, 0);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}

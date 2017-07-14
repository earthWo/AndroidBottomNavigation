package com.whitelife.library;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.util.Pools;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzefeng on 16/8/24.
 */

public class BottomNavigationMenuView extends ViewGroup implements View.OnClickListener{

    /**
     * 默认动画事件
     */
    private final int DEFAULT_ANIMATION_TIME=150;

    private int animationTime=DEFAULT_ANIMATION_TIME;

    private List<BottomNavigationItem> bottomNavigationItemList=new ArrayList<>();

    private boolean mShiftingMode = false;

    private int normalWidth;

    private int largeWidth;

    private int[]childWidths;

    private BottomNavigationPresent present;

    private int selectPosition;

    private Boolean finalShiftingMode;

    private Pools.Pool<BottomNavigationItem>pool=new Pools.SynchronizedPool<>(10);

    private TransitionSet transitionSet;

    public BottomNavigationMenuView(Context context) {
        this(context,null);
    }

    public BottomNavigationMenuView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomNavigationMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAnimation();
    }


    private void initAnimation(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transitionSet = new TransitionSet();
            transitionSet.setOrdering(1);
            transitionSet.addTransition(new ChangeBounds());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                transitionSet.addTransition(new ChangeTransform());
            }
            transitionSet.addTransition(new Fade(Fade.IN));
            transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
            transitionSet.setDuration(animationTime);
            transitionSet.setInterpolator(new FastOutSlowInInterpolator());
        }
    }

    public void setFinalShiftingMode(Boolean finalShiftingMode) {
        this.finalShiftingMode = finalShiftingMode;
        mShiftingMode=finalShiftingMode;
        buildMenuView();
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int width=r-l;
        int height=b-t;
        int count=getChildCount();
        int used=0;
        for(int i=0;i<count;i++){
            View child=getChildAt(i);
            //左向右或右向左
            if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                child.layout(width - used - child.getMeasuredWidth(), 0, width - used, height);
            } else {
                child.layout(used, 0, child.getMeasuredWidth() + used, height);
            }
            used += child.getMeasuredWidth();
        }

    }

    public void setPresent(BottomNavigationPresent present) {
        this.present = present;
    }


    public boolean isShiftingMode() {
        return mShiftingMode;
    }

    public void setShiftingMode(boolean mShiftingMode) {
        this.mShiftingMode = mShiftingMode;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置整个高
        int width=MeasureSpec.makeMeasureSpec(widthMeasureSpec,View.MeasureSpec.UNSPECIFIED);
        int height=MeasureSpec.makeMeasureSpec(Util.dp2px(getContext(),56),View.MeasureSpec.UNSPECIFIED);
        setMeasuredDimension(width,height);
        int count=getChildCount();
        childWidths=new int[count];
        //摆动状态
        if(mShiftingMode){
            measureChildWidth(width);
            for(int i=0;i<count;i++){
                if(i==selectPosition){
                    childWidths[i]=largeWidth;
                }else{
                    childWidths[i]=normalWidth;
                }
                getChildAt(i).measure(MeasureSpec.makeMeasureSpec(childWidths[i],MeasureSpec.EXACTLY),height);
            }
        }else{//不摆动状态

            largeWidth=0;
            if(count>0){
                normalWidth=width/count;
            }else{
                normalWidth=0;
            }

            for(int i=0;i<count;i++){
                childWidths[i]=normalWidth;
                getChildAt(i).measure(MeasureSpec.makeMeasureSpec(normalWidth,MeasureSpec.EXACTLY),height);
            }
        }
    }

    private void measureChildWidth(int width) {
        int count=getChildCount();
        if(count==1){
            largeWidth=width;
            normalWidth=width;
        }else if(count==2){
            normalWidth=Util.dp2px(getContext(),96);
            largeWidth=width-normalWidth;
        }if(count<=4){
            largeWidth=Util.dp2px(getContext(),168);
            normalWidth=(width-largeWidth)/(count-1);
        }else if(count==5){
            normalWidth=Util.dp2px(getContext(),60);
            largeWidth=width-normalWidth*4;
        }else{
            largeWidth=Util.dp2px(getContext(),96);
            normalWidth=(width-largeWidth)/(count-1);
        }
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        for(BottomNavigationItem item:bottomNavigationItemList){
            item.setDefaultColor(backgroundColor);
        }
    }

    @Override
    public void onClick(View v) {
        if(!present.isAnimalRunning()){
            updateItem(v);
            if(clickCallback!=null){
                clickCallback.click(v,selectPosition,v.getId());
            }
        }
    }

    public int getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(int animationTime) {
        if(animationTime>=0) {
            this.animationTime = animationTime;
            initAnimation();
        }
    }

    private void updateItem(View v){
        if (transitionSet != null&&Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(this,transitionSet);
        }

        int count=getChildCount();
        for(int i=0;i<count;i++){
            View view=getChildAt(i);
            if(v==view){
                ((BottomNavigationItem)view).setSelect(true);
                selectPosition=i;
            }else{
                ((BottomNavigationItem)view).setSelect(false);
            }
        }
    }

    public void buildMenuView() {
        removeAllViews();
        BottomMenu bottomMenu=present.getBottomMenu();
        if(bottomMenu!=null&&bottomMenu.getItems().size()>0){
            if(bottomNavigationItemList.size()>0){
                //将已有的存起来
                for(BottomNavigationItem bottomNavigationItem:bottomNavigationItemList){
                    pool.release(bottomNavigationItem);
                }
            }

            bottomNavigationItemList.clear();
            List<BottomMenuItem>items=bottomMenu.getItems();

            if(items.size()>=selectPosition){
                selectPosition=0;
            }

            if(finalShiftingMode==null){
                mShiftingMode=items.size()>3;
            }

            for(int i=0;i<items.size();i++){
                BottomNavigationItem bottom=getBottomNavigationItem();
                bottom.setMenuItem(items.get(i));
                bottom.setShiftingMode(mShiftingMode);
                bottom.setSelect(i==0);
                bottom.setOnClickListener(this);
                bottomNavigationItemList.add(bottom);
                addView(bottom);
            }
        }
        setViewBackgroundColor();
    }

    public void setViewBackgroundColor(){
        if(!mShiftingMode){
            this.setBackgroundColor(Color.WHITE);
        }
    }

    private ClickCallback clickCallback;

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    private BottomNavigationItem getBottomNavigationItem(){
        BottomNavigationItem item=pool.acquire();
        if(item==null){
            item=new BottomNavigationItem(getContext());
        }
        return item;
    }

    interface ClickCallback{
        void click(View view,int selectPosition,int id);
    }
}

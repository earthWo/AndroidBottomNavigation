package com.whitelife.library;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wuzefeng on 2017/7/24.
 */

public class BottonNavigationBehavior extends CoordinatorLayout.Behavior<View> {


    private TransitionSet transitionSet;

    private final static int TRANSLISTION_DY=10;

    public BottonNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transitionSet=new TransitionSet();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                transitionSet.addTransition(new Slide());
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    transitionSet.addTransition(new Fade());
                }
            }
            transitionSet.setDuration(200);
        }
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {

        if(dy>TRANSLISTION_DY){
            viewInVisible(child,coordinatorLayout);
        }else if(dy<-TRANSLISTION_DY){
            viewVisible(child,coordinatorLayout);
        }

    }


    private void viewVisible(View targetView,CoordinatorLayout coordinatorLayout){
        if(targetView.getVisibility()!=View.VISIBLE){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(coordinatorLayout,transitionSet);
            }
            targetView.setVisibility(View.VISIBLE);
        }
    }

    private void viewInVisible(View targetView,CoordinatorLayout coordinatorLayout){
        if(targetView.getVisibility()!=View.GONE){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(coordinatorLayout,transitionSet);
            }
            targetView.setVisibility(View.GONE);
        }
    }



}

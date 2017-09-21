package com.whitelife.library;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by wuzefeng on 16/8/24.
 */

public class BottomNavigationItem extends FrameLayout{

    private Drawable icon;

    private String text;

    private boolean select;

    private boolean mShiftingMode = true;

    private TextView titleView;

    private ImageView iconView;

    private final float mScaleUpFactor=14.0f/12;

    private int selectMargin;

    private ColorStateList selectColor;

    private ColorStateList defaultColor;

    private ColorStateList whiteColor;

    private ColorStateList shiftNormalColor;

    public BottomNavigationItem(Context context) {
        this(context,null);
    }

    public BottomNavigationItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomNavigationItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_bottom_navigation_item,this,true);
        titleView= (TextView) findViewById(R.id.tv_item);
        iconView= (ImageView) findViewById(R.id.iv_item);
        selectMargin=Util.dp2px(context,2);
        selectColor =  ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.default_color));
        defaultColor =  ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.color_gray));
        whiteColor =  ColorStateList.valueOf(Color.WHITE);
        shiftNormalColor =  ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.shift_color_gray));
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
        this.iconView.setImageDrawable(icon);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        titleView.setText(text);
    }

    public void setMenuItem(BottomMenuItem menuItem) {
        setText(menuItem.getTitle());
        setIcon(menuItem.getIcon());
        setId(menuItem.getItemId());
    }

    public void setDefaultColor(int color){
        if(color!=0){
            selectColor =  ColorStateList.valueOf(color);
            this.setIconViewColor();
        }
    }

    public void setShiftingMode(boolean mShiftingMode) {
        this.mShiftingMode = mShiftingMode;
    }

    public void setSelect(boolean select) {
        this.select = select;

        if(mShiftingMode){
            setIconViewColor();
            if(select){
                titleView.setVisibility(VISIBLE);
                titleView.setScaleX(mScaleUpFactor);
                titleView.setScaleY(mScaleUpFactor);
                FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) iconView.getLayoutParams();
                layoutParams.topMargin=Util.dp2px(getContext(),6);
                layoutParams.gravity= Gravity.CENTER_HORIZONTAL|Gravity.TOP;
                iconView.setLayoutParams(layoutParams);
            }else{
                titleView.setVisibility(INVISIBLE);
                titleView.setScaleX(1);
                titleView.setScaleY(1);
                FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) iconView.getLayoutParams();
                layoutParams.topMargin=0;
                layoutParams.gravity= Gravity.CENTER;
                iconView.setLayoutParams(layoutParams);
            }
        }else{
            setIconViewColor();
            if(select){
                FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) iconView.getLayoutParams();
                layoutParams.topMargin=Util.dp2px(getContext(),6);
                layoutParams.gravity= Gravity.CENTER_HORIZONTAL|Gravity.TOP;
                iconView.setLayoutParams(layoutParams);
                titleView.setScaleX(mScaleUpFactor);
                titleView.setScaleY(mScaleUpFactor);
            }else{
                FrameLayout.LayoutParams  layoutParams= (FrameLayout.LayoutParams) iconView.getLayoutParams();
                layoutParams.topMargin=Util.dp2px(getContext(),8);
                layoutParams.gravity= Gravity.CENTER_HORIZONTAL|Gravity.TOP;
                iconView.setLayoutParams(layoutParams);
                titleView.setScaleX(1);
                titleView.setScaleY(1);
            }

        }
        refreshDrawableState();
    }

    private void setIconViewColor(){
        if(mShiftingMode){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(select){
                    iconView.setImageTintList(whiteColor);
                    titleView.setTextColor(whiteColor);
                }else{
                    iconView.setImageTintList(shiftNormalColor);
                    titleView.setTextColor(shiftNormalColor);
                }
            }else{
                if(select){
                    titleView.setTextColor(whiteColor);
                }else{
                    titleView.setTextColor(shiftNormalColor);
                }
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(select){
                    iconView.setImageTintList(selectColor);
                    titleView.setTextColor(selectColor);
                }else{
                    iconView.setImageTintList(defaultColor);
                    titleView.setTextColor(defaultColor);
                }
            }else{
                if(select){
                    titleView.setTextColor(selectColor);
                }else{
                    titleView.setTextColor(defaultColor);
                }
            }
        }
    }
}

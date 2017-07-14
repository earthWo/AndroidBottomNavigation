package com.whitelife.library;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/**
 * Created by wuzefeng on 2017/7/12.
 */

public class BottomMenuItem {

    private int itemId;

    private String title;

    private Drawable icon;


    public BottomMenuItem(int itemId, String title, Drawable icon) {
        this.itemId = itemId;
        this.title = title;
        this.icon = icon;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}

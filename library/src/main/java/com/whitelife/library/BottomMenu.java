package com.whitelife.library;

import android.graphics.drawable.Drawable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzefeng on 2017/7/12.
 */

public class BottomMenu {

    private BottomNavigationPresent present;

    private List<BottomMenuItem> items=new ArrayList<>();

    public void addItem(int id,String title,Drawable icon){
        items.add(new BottomMenuItem(id,title,icon));
    }

    public List<BottomMenuItem> getItems() {
        return items;
    }

    public void setPresent(BottomNavigationPresent present) {
        this.present = present;
    }

    public void clear(){
        items.clear();
    }
}

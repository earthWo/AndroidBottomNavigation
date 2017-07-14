package com.whitelife.library;

/**
 * Created by wuzefeng on 2017/7/13.
 */

public class BottomNavigationPresent {

    private BottomMenu bottomMenu;

    private BottomNavigationMenuView bottomNavigationMenuView;

    private boolean animalRunning;

    public boolean isAnimalRunning() {
        return animalRunning;
    }

    public void setAnimalRunning(boolean animalRunning) {
        this.animalRunning = animalRunning;
    }

    public BottomMenu getBottomMenu() {
        return bottomMenu;
    }

    public void setBottomMenu(BottomMenu bottomMenu) {
        this.bottomMenu = bottomMenu;
    }

    public BottomNavigationMenuView getBottomNavigationMenuView() {
        return bottomNavigationMenuView;
    }

    public void setBottomNavigationMenuView(BottomNavigationMenuView bottomNavigationMenuView) {
        this.bottomNavigationMenuView = bottomNavigationMenuView;
    }

    /**
     * 更新菜单
     */
    public void updateMenuView(){
        bottomNavigationMenuView.buildMenuView();
    }
}

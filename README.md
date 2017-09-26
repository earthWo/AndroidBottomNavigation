# AndroidBottomNavigation

## 截图
![](http://7xjrms.com1.z0.glb.clouddn.com/SM-G9500_20170714102952.gif)
![](http://7xjrms.com1.z0.glb.clouddn.com/SM-G9500_20170714103217.gif)
## 使用方法

gradle:
```compile 'com.whitelife.library:library:1.0'```
maven:
```
<dependency>
  <groupId>com.whitelife.library</groupId>
  <artifactId>library</artifactId>
  <version>1.0</version>
  <type>pom</type>
</dependency>
```

```
<com.whitelife.library.BottomNavigationView
       android:id="@+id/bottom_navigation"
       android:layout_width="match_parent"
       android:layout_height="56dp"
       android:layout_gravity="bottom"
       app:background_color="@color/colorAccent"//设置背景颜色
       app:animation_time="100"//设置动画时间
       app:shifting_mode="true" //设置动画类型
       app:menu="@menu/bottom_items">
   </com.whitelife.library.BottomNavigationView>
```
同时指定menu,在menu文件夹下创建menu文件
```
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/b1" android:title="首页" android:icon="@drawable/ic_home_black_24dp" ></item>
    <item android:id="@+id/b2" android:title="发现" android:icon="@drawable/ic_dashboard_black_24dp" ></item>
    <item android:id="@+id/b3" android:title="设置" android:icon="@drawable/ic_notifications_black_24dp" ></item>
</menu>

设置点击颜色变化

int color[]={Color.RED,Color.BLUE,Color.GREEN};
       try {
           bottomNavigationView.setColors(color);
       } catch (Exception e) {
           e.printStackTrace();
       }
```

## 与官方控件的区别
1.可以指定动画时间
2.可以设置点击水波纹的颜色
3.可以强制指定动画类型
4.可以突破菜单个数限制
5.添加滑动动画

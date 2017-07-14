package com.whitelife.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by wuzefeng on 2017/7/12.
 */

public class BottomMenuInflater {

    /** Menu tag name in XML. */
    private static final String XML_MENU = "menu";

    /** Item tag name in XML. */
    private static final String XML_ITEM = "item";

    private Context context;

    public BottomMenuInflater(Context context) {
        this.context = context;
    }

    public  void inflater(BottomMenu bottomMenu, int resId){
        if(bottomMenu==null||resId==0||context==null){
            return;
        }else{
            XmlResourceParser parser = null;
            try {
                parser = context.getResources().getLayout(resId);
                AttributeSet attrs = Xml.asAttributeSet(parser);
                parseMenu(parser, attrs, bottomMenu);
            } catch (XmlPullParserException e) {
                throw new InflateException("menu xml文件错误", e);
            } catch (IOException e) {
                throw new InflateException("menu xml文件错误", e);
            } finally {
                if (parser != null) parser.close();
            }
        }

    }

    private  void parseMenu(XmlPullParser parser, AttributeSet attrs,BottomMenu bottomMenu)
            throws XmlPullParserException, IOException{
        int eventType = parser.getEventType();
        String tagName="";
        //遍历xml
        do{
            if (eventType == XmlPullParser.START_TAG) {
                tagName = parser.getName();
                if (tagName.equals(XML_MENU)) {
                    // Go to next tag
                    eventType = parser.next();
                    break;
                }
                throw new RuntimeException("menu解析错误");
            }
            eventType=parser.next();
        }while(eventType!=XmlPullParser.END_DOCUMENT);

        boolean reachedEndOfMenu = false;
        while(!reachedEndOfMenu){
            tagName = parser.getName();
            switch (eventType){
                case XmlPullParser.END_DOCUMENT:
                    throw new RuntimeException("xml错误");
                case XmlPullParser.END_TAG:
                    if (tagName.equals(XML_MENU)) {
                        reachedEndOfMenu = true;
                    }
                    break;
                case XmlPullParser.START_TAG:
                    if (tagName.equals(XML_ITEM)) {
                        addItem(attrs,bottomMenu);
                    }
                    break;
            }
            eventType = parser.next();
        }
    }


    private  void addItem(AttributeSet attrs,BottomMenu bottomMenu){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MenuItem);
        int itemId = a.getResourceId(R.styleable.MenuItem_android_id, 0);
        CharSequence itemTitle = a.getText(R.styleable.MenuItem_android_title);
        int itemIconResId = a.getResourceId(android.support.v7.appcompat.R.styleable.MenuItem_android_icon, 0);


        bottomMenu.addItem(itemId,itemTitle.toString(),context.getResources().getDrawable(itemIconResId));
    }

}

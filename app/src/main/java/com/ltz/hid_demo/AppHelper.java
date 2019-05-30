package com.ltz.hid_demo;

import android.app.Application;

/**
 * Created by Administrator on 2019/5/25.
 */
public class AppHelper extends Application{

    public static AppHelper mAppHelper;


    @Override
    public void onCreate() {
        super.onCreate();
        mAppHelper = this;
    }

    public static AppHelper getAppHelper(){
        return mAppHelper;
    }
}

package com.example.jinglinzichan;

import android.app.Application;

/**
 * Created by chenchangjun on 18/5/3.
 */

public class App extends Application {


    private static Application app;


    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
    }

    public static Application getContext() {
        return app;
    }
}

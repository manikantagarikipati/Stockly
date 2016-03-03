package com.winneredge.stockly;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

/**
 * Created by Manikanta on 2/28/2016.
 */
public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /*Fabric.with(this, new Crashlytics());*/
        Configuration dbConfiguration = new Configuration.Builder(this).setDatabaseVersion(1).create();
        ActiveAndroid.initialize(dbConfiguration);

    }

}

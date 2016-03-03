package com.winneredge.stockly.wcommons.activities;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Manikanta on 2/26/2016.
 */
public abstract class WActivity extends AppCompatActivity {

    public abstract void initializeViewBean();

    public abstract void initPresenter();

    public abstract void initialiseComponents();

}

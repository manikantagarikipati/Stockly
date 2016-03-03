package com.winneredge.stockly.wcommons.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Manikanta on 3/1/2016.
 */
public class AppUtils {

    private static final String LAUNCH_DETAILS="Launch_Info";
    private static final String USER_INFO = "User_Info";
    //can keep refresh token, resetXTrailList token , username as individual fields it keeps on growing
    //instead we convert the server response into json string and store as it is.
    public static void updateLaunchInfo(Context context){

        SharedPreferences.Editor editor = context.getSharedPreferences(LAUNCH_DETAILS, context.MODE_PRIVATE).edit();
        editor.putBoolean(USER_INFO,true);
        editor.apply();
    }

    //get UserInfo from shared preferences as string and convert it into result object
    public static boolean isFirstTimeLaunch(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(LAUNCH_DETAILS, context.MODE_PRIVATE);
        return prefs.getBoolean(USER_INFO, false);
    }

}

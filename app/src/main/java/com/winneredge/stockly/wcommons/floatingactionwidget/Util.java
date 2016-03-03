/*
 * Copyright (c) 2016, Prokavi Systems Private Limited and/or its affiliates.
 * 9th Floor, Gamma Tower, Sigma Soft Tech Park, Whitefield, Bengaluru - 560066.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information 
 * of Prokavi Systems Private Limited ("Confidential Information").
 * You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with Prokavi Systems Private Limited.
 */
package com.winneredge.stockly.wcommons.floatingactionwidget;

import android.content.Context;
import android.os.Build;

final class Util {

    private Util() {
    }

    static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }

    static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}

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
package com.winneredge.stockly.wcommons.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.winneredge.stockly.wcommons.constants.GlobalConstants;
/**
 * Created by Manikanta on 15-07-2015.
 */
public class PrimaryTextView extends TextView {

    /*
     * Caches typefaces based on their file path and name, so that they don't have to be created every time when they are referenced.
     */
    private static Typeface mTypeface;

    public PrimaryTextView(final Context context) {
        this(context, null);
    }

    public PrimaryTextView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrimaryTextView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(context.getAssets(), GlobalConstants.PRIMARY_FONT);
        }
        setTypeface(mTypeface);
    }

}

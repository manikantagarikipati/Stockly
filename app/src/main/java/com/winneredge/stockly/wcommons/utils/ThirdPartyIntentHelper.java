package com.winneredge.stockly.wcommons.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.winneredge.stockly.wcommons.constants.GlobalConstants;

/**
 * Created by Manikanta on 3/1/2016.
 */
public class ThirdPartyIntentHelper {


    //this method allows user to send an email to the provided recipient
    public static void sendMail(Context context, String recipient, String subject) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", recipient, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        //checking if there is are apps installed that can handle this operation
        if (context.getPackageManager().queryIntentActivities(emailIntent, 0).size() > 0) {
            context.startActivity(Intent.createChooser(emailIntent, GlobalConstants.USER_SEND_EMAIL_APPLICATION_SELECTOR_TITLE));
        }
    }


}

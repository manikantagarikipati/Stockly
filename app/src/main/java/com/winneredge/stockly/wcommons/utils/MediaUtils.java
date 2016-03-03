package com.winneredge.stockly.wcommons.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.winneredge.stockly.R;
import com.winneredge.stockly.wcommons.texticon.ColorGenerator;
import com.winneredge.stockly.wcommons.texticon.TextIcons;

/**
 * Created by Manikanta on 2/28/2016.
 */
public class MediaUtils {

    public static TextIcons getRoundedTextIcon(String iconText,Context context){

        iconText = iconText.trim();
        ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
        TextIcons.IBuilder mDrawableBuilder;
        String nextLetter;
        mDrawableBuilder = TextIcons.builder().round();
        String FirstLetter = String.valueOf(iconText.charAt(0)).toUpperCase();
        String[] words = iconText.split("\\s+");
        if(words.length>1){
            nextLetter = String.valueOf(words[1].charAt(0)).toUpperCase();
        }
        else{

            nextLetter = (iconText.length()>1)? String.valueOf(iconText.charAt(1)).toUpperCase():"";
        }

        int fontSize = Math.round(context.getResources().getDimension(R.dimen.updates_text_icon_size));
        return mDrawableBuilder.build(FirstLetter +nextLetter, mColorGenerator.getColor(iconText),fontSize);

    }

    /*
     * calls the appropriate intent based on the file name
     * */
    public static Intent filePublisher(String filename,Uri uri,Intent intent)
    {
        if (filename.contains(".doc") || filename.contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if(filename.contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if(filename.contains(".ppt") || filename.contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if(filename.contains(".xls") || filename.contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if(filename.contains(".zip") || filename.contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
        } else if(filename.contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if(filename.contains(".wav") || filename.contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if(filename.contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if(filename.contains(".jpg") || filename.contains(".jpeg") || filename.contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if(filename.contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if(filename.contains(".3gp") || filename.contains(".mpg") || filename.contains(".mpeg") || filename.contains(".mpe") || filename.contains(".mp4") || filename.contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            intent.setDataAndType(uri, "*/*");
        }

        return intent;
    }
}

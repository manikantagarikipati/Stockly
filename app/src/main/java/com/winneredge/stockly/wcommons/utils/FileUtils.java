package com.winneredge.stockly.wcommons.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.winneredge.stockly.wcommons.constants.GlobalConstants;

import java.io.File;

/**
 * Created by Manikanta on 2/29/2016.
 */
public class FileUtils {


    public static void viewExcelSheet(Context context,String fileName){

        Uri uri = FileUtils.getFileUriFromName(fileName+GlobalConstants.EXCEL_EXTENSION);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            context.startActivity(intent);
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Cannot open this type of file", Toast.LENGTH_LONG).show();
        }
    }

    private static Uri getFileUriFromName(String fileName) {


            File file = new File(Environment.getExternalStorageDirectory()+ GlobalConstants.BASE_DIRECTORY+"/"+fileName);
            return  Uri.fromFile(file);

    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState));
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    public static String getNonConflictingFileName(String fileName){
        createAppDirectoryIfNotExists();
        File myFile = new File(Environment.getExternalStorageDirectory()+ GlobalConstants.BASE_DIRECTORY+"/"+fileName);
        if(myFile.exists()){
            return fileName+System.currentTimeMillis();
        }
        else{
            return fileName;
        }
    }

    public static void createAppDirectoryIfNotExists() {
        File f = new File(Environment.getExternalStorageDirectory()+ GlobalConstants.BASE_DIRECTORY);
        if(f.exists()){
            // directory is present so carry on with file saving in the directory
        }
        else
        {
            try {
                f.mkdir();
            }
            catch (Exception error)
            {
                error.printStackTrace();
            }

        }
    }

}

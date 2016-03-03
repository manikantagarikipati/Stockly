package com.winneredge.stockly.wcommons.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.winneredge.stockly.wcommons.constants.GlobalConstants;
import com.winneredge.stockly.wcommons.database.DBConstants;
import com.winneredge.stockly.wcommons.database.WAsset;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Manikanta on 2/28/2016.
 */
public class ExcelUtils {

    public static String prepareExcelSheet(Context context, String fileName, String sheetName,
                                            List<WAsset> assetList) {

        // check if available and not read only
        if (!FileUtils.isExternalStorageAvailable() || FileUtils.isExternalStorageReadOnly()) {
            Log.e("Excel Utils", "Storage not available or read only");
            return "";
        }

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //New Sheet
        Sheet sheet1 = null;
        String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName);
        sheet1 = wb.createSheet(safeSheetName);

        int rowValue = 0;

        Row row = sheet1.createRow(rowValue);
        List<String> columnNames = getColumnNames();
        for(int i=0;i<columnNames.size();i++){
            c = row.createCell(i);
            c.setCellValue(columnNames.get(i));
            c.setCellStyle(cs);
            sheet1.setColumnWidth(i, (15 * 500));
        }

        Map<String,Object[]> data = createMapWithData(assetList);

        Set<String> keyset = data.keySet();

        for (String key : keyset) {
            Row datarow = sheet1.createRow(rowValue++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = datarow.createCell(cellnum++);
                cell.setCellValue((String)obj);
            }
        }

        String nonConflictingFileName = FileUtils.getNonConflictingFileName(safeSheetName);
        // Create a path where we will place our List of objects on external storage
        File file = new File(Environment.getExternalStorageDirectory()+ GlobalConstants.BASE_DIRECTORY+"/"+nonConflictingFileName+GlobalConstants.EXCEL_EXTENSION);

        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return nonConflictingFileName;
    }

    private static Map<String,Object[]> createMapWithData(List<WAsset> assetList) {
        Map<String, Object[]> data = new HashMap<>();
        for(int i=0;i<assetList.size();i++){
            WAsset wAsset = assetList.get(i);
            data.put(i+"", new Object[] {wAsset.assetBarCode, wAsset.assetName, wAsset.assetComments,wAsset.assetCount});
        }
        return data;
    }

    private static List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>();
        columnNames.add(DBConstants.ASSET_BARCODE);
        columnNames.add(DBConstants.ASSET_NAME);
        columnNames.add(DBConstants.ASSET_COMMENTS);
        columnNames.add(DBConstants.ASSET_COUNT);
        return columnNames;
    }



}

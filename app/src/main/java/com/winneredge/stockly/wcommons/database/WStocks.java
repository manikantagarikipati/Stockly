package com.winneredge.stockly.wcommons.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Manikanta on 2/28/2016.
 */

@Table(name =DBConstants.TABLE_W_STOCK )
public class WStocks extends Model{

    @Column(name = DBConstants.COLUMN_W_STOCK_ID ,unique = true,onUniqueConflict = Column.ConflictAction.REPLACE,index = true)
    public String stockId;

    @Column(name = DBConstants.COLUMN_W_STOCK_CREATED_DATE)
    public String stockCreatedDate;

    @Column(name = DBConstants.COLUMN_W_STOCK_NAME)
    public String stockName;

    @Column(name = DBConstants.COLUMN_W_EXCEL_SHEET_NAME)
    public String stockUpdatedSheetName;

    public WStocks(){super();}

    public static List<WStocks> getListOfStocks() {
        return new Select()
                .from(WStocks.class)
                .orderBy(DBConstants.COLUMN_W_STOCK_CREATED_DATE + " DESC ")
                .execute();
    }
}

package com.winneredge.stockly.wcommons.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Manikanta on 2/28/2016.
 */

@Table(name =DBConstants.TABLE_W_ASSET )
public class WAsset extends Model {

    @Column(name = DBConstants.COLUMN_W_ASSET_ID ,unique = true,onUniqueConflict = Column.ConflictAction.REPLACE)
    public String assetId;

    @Column(name = DBConstants.COLUMN_W_ASSET_NAME)
    public String assetName;

    @Column(name = DBConstants.COLUMN_W_ASSET_BARCODE)
    public String assetBarCode;

    @Column(name = DBConstants.COLUMN_W_ASSET_COMMENTS)
    public String assetComments;

    @Column(name = DBConstants.COLUMN_W_ASSET_ADDED_DATE)
    public String assetAddedDate;

    @Column(name = DBConstants.COLUMN_W_ASSET_EXTRA_FIELDS)
    public Map<String,String> assetExtraFields;

    @Column(name = DBConstants.COLUMN_W_STOCK_ID,index = true)
    public long stockId;

    @Column(name = DBConstants.COLUMN_W_ASSET_COUNT)
    public String assetCount;

    public WAsset(){super();}

    public static List<WAsset> getAssetsForStock(long stockId) {
        return new Select()
                .from(WAsset.class)
                .where(DBConstants.COLUMN_W_STOCK_ID+"=?", stockId)
                .orderBy(DBConstants.COLUMN_W_ASSET_ADDED_DATE + " DESC ")
                .execute();
    }

    public static void deleteAsset(String assetId){
        new Delete().from(WAsset.class).where(DBConstants.COLUMN_W_ASSET_ID + "= ?", assetId).execute();
    }

}

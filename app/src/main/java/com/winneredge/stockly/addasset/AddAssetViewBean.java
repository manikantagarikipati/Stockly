package com.winneredge.stockly.addasset;

import com.winneredge.stockly.wcommons.database.WAsset;

/**
 * Created by Manikanta on 2/28/2016.
 */
public class AddAssetViewBean {

    private long stockId;

    private WAsset wAsset;

    private String barcode;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    private String name;

    private String comments;

    public String getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(String numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    private String numberOfItems;



    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
    }

    public WAsset getwAsset() {
        return wAsset;
    }

    public void setwAsset(WAsset wAsset) {
        this.wAsset = wAsset;
    }

}

package com.winneredge.stockly.listofassets;

import com.winneredge.stockly.wcommons.database.WAsset;
import com.winneredge.stockly.wcommons.database.WStocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manikanta on 2/28/2016.
 */
public class ListOfAssetsViewBean {

    private long stockId;

    private List<WAsset> assetsList = new ArrayList<>();

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    private String stockName;

    public WStocks getwStock() {
        return wStock;
    }

    public void setwStock(WStocks wStock) {
        this.wStock = wStock;
    }

    private WStocks wStock;

    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
    }

    public List<WAsset> getAssetsList() {
        return assetsList;
    }

    public void setAssetsList(List<WAsset> assetsList) {
        this.assetsList = assetsList;
    }




}

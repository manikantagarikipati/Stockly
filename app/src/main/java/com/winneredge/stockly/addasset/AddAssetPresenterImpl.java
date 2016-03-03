package com.winneredge.stockly.addasset;

import android.content.Context;

import com.winneredge.stockly.wcommons.database.WAsset;
import com.winneredge.stockly.wcommons.utils.DateUtil;

import java.util.HashMap;

/**
 * Created by Manikanta on 2/28/2016.
 */
public class AddAssetPresenterImpl implements AddAssetPresenter {

    private AddAssetView view;
    private AddAssetViewBean bean;
    private Context context;

    public AddAssetPresenterImpl(AddAssetView view,AddAssetViewBean bean,Context context){
        this.view = view;
        this.bean = bean;
        this.context = context;
    }

    @Override
    public void userPressedAddAsset() {

        try{
            WAsset wAsset = new WAsset();
            wAsset.assetName = bean.getName();
            wAsset.assetBarCode = bean.getBarcode();
            wAsset.assetComments = bean.getComments();
            wAsset.stockId = bean.getStockId();
            wAsset.assetAddedDate = DateUtil.getCurrentDate();
            wAsset.assetCount = bean.getNumberOfItems();
            //todo dynamic fields functionality to be implemented
            wAsset.assetExtraFields = new HashMap<>();
            wAsset.save();

            bean.setwAsset(wAsset);

            view.userPressedAddAssetSuccess();
        }catch (Exception e){
            view.userPressedAddAssetFailure();
        }

    }

    @Override
    public void userPressedScanBarcode() {
        view.userPressedScanBarcodeSuccess();
    }
}

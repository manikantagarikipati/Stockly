package com.winneredge.stockly.listofassets;

import android.content.Context;

import com.winneredge.stockly.wcommons.database.WAsset;
import com.winneredge.stockly.wcommons.database.WStocks;
import com.winneredge.stockly.wcommons.utils.ExcelUtils;
import com.winneredge.stockly.wcommons.utils.CollectionUtils;
import com.winneredge.stockly.wcommons.utils.StringUtils;

import java.util.List;

/**
 * Created by Manikanta on 2/28/2016.
 */
public class ListOfAssetsPresenterImpl implements ListOfAssetsPresenter{

    private  Context context;
    private ListOfAssetsViewBean bean;
    private ListOfAssetsView view;

    public ListOfAssetsPresenterImpl(Context context,ListOfAssetsViewBean bean,ListOfAssetsView view){
        this.context = context;
        this.view = view;
        this.bean = bean;
    }

    @Override
    public void systemFetchAssetsList() {
        if(bean.getStockId()!=0){
            List<WAsset> assetList = WAsset.getAssetsForStock(bean.getStockId());
            if(CollectionUtils.isEmpty(assetList)){
                view.systemFetchAssetsListEmpty();
            }else{
                bean.setAssetsList(assetList);
                view.systemFetchAssetsListSuccess();
            }
        }else{
            view.systemFetchAssetsListFailure();
        }

    }

    @Override
    public void userPressedCreateExcelSheet() {
        if(CollectionUtils.isNotEmpty(bean.getAssetsList())){
            try{
                String workSheetName = ExcelUtils.prepareExcelSheet(context, bean.getStockName(), bean.getStockName(), bean.getAssetsList());
                if(StringUtils.isNotEmpty(workSheetName)){
                    WStocks stocks = bean.getwStock();
                    stocks.stockUpdatedSheetName = workSheetName;
                    stocks.save();
                    bean.setwStock(stocks);
                    view.userPressedCreateExcelSheetSuccess();
                }else{
                    view.userPressedCreateExcelSheetFailure();
                }

            }catch (Exception e){
                view.userPressedCreateExcelSheetFailure();
            }
        }else{
            view.userPressedCreateExcelSheetFailure();
        }
    }
}

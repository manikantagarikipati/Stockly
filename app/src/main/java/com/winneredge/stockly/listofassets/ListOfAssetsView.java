package com.winneredge.stockly.listofassets;

/**
 * Created by Manikanta on 2/28/2016.
 */
public interface ListOfAssetsView {

    void systemFetchAssetsListSuccess();

    void systemFetchAssetsListFailure();

    void systemFetchAssetsListEmpty();

    void userPressedCreateExcelSheetSuccess();

    void userPressedCreateExcelSheetFailure();
}

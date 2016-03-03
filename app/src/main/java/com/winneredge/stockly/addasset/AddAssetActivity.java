package com.winneredge.stockly.addasset;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.winneredge.stockly.R;
import com.winneredge.stockly.wcommons.activities.WActivity;
import com.winneredge.stockly.wcommons.constants.GlobalConstants;
import com.winneredge.stockly.wcommons.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import zxing.CaptureActivity;

public class AddAssetActivity extends WActivity implements AddAssetView{

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private AddAssetViewBean bean;

    private static final int SCAN_BARCODE_REQUEST_CODE = 100;

    private AddAssetPresenter presenter;

    @InjectView(R.id.til_barcode)
    TextInputLayout tilBarcode;

    @InjectView(R.id.til_asset_name)
    TextInputLayout tilAssetName;

    @InjectView(R.id.til_comment)
    TextInputLayout tilComments;

    @InjectView(R.id.til_number_of_items)
    TextInputLayout tilNumberOfItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        initializeViewBean();
        initPresenter();
        initActionBar();

    }

    private void initActionBar() {
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.add_product));
        }
    }

    @OnClick(R.id.fab_add_asset) void addAsset(){

        if(checkIfAllFieldsAreProvided()){
            presenter.userPressedAddAsset();
        }
    }

    private boolean checkIfAllFieldsAreProvided() {
        boolean validBarCode = false,validName = false ,validComments = false,validItemCount = false;

        String barCode = tilBarcode.getEditText().getText().toString().trim();
        if(StringUtils.isNotEmpty(barCode)){
            validBarCode = true;
            bean.setBarcode(barCode);
        }else{
            tilBarcode.setError("Invalid BarCode");
        }
        String productName = tilAssetName.getEditText().getText().toString().trim();

        if(StringUtils.isNotEmpty(productName)){
            validName = true;
            bean.setName(productName);
        }else{
            tilAssetName.setError("Invalid AssetName");
        }

        String comments = tilComments.getEditText().getText().toString().trim();

        if(StringUtils.isNotEmpty(comments)){
            validComments = true;
            bean.setComments(comments);
        }else{
            tilComments.setError("Comments Cannot be empty");
        }

        String numberOfItems = tilNumberOfItems.getEditText().getText().toString().trim();

        if(StringUtils.isNotEmpty(numberOfItems)){
            validItemCount = true;
            bean.setNumberOfItems(numberOfItems);
        }else{
            tilNumberOfItems.setError("Enter number of items for this asset");
        }
        return (validBarCode && validName
                && validComments && validItemCount);
    }

    @OnClick(R.id.scan_barcode) void scanBarcode(){
        presenter.userPressedScanBarcode();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }

    @Override
    public void initializeViewBean() {

        bean = new AddAssetViewBean();
        if(getIntent()!=null && getIntent().getExtras()!=null){
            bean.setStockId(getIntent().getExtras().getLong(GlobalConstants.STOCK_ID));
        }else{
            Toast.makeText(this,"Cannot add Asset without a stock id",Toast.LENGTH_LONG).show();
            finish();
        }

    }

    @Override
    public void initPresenter() {
        presenter = new AddAssetPresenterImpl(this,bean,this);
    }

    @Override
    public void initialiseComponents() {

    }

    @Override
    public void userPressedScanBarcodeSuccess() {
        Intent scanBarcodeIntent = new Intent(this, CaptureActivity.class);
        startActivityForResult(scanBarcodeIntent, SCAN_BARCODE_REQUEST_CODE);
    }

    @Override
    public void userPressedAddAssetSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void userPressedAddAssetFailure() {
        Toast.makeText(this,"Cannot add asset at this moment please try later",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == SCAN_BARCODE_REQUEST_CODE){
                String scannedCode = data.getStringExtra(GlobalConstants.SCANNED_CODE);
                if(tilBarcode.getEditText()!=null){
                    //clear the existing text before setting obtained barcode
                    tilBarcode.getEditText().setText("");
                    tilBarcode.getEditText().append(scannedCode);
                }
            }
        }
    }
}

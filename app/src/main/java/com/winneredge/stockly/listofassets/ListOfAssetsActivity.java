package com.winneredge.stockly.listofassets;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.winneredge.stockly.R;
import com.winneredge.stockly.addasset.AddAssetActivity;
import com.winneredge.stockly.wcommons.activities.WActivity;
import com.winneredge.stockly.wcommons.constants.GlobalConstants;
import com.winneredge.stockly.wcommons.database.WStocks;
import com.winneredge.stockly.wcommons.floatingactionwidget.FloatingActionMenu;
import com.winneredge.stockly.wcommons.recyclerviewaddons.DividerItemDecoration;
import com.winneredge.stockly.wcommons.utils.DateUtil;
import com.winneredge.stockly.wcommons.utils.FileUtils;
import com.winneredge.stockly.wcommons.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ListOfAssetsActivity extends WActivity implements ListOfAssetsView{


    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.rv_list_of_assets)
    RecyclerView listOfAssetsRecyclerView;

    @InjectView(R.id.add_info_menu)
    FloatingActionMenu floatingActionMenu;

    @InjectView(R.id.activity_asset_list)
    RelativeLayout emptyView;
    private ListOfAssetsViewBean bean;
    private ListOfAssetsPresenter presenter;
    private final int ADD_PRODUCT_REQUEST_CODE = 100;
    private ListOfAssetsRecyclerViewAdapter listOfAssetsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_assets);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        initActionBar();
        initializeViewBean();
        initPresenter();
        initialiseComponents();
    }

    private void initActionBar() {
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @OnClick(R.id.add_asset) void addAssetClicked(){
        floatingActionMenu.close(true);
        Intent addAsset = new Intent(this, AddAssetActivity.class);
        addAsset.putExtra(GlobalConstants.STOCK_ID,bean.getStockId());
        startActivityForResult(addAsset, ADD_PRODUCT_REQUEST_CODE);
    }

    @OnClick(R.id.upload_excel) void uploadExcelClicked(){
        floatingActionMenu.close(true);
        presenter.userPressedCreateExcelSheet();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == ADD_PRODUCT_REQUEST_CODE){
                presenter.systemFetchAssetsList();
            }
        }
    }

    @Override
    public void initializeViewBean() {
        bean = new ListOfAssetsViewBean();
        if(getIntent()!=null && getIntent().getExtras()!=null){

            bean.setStockId(getIntent().getLongExtra(GlobalConstants.STOCK_ID, 0));
            bean.setStockName(getIntent().getStringExtra(GlobalConstants.STOCK_NAME));
            bean.setwStock(WStocks.load(WStocks.class, bean.getStockId()));
            setToolbarTitle();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_of_assets,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_view_excel:
                if(StringUtils.isNotEmpty(bean.getwStock().stockUpdatedSheetName)){
                    FileUtils.viewExcelSheet(this,bean.getwStock().stockUpdatedSheetName);
                }else{
                    Toast.makeText(ListOfAssetsActivity.this, "Cannot find spread sheet for this stock create an excel sheet",
                            Toast.LENGTH_LONG).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if(floatingActionMenu.isOpened()){
            floatingActionMenu.close(true);
        }else{
            if(bean.getStockId()!= 0){
                setResult(RESULT_OK);
            }else{
                setResult(RESULT_CANCELED);
            }
            finish();
            super.onBackPressed();
        }
    }


    @Override
    public void initPresenter() {
        presenter = new ListOfAssetsPresenterImpl(this,bean,this);
    }

    @Override
    public void initialiseComponents() {
        floatingActionMenu.setClosedOnTouchOutside(true);
        presenter.systemFetchAssetsList();
    }

    @Override
    public void systemFetchAssetsListSuccess() {


        emptyView.setVisibility(View.GONE);
        listOfAssetsRecyclerView.setVisibility(View.VISIBLE);

        if(listOfAssetsRecyclerViewAdapter == null){
            listOfAssetsRecyclerViewAdapter = new ListOfAssetsRecyclerViewAdapter(bean.getAssetsList(),this);
            listOfAssetsRecyclerView.setHasFixedSize(true);
            listOfAssetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            listOfAssetsRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
            listOfAssetsRecyclerView.setAdapter(listOfAssetsRecyclerViewAdapter);
        }else{
            listOfAssetsRecyclerViewAdapter.resetAdapter(bean.getAssetsList());
        }
    }

    @Override
    public void systemFetchAssetsListFailure() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_stock_name, null);
        final TextInputLayout tilStockName = (TextInputLayout)view.findViewById(R.id.til_stock_name);
        final TextView tvCancel = (TextView)view.findViewById(R.id.tv_cancel);
        final TextView tvOk = (TextView)view.findViewById(R.id.tv_ok);

        alertDialog.setView(view);
        alertDialog.setCancelable(false);

        alertDialog.create();
        final AlertDialog dialog = alertDialog.show();

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tilStockName.getEditText() != null) {
                    String stockName = tilStockName.getEditText().getText().toString();
                    if (StringUtils.isNotEmpty(stockName)) {
                        dialog.dismiss();
                        bean.setStockName(tilStockName.getEditText().getText().toString());
                        createStock();
                        emptyView.setVisibility(View.VISIBLE);
                        listOfAssetsRecyclerView.setVisibility(View.GONE);
                        setToolbarTitle();
                    } else {
                        tilStockName.setError("Stock Name cannot be empty");
                    }
                }
            }
        });
    }

    @Override
    public void systemFetchAssetsListEmpty() {
        emptyView.setVisibility(View.VISIBLE);
        listOfAssetsRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void userPressedCreateExcelSheetSuccess() {
        FileUtils.viewExcelSheet(this,bean.getwStock().stockUpdatedSheetName);
        Toast.makeText(this, "Excel sheet created successfully!! with name " + bean.getStockName(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void userPressedCreateExcelSheetFailure() {
        Toast.makeText(this,"Failed to prepare excel sheet",Toast.LENGTH_LONG).show();
    }

    private void createStock() {
        WStocks wStocks = new WStocks();
        wStocks.stockCreatedDate = DateUtil.getCurrentDate();
        wStocks.stockName = bean.getStockName();
        wStocks.stockUpdatedSheetName = "";
        wStocks.save();
        bean.setwStock(wStocks);
        bean.setStockId(wStocks.getId());
    }

    private void setToolbarTitle(){
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(bean.getStockName());
        }
    }
}

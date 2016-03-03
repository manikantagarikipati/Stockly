package com.winneredge.stockly.mystocks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.winneredge.stockly.R;
import com.winneredge.stockly.licenses.LicensesActivity;
import com.winneredge.stockly.listofassets.ListOfAssetsActivity;
import com.winneredge.stockly.portfoilio.PortfolioActivity;
import com.winneredge.stockly.wcommons.activities.WActivity;
import com.winneredge.stockly.wcommons.constants.GlobalConstants;
import com.winneredge.stockly.wcommons.database.WStocks;
import com.winneredge.stockly.wcommons.floatingactionwidget.FloatingActionButton;
import com.winneredge.stockly.wcommons.recyclerviewaddons.DividerItemDecoration;
import com.winneredge.stockly.wcommons.utils.CollectionUtils;
import com.winneredge.stockly.wcommons.utils.ThirdPartyIntentHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HomeActivity extends WActivity {

    @InjectView(R.id.activity_home_empty_view)
    View emptyView;

    @InjectView(R.id.fab_add_asset)
    FloatingActionButton fabAdd;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.stocks_recycler_view)
    RecyclerView stocksRecyclerView;

    private StocksRecyclerViewAdapter stocksRecyclerViewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        initializeViewBean();
        initPresenter();
        initialiseComponents();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.fab_add_asset) void addProduct(){
        hideFab();
    }

    private void hideFab() {
         Animation   fabAnimation = AnimationUtils.loadAnimation(this,R.anim.fab_hide);

        fabAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                    fabAdd.setVisibility(View.GONE);
                    Intent addProductIntent = new Intent(HomeActivity.this, ListOfAssetsActivity.class);
                    startActivityForResult(addProductIntent, GlobalConstants.ADD_PRODUCT_REQUEST_CODE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fabAdd.startAnimation(fabAnimation);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fabAdd.setVisibility(View.VISIBLE);

            if(requestCode == GlobalConstants.ADD_PRODUCT_REQUEST_CODE){
                if(resultCode == RESULT_OK){
                setStocksToAdapter();
            }
        }
    }

    @Override
    public void initializeViewBean() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initialiseComponents() {


        setStocksToAdapter();

    }

    private void setStocksToAdapter() {

        List<WStocks> stocks = WStocks.getListOfStocks();
        if(CollectionUtils.isNotEmpty(stocks)){
            emptyView.setVisibility(View.GONE);
            stocksRecyclerView.setVisibility(View.VISIBLE);
            if(stocksRecyclerViewAdapter == null){
                stocksRecyclerViewAdapter = new StocksRecyclerViewAdapter(stocks,this);
                stocksRecyclerView.setHasFixedSize(true);
                stocksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                stocksRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
                stocksRecyclerView.setAdapter(stocksRecyclerViewAdapter);
            }else{
                stocksRecyclerViewAdapter.resetAdapter(stocks);
            }

        }else{
            stocksRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.nav_about_me:
                Intent portfolioIntent = new Intent(this, PortfolioActivity.class);
                startActivity(portfolioIntent);
                return false;
            case R.id.nav_feedback:
                ThirdPartyIntentHelper.sendMail(this, "manikanta.garikipati@gmail.com", "Asset Manager FeedBack");
                return false;
            case R.id.nav_license:
                Intent licenseIntent = new Intent(this, LicensesActivity.class);
                startActivity(licenseIntent);
            default:
                super.onOptionsItemSelected(item);
                return true;
        }

    }

}

package com.winneredge.stockly.mystocks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.winneredge.stockly.R;
import com.winneredge.stockly.listofassets.ListOfAssetsActivity;
import com.winneredge.stockly.wcommons.constants.GlobalConstants;
import com.winneredge.stockly.wcommons.database.WAsset;
import com.winneredge.stockly.wcommons.database.WStocks;
import com.winneredge.stockly.wcommons.utils.CollectionUtils;
import com.winneredge.stockly.wcommons.utils.MediaUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Manikanta on 2/28/2016.
 */
public class StocksRecyclerViewAdapter extends RecyclerView.Adapter<StocksRecyclerViewAdapter.StockViewHolder> {


    private List<WStocks> stocksList;
    private Context context;

    public StocksRecyclerViewAdapter(List<WStocks> stocksList,Context context){
        this.stocksList = stocksList;
        this.context = context;
    }

    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View stockView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_item, parent, false);
        return new StockViewHolder(stockView);
    }

    @Override
    public void onBindViewHolder(final StockViewHolder holder, int position) {
        final WStocks stocks = stocksList.get(position);

        holder.stockName.setText(stocks.stockName);
        holder.stockThumbnail.setImageDrawable(MediaUtils.getRoundedTextIcon(stocks.stockName, context));

        List<WAsset> assetsList = WAsset.getAssetsForStock(stocks.getId());
        if(CollectionUtils.isNotEmpty(assetsList)){
            holder.stockSheetName.setText(context.getString(R.string.number_of_assets, "", assetsList.size()));
        }else{
            holder.stockSheetName.setText(context.getString(R.string.assets_not_added));
        }

        holder.stockCreatedDate.setText(stocks.stockCreatedDate);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent assetList = new Intent(context, ListOfAssetsActivity.class);
                assetList.putExtra(GlobalConstants.STOCK_ID,stocks.getId());
                assetList.putExtra(GlobalConstants.STOCK_NAME,stocks.stockName);
                ((HomeActivity)context).startActivityForResult(assetList, GlobalConstants.ADD_PRODUCT_REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stocksList.size();
    }

    public void resetAdapter(List<WStocks> latestStock) {
        if(CollectionUtils.isNotEmpty(latestStock)){
            stocksList.clear();
            stocksList.addAll(latestStock);
            notifyDataSetChanged();
        }

    }

    static class StockViewHolder  extends RecyclerView.ViewHolder{

        @InjectView(R.id.stock_sheet_name)
        TextView stockSheetName;

        @InjectView(R.id.stock_created_date) TextView stockCreatedDate;

        @InjectView(R.id.stock_thumbnail)
        ImageView stockThumbnail;

        @InjectView(R.id.stock_name)
        TextView stockName;

        public final View mView;

        StockViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.inject(this, itemView);
        }
    }


}

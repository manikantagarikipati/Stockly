package com.winneredge.stockly.listofassets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.winneredge.stockly.R;
import com.winneredge.stockly.wcommons.database.WAsset;
import com.winneredge.stockly.wcommons.utils.CollectionUtils;
import com.winneredge.stockly.wcommons.utils.MediaUtils;
import com.winneredge.stockly.wcommons.utils.StringUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Manikanta on 2/28/2016.
 */
public class ListOfAssetsRecyclerViewAdapter extends RecyclerView.Adapter<ListOfAssetsRecyclerViewAdapter.ListOFAssetsViewHolder> {

    private List<WAsset> assetList;
    private Context context;

    public ListOfAssetsRecyclerViewAdapter(List<WAsset> assetsList,Context context){
        this.assetList = assetsList;
        this.context = context;
    }


    @Override
    public ListOFAssetsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View assetsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.asset_item, parent, false);
        return new ListOFAssetsViewHolder(assetsView);
    }


    @Override
    public void onBindViewHolder(final ListOFAssetsViewHolder holder, int position) {
        final WAsset asset = assetList.get(position);
        holder.assetBarCode.setText(asset.assetBarCode);
        holder.assetName.setText(asset.assetName);
        holder.assetThumbnail.setImageDrawable(MediaUtils.getRoundedTextIcon(asset.assetName, context));
        holder.assetCreatedDate.setText(asset.assetAddedDate);
        holder.numberOfItems.setText(context.getString(R.string.number_of_items_per_asset,asset.assetCount));
        if(StringUtils.isNotEmpty(asset.assetComments)){
            holder.assetComments.setVisibility(View.VISIBLE);
            holder.assetComments.setText(asset.assetComments);
        }else{
            holder.assetComments.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }

    public void resetAdapter(List<WAsset> assetsList) {
        if(CollectionUtils.isNotEmpty(assetsList)){
            assetList.clear();
            assetList.addAll(assetsList);
            notifyDataSetChanged();
        }
    }

    static class ListOFAssetsViewHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.asset_thumbnail)
        ImageView assetThumbnail;

        @InjectView(R.id.asset_name)
        TextView assetName;

        @InjectView(R.id.asset_created_date)
        TextView assetCreatedDate;

        @InjectView(R.id.asset_comments)
        TextView assetComments;

        @InjectView(R.id.asset_bar_code)
        TextView assetBarCode;

        @InjectView(R.id.number_of_items)
        TextView numberOfItems;

        public final View mView;

        public ListOFAssetsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.inject(this,itemView);
        }
    }
}

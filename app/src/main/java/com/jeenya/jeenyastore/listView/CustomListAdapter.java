package com.jeenya.jeenyastore.listView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.jeenya.jeenyastore.R;
import com.jeenya.jeenyastore.volley.ProductDataHolder;
import com.jeenya.jeenyastore.volley.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by hp on 6/10/2015.
 */
public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.MyViewHolder> {

    ArrayList<ProductDataHolder> productDataList = new ArrayList<>();
    LayoutInflater inflator;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private ClickListener clickListener;
    //FOR TEST
    private int listOrGrid = 0;


    public void setProductData(ArrayList<ProductDataHolder> productDataList) {
        this.productDataList = productDataList;
        notifyItemRangeChanged(0, productDataList.size());

    }

    public CustomListAdapter(Context context, int a) {
        inflator = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
        listOrGrid = a;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (listOrGrid == 0) {
            view = inflator.inflate(R.layout.layout_custom_list_item, parent, false);

        }
        if (listOrGrid == 1) {
            view = inflator.inflate(R.layout.layout_custom_list_item_linear_layout, parent, false);
        }

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        ProductDataHolder currentProduct = productDataList.get(position);
        holder.title.setText(currentProduct.getName());

        // Log.d("Product  Adapter Title", currentProduct.getName());
        //Log.d("Product Adapter price", currentProduct.getPrice());
        holder.price.setText(currentProduct.getPrice());


        String urlThumbNail = currentProduct.getThumbNailUrl();
        Log.d("thumbnail", urlThumbNail);
        if (urlThumbNail != null) {
            imageLoader.get(urlThumbNail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.thumbNail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView price;
        ImageView thumbNail;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.product_title_list);
            price = (TextView) itemView.findViewById(R.id.price_list);
            thumbNail = (ImageView) itemView.findViewById(R.id.thumbnail_list);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition(), productDataList);
            }
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position, ArrayList<ProductDataHolder> product);

    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

}

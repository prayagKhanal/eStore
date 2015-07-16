package com.jeenya.jeenyastore.cart;

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
import com.jeenya.jeenyastore.db.DatabaseHandler;
import com.jeenya.jeenyastore.db.SqlDataHolder;
import com.jeenya.jeenyastore.volley.RetriveMyApplicationContext;
import com.jeenya.jeenyastore.volley.VolleySingleton;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp on 6/21/2015.
 */
public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartViewHolder> {

    ArrayList<SqlDataHolder> productDataList = new ArrayList<>();
    LayoutInflater inflator;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private ClickListener clickListener;
    TextView tot;


    public void setProductData(ArrayList<SqlDataHolder> productDataList) {
        this.productDataList = productDataList;
        notifyItemRangeChanged(0, productDataList.size());
    }

    public CartItemAdapter(Context context, TextView total) {
        tot = total;
        inflator = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflator.inflate(R.layout.custom_layout_list_cart, parent, false);
        CartViewHolder cartViewHolder = new CartViewHolder(view);
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, final int position) {

        final SqlDataHolder currentProduct = productDataList.get(position);

        holder.title.setText(currentProduct.getTitle());
        holder.size.setText(currentProduct.getSize());
        holder.color.setText(currentProduct.getColor());
        holder.price.setText(currentProduct.getPrice());
        holder.quantity.setText(currentProduct.getQuantity());
        holder.total.setText(currentProduct.getTotal());

        //DELETE BUTTON CLICK LISTENER
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler d = new DatabaseHandler(RetriveMyApplicationContext.getAppContext());
                d.deleteItem(currentProduct.getId_database_row());
                productDataList.remove(holder.getPosition());
                notifyItemRemoved(holder.getPosition());

                //Changing Total Amt on Item Deleted From Cart
                int total = calculateTotal();
                tot.setText(String.valueOf(total));


            }
        });


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

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView price;
        TextView size;
        TextView color;
        TextView quantity;
        TextView total;
        //ImageView thumbNail;
        CircleImageView thumbNail;
        ImageView del;

        public CartViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            thumbNail = (CircleImageView) itemView.findViewById(R.id.cartList_thumbnail);

            title = (TextView) itemView.findViewById(R.id.cartList_title);
            size = (TextView) itemView.findViewById(R.id.cartList_size);
            color = (TextView) itemView.findViewById(R.id.cartList_color);

            price = (TextView) itemView.findViewById(R.id.cartList_price);
            quantity = (TextView) itemView.findViewById(R.id.cartList_quantity);
            total = (TextView) itemView.findViewById(R.id.cartList_total);
            del = (ImageView) itemView.findViewById(R.id.cartList_btnDelete);

        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition(), productDataList);
            }
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position, ArrayList<SqlDataHolder> product);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    private int calculateTotal() {

        int amtTotal = 0;

         /* PAss DATA in Adapter */
        DatabaseHandler d = new DatabaseHandler(RetriveMyApplicationContext.getAppContext());
        ArrayList<SqlDataHolder> m = d.retriveCartData();

        if (!m.isEmpty()) {

            for (int i = 0; i < m.size(); i++) {
                SqlDataHolder currentProduct = m.get(i);
                int t = Integer.parseInt(currentProduct.getTotal());
                amtTotal = amtTotal + t;
            }
        }
        return amtTotal;
    }


}
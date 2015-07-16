package com.jeenya.jeenyastore.dynamicMenuHome;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.jeenya.jeenyastore.volley.RetriveMyApplicationContext;
import com.jeenya.jeenyastore.volley.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by hp on 6/25/2015.
 */
public class DynamicListAdapter extends RecyclerView.Adapter<DynamicListAdapter.MyViewHolder> {

    ArrayList<MenuDataHolder> menuData = new ArrayList<>();
    LayoutInflater inflator;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;


    public void setMenuData(ArrayList<MenuDataHolder> menuData) {
        this.menuData = menuData;
        notifyItemRangeChanged(0, menuData.size());

    }

    public DynamicListAdapter(Context context) {
        inflator = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = inflator.inflate(R.layout.layout_custom_home_menu_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        MenuDataHolder currentMenu = menuData.get(position);
        holder.menuHead.setText(currentMenu.getMenuTitle());
        String urlIcon = currentMenu.getMenuImageUrl();
        Log.d("thumbnail", urlIcon);

        if (urlIcon != null) {
            imageLoader.get(urlIcon, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.menuIcon.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Bitmap icon = BitmapFactory.decodeResource(RetriveMyApplicationContext.getAppContext().getResources(),
                            R.drawable.ic_error);
                    holder.menuIcon.setImageBitmap(icon);
                }
            });
        }


    }


    @Override
    public int getItemCount() {
        return menuData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menuHead;
        ImageView menuIcon;


        public MyViewHolder(View itemView) {
            super(itemView);
            menuHead = (TextView) itemView.findViewById(R.id.menu_header);
            menuIcon = (ImageView) itemView.findViewById(R.id.menu_icon);

        }
    }

}

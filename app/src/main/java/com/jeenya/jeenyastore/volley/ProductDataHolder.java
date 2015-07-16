package com.jeenya.jeenyastore.volley;

import java.util.ArrayList;

/**
 * Created by hp on 6/11/2015.
 */
public class ProductDataHolder {
    String productId;
    String thumbNailUrl;
    String price;
    String name;
    String productDescription;
    ArrayList<String> productSizeList;
    ArrayList<String> productColorList;
    ArrayList<String> imageUrlList;

    public ArrayList<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(ArrayList<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }


    public ArrayList<String> getProductColorList() {
        return productColorList;
    }

    public void setProductColorList(ArrayList<String> productColorList) {
        this.productColorList = productColorList;
    }


    public ArrayList<String> getProductSizeList() {


        return productSizeList;
    }

    public void setProductSizeList(ArrayList<String> productSizeList) {
        this.productSizeList = productSizeList;
    }


    public String getProductId() {
        return productId;
    }

    public String getThumbNailUrl() {


        return thumbNailUrl;
    }

    public String getPrice() {

        return price;
    }

    public String getName() {

        return name;
    }

    public void setProductId(String productId) {

        this.productId = productId;

    }

    public void setThumbNailUrl(String thumbNailUrl) {
        this.thumbNailUrl = thumbNailUrl;
    }

    public void setPrice(String price) {
        this.price = price;

    }

    public void setName(String name) {
        this.name = name;
    }
}

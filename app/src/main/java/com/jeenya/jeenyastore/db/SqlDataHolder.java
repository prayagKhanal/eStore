package com.jeenya.jeenyastore.db;

/**
 * Created by hp on 6/19/2015.
 */
public class SqlDataHolder {


    int id_database_row;
    String size;
    String color;
    String quantity;
    String title;
    String price;
    String thumbNailUrl;
    String total;
    String product_Id;

    public String getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(String product_Id) {
        this.product_Id = product_Id;
    }

    public static int getPosition() {
        return position;
    }

    public static void setPosition(int position) {
        SqlDataHolder.position = position;
    }

    public static int position;

    public int getId_database_row() {
        return id_database_row;
    }

    public void setId_database_row(int id_database_row) {
        this.id_database_row = id_database_row;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal() {
        try {
            int t = Integer.parseInt(price) * Integer.parseInt(quantity);
            total = String.valueOf(t);
        } catch (Exception e) {
            return String.valueOf(0);

        }
        return total;
    }


    public String getThumbNailUrl() {
        return thumbNailUrl;
    }

    public void setThumbNailUrl(String thumbNailUrl) {
        this.thumbNailUrl = thumbNailUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}

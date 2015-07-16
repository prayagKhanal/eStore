package com.jeenya.jeenyastore.productDetails;

/**
 * Created by hp on 6/18/2015.
 */
public class ProductCartStack {


    private static String ColorSelected = "N/A", size = "N/A";
    private static int quantity = 1;
    private static String price = "N/A", url = "N/A";
    private static String productId = "N/A";

    public static String getProductId() {
        return productId;
    }

    public static void setProductId(String productId) {
        ProductCartStack.productId = productId;
    }

    public static String getPrice() {
        return price;
    }

    public static void setPrice(String price) {
        ProductCartStack.price = price;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        ProductCartStack.url = url;
    }


    public static void resetCart() {
        ColorSelected = "N/A";
        size = "N/A";
        quantity = 1;
        price = "N/A";
        url = "N/A";

    }

    public static int getQuantity() {
        return quantity;
    }

    public static void setQuantity(int quantity) {
        ProductCartStack.quantity = quantity;
    }

    public static String getColorSelected() {
        return ColorSelected;
    }

    public static void setColorSelected(String colorSelected) {
        ColorSelected = colorSelected;
    }

    public static String getSize() {
        return size;
    }

    public static void setSize(String size) {
        ProductCartStack.size = size;
    }


}

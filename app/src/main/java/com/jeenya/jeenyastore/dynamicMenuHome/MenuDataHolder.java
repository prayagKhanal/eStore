package com.jeenya.jeenyastore.dynamicMenuHome;

import java.util.ArrayList;

/**
 * Created by hp on 6/25/2015.
 */
public class MenuDataHolder {

    String menuTitle;
    String menuImageUrl;
    ArrayList<String> subMenulList;

    public String getMenuImageUrl() {
        return menuImageUrl;
    }

    public void setMenuImageUrl(String menuImageUrl) {
        this.menuImageUrl = menuImageUrl;
    }


    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public ArrayList<String> getSubMenulList() {
        return subMenulList;
    }

    public void setSubMenulList(ArrayList<String> subMenulList) {
        this.subMenulList = subMenulList;
    }
}

package com.clientBase.model;

import java.io.Serializable;
import java.util.List;


public class MainModel implements Serializable {


    private List<ShopModel> listTuIjian;
    private  List<ShopModel> listShop;


    public List<ShopModel> getListTuIjian() {
        return listTuIjian;
    }

    public void setListTuIjian(List<ShopModel> listTuIjian) {
        this.listTuIjian = listTuIjian;
    }

    public List<ShopModel> getListShop() {
        return listShop;
    }

    public void setListShop(List<ShopModel> listShop) {
        this.listShop = listShop;
    }
}

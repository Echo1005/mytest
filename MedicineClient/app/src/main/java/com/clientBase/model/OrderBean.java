package com.clientBase.model;

import java.io.Serializable;
import java.util.List;


public class OrderBean implements Serializable {


    private List<ShopModel> shopLists;
    private String orderMessageMoney;
    private String orderMessageId;
    private String orderUserId;
    private String orderTime;
    private String orderUserName;
    private int orderId;
    private String orderMessageName;
    private String orderAddress;
    private String orderNo;
    private String orderCreatime;
    private String orderRemark;
    private String orderState;

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public List<ShopModel> getShopLists() {
        return shopLists;
    }

    public void setShopLists(List<ShopModel> shopLists) {
        this.shopLists = shopLists;
    }

    public String getOrderMessageMoney() {
        return orderMessageMoney;
    }

    public void setOrderMessageMoney(String orderMessageMoney) {
        this.orderMessageMoney = orderMessageMoney;
    }

    public String getOrderMessageId() {
        return orderMessageId;
    }

    public void setOrderMessageId(String orderMessageId) {
        this.orderMessageId = orderMessageId;
    }

    public String getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(String orderUserId) {
        this.orderUserId = orderUserId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderMessageName() {
        return orderMessageName;
    }

    public void setOrderMessageName(String orderMessageName) {
        this.orderMessageName = orderMessageName;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderCreatime() {
        return orderCreatime;
    }

    public void setOrderCreatime(String orderCreatime) {
        this.orderCreatime = orderCreatime;
    }
}

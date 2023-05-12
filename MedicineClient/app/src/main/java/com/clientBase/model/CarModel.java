package com.clientBase.model;

import java.io.Serializable;


public class CarModel implements Serializable {

    private String   carShopId;
    private String   carUserId;
    private String   carId;


    private String shopMessage;
    private String shopName;
    private String shopId;
    private String shopMoney;
    private String shopImg;
    private String shopCreatime;
    private String shopUserId;
    private String shopUserName;
    private String userPhone;
    private String shopIsIm;

    private String shopTypeId;
    private String shopTypeName;

    private boolean isEndPrice;
    private PriceModel priceUser;
    private String shopIsSend;
    private String shopRecycling;
//    private int shopNumber = 1;
    private int carNumber = 1;
    private boolean isChoice =true;

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }

//    public int getShopNumber() {
//        return shopNumber;
//    }
//
//    public void setShopNumber(int shopNumber) {
//        this.shopNumber = shopNumber;
//    }

    public String getCarShopId() {
        return carShopId;
    }

    public void setCarShopId(String carShopId) {
        this.carShopId = carShopId;
    }

    public String getCarUserId() {
        return carUserId;
    }

    public void setCarUserId(String carUserId) {
        this.carUserId = carUserId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getShopMessage() {
        return shopMessage;
    }

    public void setShopMessage(String shopMessage) {
        this.shopMessage = shopMessage;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(String shopMoney) {
        this.shopMoney = shopMoney;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public String getShopCreatime() {
        return shopCreatime;
    }

    public void setShopCreatime(String shopCreatime) {
        this.shopCreatime = shopCreatime;
    }

    public String getShopUserId() {
        return shopUserId;
    }

    public void setShopUserId(String shopUserId) {
        this.shopUserId = shopUserId;
    }

    public String getShopUserName() {
        return shopUserName;
    }

    public void setShopUserName(String shopUserName) {
        this.shopUserName = shopUserName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getShopIsIm() {
        return shopIsIm;
    }

    public void setShopIsIm(String shopIsIm) {
        this.shopIsIm = shopIsIm;
    }

    public String getShopTypeId() {
        return shopTypeId;
    }

    public void setShopTypeId(String shopTypeId) {
        this.shopTypeId = shopTypeId;
    }

    public String getShopTypeName() {
        return shopTypeName;
    }

    public void setShopTypeName(String shopTypeName) {
        this.shopTypeName = shopTypeName;
    }

    public boolean isEndPrice() {
        return isEndPrice;
    }

    public void setEndPrice(boolean endPrice) {
        isEndPrice = endPrice;
    }

    public PriceModel getPriceUser() {
        return priceUser;
    }

    public void setPriceUser(PriceModel priceUser) {
        this.priceUser = priceUser;
    }

    public String getShopIsSend() {
        return shopIsSend;
    }

    public void setShopIsSend(String shopIsSend) {
        this.shopIsSend = shopIsSend;
    }

    public String getShopRecycling() {
        return shopRecycling;
    }

    public void setShopRecycling(String shopRecycling) {
        this.shopRecycling = shopRecycling;
    }
}

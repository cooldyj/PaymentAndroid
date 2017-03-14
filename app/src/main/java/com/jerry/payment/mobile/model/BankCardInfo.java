package com.jerry.payment.mobile.model;

/**
 * 银行卡实体类
 * Created by jerry on 2016/7/19.
 */
public class BankCardInfo {

    private String iconUrl;
    private String bankName;
    private String bankNumber;
    private String bankType;

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public String getBankType() {
        return bankType;
    }
}

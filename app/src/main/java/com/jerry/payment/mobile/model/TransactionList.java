package com.jerry.payment.mobile.model;

import java.util.List;

/**
 * 交易列表实体类
 * Created by jerry on 2016/8/11.
 */
public class TransactionList {


    /**
     * pageSize : 60
     * pageNo : 0
     * pageCount : 187
     * list : [{"transactionId":10121312321321321,"transactionType":0,"transactionNumber":500,"currency":"EUR","balance":600.12,"transactionDate":1470894118973},{"transactionId":101231232122312,"transactionType":1,"transactionNumber":1200,"currency":"EUR","balance":88600,"transactionDate":1470894118973},{"transactionId":10124123123122314,"transactionType":0,"transactionNumber":20.22,"currency":"EUR","balance":2200.66,"transactionDate":1470894118973}]
     */

    private int pageSize;
    private int pageNo;
    private int pageCount;
    /**
     * transactionId : 10121312321321321
     * transactionType : 0
     * transactionNumber : 500.0
     * currency : EUR
     * balance : 600.12
     * transactionDate : 1470894118973
     */

    private List<ListBean> list;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private long transactionId;
        private int transactionType;
        private double transactionNumber;
        private String currency;
        private double balance;
        private long transactionDate;

        public long getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(long transactionId) {
            this.transactionId = transactionId;
        }

        public int getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(int transactionType) {
            this.transactionType = transactionType;
        }

        public double getTransactionNumber() {
            return transactionNumber;
        }

        public void setTransactionNumber(double transactionNumber) {
            this.transactionNumber = transactionNumber;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public long getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(long transactionDate) {
            this.transactionDate = transactionDate;
        }
    }
}

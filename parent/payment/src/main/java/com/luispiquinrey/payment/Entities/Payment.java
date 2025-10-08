package com.luispiquinrey.payment.Entities;

import java.time.LocalDate;
public class Payment {
    private String idPayment;
    private String provider;
    private LocalDate expiryDate;
    private long accountNumber;
    private int CSV;

    

    public Payment(int CSV, long accountNumber, LocalDate expiryDate, String provider) {
        this.CSV = CSV;
        this.accountNumber = accountNumber;
        this.expiryDate = expiryDate;
        this.provider = provider;
    }

    public Payment() {
    }

    public String getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(String idPayment) {
        this.idPayment = idPayment;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getCSV() {
        return CSV;
    }

    public void setCSV(int CSV) {
        this.CSV = CSV;
    }

}

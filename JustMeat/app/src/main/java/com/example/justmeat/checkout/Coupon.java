package com.example.justmeat.checkout;

public class Coupon {
    String code;
    Double percentage;
    boolean available;

    Coupon(String code, double percentage, int available){
        this.code = code;
        this.percentage = percentage;
        if(available == 200)
            this.available = true;
        else
            this.available = false;
    }

    public Double getPercentage() {
        return percentage;
    }

    public String getCode() {
        return code;
    }

    public boolean isAvailable() {
        return available;
    }
}

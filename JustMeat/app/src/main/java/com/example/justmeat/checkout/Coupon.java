package com.example.justmeat.checkout;

class Coupon {
    String code;
    Double percentage;

    Coupon(String code, double percentage){
        this.code = code;
        this.percentage = percentage;
    }

    public Double getPercentage() {
        return percentage;
    }

    public String getCode() {
        return code;
    }
}

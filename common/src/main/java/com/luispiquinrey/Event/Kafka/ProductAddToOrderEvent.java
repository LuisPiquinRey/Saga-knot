package com.luispiquinrey.Event.Kafka;

public class ProductAddToOrderEvent {
    private String idProduct;

    public ProductAddToOrderEvent(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
    
}

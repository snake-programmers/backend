package com.alesharik.storemain.repository.projections;

public interface NearestProductProjection {
    Long getId();
    String getAddress();
    String getStore();
    Long getStoreid();
    int getPrice();
    double getDistance();
}

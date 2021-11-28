package com.alesharik.storemain.repository.projections;

public interface DeliveryPointProjection {
    double getDistance();
    Long getId();
    String getAddress();
    String getName();
    Long getServiceid();
}

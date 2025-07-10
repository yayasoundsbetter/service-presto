package com.yaya.presto.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID shipmentId;

    @Column(unique = true)
    private UUID orderId;

    private UUID userId;

    @Enumerated(EnumType.STRING)
    private Status status = Status.preparing;

    private boolean confirmedByDeliveryAgent = false;

    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private String trackingCode;

    public Shipment() {
    }

    public Shipment(UUID orderId, UUID userId) {
        this.orderId = orderId;
        this.userId = userId;
    }


    public enum Status {
        preparing, shipped, delivered, failed
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(UUID shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isConfirmedByDeliveryAgent() {
        return confirmedByDeliveryAgent;
    }

    public void setConfirmedByDeliveryAgent(boolean confirmedByDeliveryAgent) {
        this.confirmedByDeliveryAgent = confirmedByDeliveryAgent;
    }

    public LocalDateTime getShippedAt() {
        return shippedAt;
    }

    public void setShippedAt(LocalDateTime shippedAt) {
        this.shippedAt = shippedAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }
}

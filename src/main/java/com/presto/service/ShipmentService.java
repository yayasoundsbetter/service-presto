package com.presto.service;

import com.presto.model.Shipment;
import com.presto.notifications.AccentNotifier;
import com.presto.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShipmentService {

    private final AccentNotifier accentNotifier;
    private final ShipmentRepository repository;

    public ShipmentService(ShipmentRepository repository, AccentNotifier accentNotifier) {
        this.repository = repository;
        this.accentNotifier = accentNotifier;
    }


    public Shipment createShipment(Shipment s) {
        s.setStatus(Shipment.Status.preparing);
        Shipment saved = repository.save(s);
        accentNotifier.sendOrderBeingPrepared(saved);
        return saved;
    }

    public List<Shipment> getAllShipments() {
        return repository.findAll();
    }

    public Optional<Shipment> getShipmentById(UUID id) {
        return repository.findById(id);
    }

    public Optional<Shipment> getShipmentByOrderId(UUID orderId) {
        return repository.findByOrderId(orderId);
    }

    public Shipment confirmReception(UUID id, String trackingCode) {
        Shipment s = repository.findById(id).orElseThrow();
        s.setStatus(Shipment.Status.shipped);
        s.setTrackingCode(trackingCode);
        s.setShippedAt(LocalDateTime.now());
        Shipment saved = repository.save(s);
        accentNotifier.sendOrderShipped(saved);
        return saved;
    }

    public Shipment confirmDelivery(UUID id) {
        Shipment s = repository.findById(id).orElseThrow();
        s.setStatus(Shipment.Status.delivered);
        s.setDeliveredAt(LocalDateTime.now());
        Shipment saved = repository.save(s);
        accentNotifier.sendOrderDelivered(saved);
        return saved;
    }
}

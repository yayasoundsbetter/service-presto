package com.presto.repository;

import com.presto.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {
    Optional<Shipment> findByOrderId(UUID orderId);

    List<Shipment> findByUserId(UUID userId);

    List<Shipment> findByStatus(Shipment.Status status);

    List<Shipment> findByShippedAtBetween(LocalDateTime start, LocalDateTime end);

}

package com.presto.controller;

import com.presto.model.Shipment;
import com.presto.service.ShipmentService;
import com.presto.model.ShipmentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentService service;

    public ShipmentController(ShipmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Shipment> createShipment(@RequestBody ShipmentRequest request) {
        Shipment s = new Shipment();
        s.setOrderId(request.getOrderId());
        s.setUserId(request.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createShipment(s));
    }

    @GetMapping
    public List<Shipment> getAllShipments() {
        return service.getAllShipments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable UUID id) {
        return service.getShipmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Shipment> getShipmentByOrderId(@PathVariable UUID orderId) {
        return service.getShipmentByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/confirm-reception")
    public ResponseEntity<Shipment> confirmReception(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(service.confirmReception(id, body.get("trackingCode")));
    }

    @PutMapping("/{id}/confirm-delivery")
    public ResponseEntity<Shipment> confirmDelivery(@PathVariable UUID id) {
        return ResponseEntity.ok(service.confirmDelivery(id));
    }
}

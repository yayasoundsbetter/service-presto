package com.presto.notifications;

import com.presto.model.Shipment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AccentNotifier {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${accent.notify.url}")
    private String ACCENT_URL;


    public void sendOrderBeingPrepared(Shipment shipment) {
        Map<String, Object> data = Map.of(
                "orderNumber", shipment.getOrderId().toString(),
                "customerName", "Client inconnu",
                "email", "client@example.com",
                "paymentMethod", "Visa ending in 1234",
                "items", List.of(),
                "shipping", Map.of("cost", 12.99),
                "shippingAddress", Map.of(
                        "name", "John Doe",
                        "street", "123 Oak Street",
                        "city", "San Francisco",
                        "state", "CA",
                        "zip", "94102",
                        "country", "USA"
                )
        );
        sendNotification("orderBeingPrepared", data);
    }

    public void sendOrderShipped(Shipment shipment) {
        Map<String, Object> data = new HashMap<>();
        data.put("orderNumber", shipment.getOrderId().toString());

        if (shipment.getTrackingCode() != null) {
            data.put("trackingCode", shipment.getTrackingCode());
        }

        if (shipment.getShippedAt() != null) {
            data.put("shippedAt", shipment.getShippedAt().toString());
        }

        sendNotification("orderShipped", data);
    }


    public void sendOrderDelivered(Shipment shipment) {
        Map<String, Object> data = Map.of(
                "orderNumber", shipment.getOrderId().toString(),
                "deliveredAt", shipment.getDeliveredAt().toString()
        );
        sendNotification("orderDelivered", data);
    }

    private void sendNotification(String type, Map<String, Object> data) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("type", type);
        payload.put("data", data);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {
            System.out.println("Envoi de notification ACCENT : " + payload);
            restTemplate.postForEntity(ACCENT_URL, request, String.class);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi à ACCENT : " + e.getMessage());
            e.printStackTrace();

        }
    }
}

package ucan.reis_imobiliaria.modules.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ucan.reis_imobiliaria.modules.order.dto.OrderDTO;
import ucan.reis_imobiliaria.modules.order.entities.OrderEntity;
import ucan.reis_imobiliaria.modules.order.useCases.OrderUseCase;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderUseCase orderUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> findById(@PathVariable("id") UUID pkOrder) {
        OrderEntity order = orderUseCase.getOrderById(pkOrder);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/last")
    public ResponseEntity<OrderEntity> findLastOrder() {
        return orderUseCase.findLastOrder()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable("userId") UUID userId) {
        List<OrderEntity> orders = new ArrayList<OrderEntity>();

        orderUseCase.getOrdersByUserId(userId).forEach(orders::add);

        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<?> findByPropertyId(@PathVariable("propertyId") UUID propertyId) {
        List<OrderEntity> orders = new ArrayList<OrderEntity>();

        orderUseCase.getOrdersByPropertyId(propertyId).forEach(orders::add);

        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        List<OrderEntity> orders = new ArrayList<OrderEntity>();

        orderUseCase.getAllOrders().forEach(orders::add);

        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody OrderDTO orderDTO) {
        try {
            OrderEntity createdOrder = orderUseCase.createOrder(orderDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar a pedido: " + e.getMessage());
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable UUID orderId) {
        try {
            String response = orderUseCase.deleteOrderById(orderId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}

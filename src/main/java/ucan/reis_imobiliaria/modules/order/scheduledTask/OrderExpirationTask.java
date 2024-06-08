package ucan.reis_imobiliaria.modules.order.scheduledTask;

import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ucan.reis_imobiliaria.modules.order.OrderRepository;
import ucan.reis_imobiliaria.modules.order.entities.OrderEntity;

@Component
public class OrderExpirationTask {
    @Autowired
    private OrderRepository orderRepository;

    // Roda a cada minuto
    // @Scheduled(cron = "0 * * * * ?")
    @Scheduled(cron = "*/30 * * * * *")
    public void expiredOrders() {
        List<OrderEntity> ordersToExpire = orderRepository.findAll().stream()
                .filter(order -> order.getExpirationDate().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());

        orderRepository.deleteAll(ordersToExpire);

        System.out.println("CronJob Executando...");
    }
}

package ucan.reis_imobiliaria.modules.order.scheduledTask;

import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ucan.reis_imobiliaria.modules.order.OrderRepository;
import ucan.reis_imobiliaria.modules.order.entities.OrderEntity;
import ucan.reis_imobiliaria.modules.property.PropertyRepository;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;
import ucan.reis_imobiliaria.modules.property.utils.property.PropertyUtil.PropertyStatus;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderExpirationTask {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    // Roda a cada minuto
    // @Scheduled(cron = "0 * * * * ?")
    @Transactional
    @Scheduled(cron = "*/30 * * * * *")
    public void expiredOrders() {
        List<OrderEntity> ordersToExpire = orderRepository.findAll().stream()
                .filter(order -> order.getExpirationDate().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());

        for (OrderEntity order : ordersToExpire) {
            PropertyEntity property = order.getProperty();
            if (property != null) {
                property.setPropertyStatus(PropertyStatus.PUBLISHED);
                propertyRepository.save(property); // Atualiza o status da propriedade no banco de dados.
            }
        }

        orderRepository.deleteAll(ordersToExpire);

        System.out.println("CronJob Executando...");
    }
}

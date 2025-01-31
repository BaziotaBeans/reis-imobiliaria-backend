package ucan.reis_imobiliaria.modules.order.useCases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ucan.reis_imobiliaria.exceptions.ResourceNotFoundException;
import ucan.reis_imobiliaria.modules.order.OrderRepository;
import ucan.reis_imobiliaria.modules.order.dto.OrderDTO;
import ucan.reis_imobiliaria.modules.order.entities.OrderEntity;
import ucan.reis_imobiliaria.modules.order.utils.OrderUtil;
import ucan.reis_imobiliaria.modules.order.utils.OrderUtil.OrderState;
import ucan.reis_imobiliaria.modules.payment.utils.PaymentUtils.PaymentMethod;
import ucan.reis_imobiliaria.modules.property.PropertyRepository;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;
import ucan.reis_imobiliaria.modules.property.utils.property.PropertyUtil.PropertyStatus;
import ucan.reis_imobiliaria.modules.user.entities.User;
import ucan.reis_imobiliaria.modules.user.repository.UserRepository;

@Service
public class OrderUseCase {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Transactional
    public OrderEntity createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        PropertyEntity property = propertyRepository
        .findById(orderDTO.getPropertyId())
        .orElseThrow(() -> new RuntimeException("Imóvel não encontrado"));
        
        property.setPropertyStatus(PropertyStatus.STANDBY);

        propertyRepository.save(property);

        OrderEntity order = new OrderEntity();
        
        order.setUser(user);
        order.setProperty(property);
        order.setEntidade(orderDTO.getEntidade());
        if (orderDTO.getPaymentMethod() == PaymentMethod.MULTICAIXA_EXPRESS) {
            order.setExpirationDate(LocalDateTime.now().plusMinutes(15));
        }
        else order.setExpirationDate(LocalDateTime.now().plusMinutes(50));
        order.setReference(OrderUtil.generateRandomReference());
        order.setOrderState(OrderState.PENDING);
        order.setTotalValue(orderDTO.getTotalValue());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        return orderRepository.save(order);
    }

    @Transactional
    public String deleteOrderById(UUID orderId) {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new IllegalArgumentException("Pedido não encontrado");
        }

        OrderEntity order = optionalOrder.get();
        PropertyEntity property = order.getProperty();

        if (property != null) {
            property.setPropertyStatus(PropertyStatus.PUBLISHED);
            propertyRepository.save(property); // Atualiza o status da propriedade no banco de dados.
        }

        orderRepository.delete(order); // Remove a ordem específica.
        return "Pedido removido com sucesso!";
    }

    public OrderEntity getOrderById(UUID id) {
        return orderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Order with id = " + id));
    }

    public Optional<OrderEntity> findLastOrder() {
        return orderRepository.findLastOrder();
    }

    public List<OrderEntity> getOrdersByUserId(UUID userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<OrderEntity> getOrdersByPropertyId(UUID propertyId) {
        return orderRepository.findByPropertyId(propertyId);
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }
}

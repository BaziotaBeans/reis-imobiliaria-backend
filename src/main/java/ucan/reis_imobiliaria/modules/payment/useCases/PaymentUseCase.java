package ucan.reis_imobiliaria.modules.payment.useCases;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import ucan.reis_imobiliaria.modules.contract.ContractRepository;
import ucan.reis_imobiliaria.modules.contract.entities.ContractEntity;
import ucan.reis_imobiliaria.modules.contract.utils.ContractUtils.ContractStatus;
import ucan.reis_imobiliaria.modules.order.OrderRepository;
import ucan.reis_imobiliaria.modules.order.entities.OrderEntity;
import ucan.reis_imobiliaria.modules.payment.PaymentRepository;
import ucan.reis_imobiliaria.modules.payment.dto.PaymentDTO;
import ucan.reis_imobiliaria.modules.payment.entities.PaymentEntity;
import ucan.reis_imobiliaria.modules.payment.utils.PaymentUtils;
import ucan.reis_imobiliaria.modules.property.PropertyRepository;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;
import ucan.reis_imobiliaria.modules.property.utils.property.PropertyUtil.PropertyStatus;

@Service
public class PaymentUseCase {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Transactional
    public void processPayment(PaymentDTO paymentDTO) { 
        // Encontra o OrderEntity usando a reference
        OrderEntity orderEntity = orderRepository.findByReference(paymentDTO.getReference())
            .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        // Atualiza o status da propriedade para RENTED
        PropertyEntity propertyEntity = orderEntity.getProperty();
        propertyEntity.setPropertyStatus(PropertyStatus.RENTED);
        propertyRepository.save(propertyEntity);

        // Cria e salva PaymentEntity
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setProperty(orderEntity.getProperty());
        paymentEntity.setUser(orderEntity.getUser());
        paymentEntity.setTotalValue(paymentDTO.getTotalValue());
        paymentEntity.setReference(paymentDTO.getReference());
        paymentEntity.setCreatedAt(LocalDateTime.now());
        paymentEntity.setPaymentMethod(paymentDTO.getPaymentMethod());
        paymentRepository.save(paymentEntity);

        // Remover o OrderEntity
        orderRepository.delete(orderEntity);

        // Cria e salva ContractEntity
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setProperty(paymentEntity.getProperty());
        contractEntity.setUser(paymentEntity.getUser());
        contractEntity.setStartDate(paymentEntity.getCreatedAt());
        contractEntity.setEndDate(PaymentUtils.calculateEndDate(propertyEntity.getPaymentModality()));
        contractEntity.setCreatedAt(LocalDateTime.now());
        contractEntity.setContractStatus(ContractStatus.PENDING);
        contractRepository.save(contractEntity);
    }

    public Optional<PaymentEntity> findLastPayment() {
        return paymentRepository.findLastPayment();
    }
}

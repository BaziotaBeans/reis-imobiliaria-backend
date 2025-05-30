package ucan.reis_imobiliaria.modules.schedulingPaymentEntity.useCases;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucan.reis_imobiliaria.modules.property.PropertyRepository;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;
import ucan.reis_imobiliaria.modules.property.entities.PropertyScheduleEntity;
import ucan.reis_imobiliaria.modules.scheduling.SchedulingRepository;
import ucan.reis_imobiliaria.modules.scheduling.entities.SchedulingEntity;
import ucan.reis_imobiliaria.modules.schedulingPaymentEntity.SchedulingPaymentRepository;
import ucan.reis_imobiliaria.modules.schedulingPaymentEntity.dto.PaymentRequestDTO;
import ucan.reis_imobiliaria.modules.schedulingPaymentEntity.entities.SchedulingPaymentEntity;
import ucan.reis_imobiliaria.modules.user.entities.User;
import ucan.reis_imobiliaria.modules.user.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class SchedulingPaymentUseCase {
    @Autowired
    private SchedulingPaymentRepository schedulingPaymentRepository;

    @Autowired
    private SchedulingPaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private SchedulingRepository schedulingRepository;

    public List<SchedulingPaymentEntity> findAllPayments() {
        return paymentRepository.findAll();
    }

    // 2. Listar pagamentos por usuário
    public List<SchedulingPaymentEntity> findPaymentsByUser(UUID userId) {
        return paymentRepository.findByUserPkUser(userId);
    }

    public SchedulingPaymentEntity findLastPayment() {
        return paymentRepository.findTopByOrderByCreatedAtDesc()
                .orElseThrow(() -> new RuntimeException("Nenhum pagamento encontrado"));
    }

    public SchedulingPaymentEntity findPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }

    public List<SchedulingPaymentEntity> findPaymentsByCompanyUser(UUID userId) {
        return schedulingPaymentRepository.findByCompanyUserId(userId);
    }

    @Transactional
    public SchedulingPaymentEntity createPayment(PaymentRequestDTO paymentRequest) {
        // Validar o usuário
        User user = userRepository.findById(paymentRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Validar o imóvel
        PropertyEntity property = propertyRepository.findById(paymentRequest.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado"));

        // Validar o agendamento
        SchedulingEntity scheduling = schedulingRepository.findById(paymentRequest.getSchedulingId())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        // Recuperar o schedule relacionado
        PropertyScheduleEntity propertySchedule = scheduling.getPropertySchedule();
        if (propertySchedule == null) {
            throw new RuntimeException("PropertySchedule não encontrado para o agendamento fornecido");
        }

        // Criar o pagamento
        SchedulingPaymentEntity payment = new SchedulingPaymentEntity();
        payment.setUser(user);
        payment.setProperty(property);
        payment.setScheduledDate(scheduling.getScheduledDate());
        payment.setScheduleDetails(String.format(
                "Data: %s, Dia da Semana: %s, Horário: %s - %s",
                scheduling.getScheduledDate(),
                propertySchedule.getDayOfWeek(),
                propertySchedule.getStartTime(),
                propertySchedule.getEndTime()
        ));
        payment.setTotalValue(paymentRequest.getTotalValue());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setReference(paymentRequest.getReference());
        payment.setCreatedAt(java.time.LocalDateTime.now());

        // Salvar o pagamento
        return paymentRepository.save(payment);
    }
}
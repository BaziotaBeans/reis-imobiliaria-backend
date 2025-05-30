package ucan.reis_imobiliaria.modules.scheduling.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ucan.reis_imobiliaria.exceptions.ResourceNotFoundException;
import ucan.reis_imobiliaria.modules.property.PropertyRepository;
import ucan.reis_imobiliaria.modules.property.PropertyScheduleRepository;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;
import ucan.reis_imobiliaria.modules.property.entities.PropertyScheduleEntity;
import ucan.reis_imobiliaria.modules.scheduling.SchedulingRepository;
import ucan.reis_imobiliaria.modules.scheduling.entities.SchedulingEntity;
import ucan.reis_imobiliaria.modules.schedulingPaymentEntity.SchedulingPaymentRepository;
import ucan.reis_imobiliaria.modules.schedulingPaymentEntity.entities.SchedulingPaymentEntity;
import ucan.reis_imobiliaria.modules.user.entities.User;
import ucan.reis_imobiliaria.modules.user.repository.UserRepository;

import static ucan.reis_imobiliaria.modules.scheduling.utils.SchedulingUtil.calculateNextDate;

@Service
public class SchedulingUseCase {

    @Autowired
    private SchedulingRepository schedulerRepository;

    @Autowired
    private PropertyScheduleRepository propertyScheduleRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchedulingPaymentRepository schedulingPaymentRepository;


    public List<SchedulingEntity> findAll() {
        return schedulerRepository.findAll();
    }

    public Optional<SchedulingEntity> createScheduling(UUID pkPropertySchedule, UUID pkProperty) {

        PropertyScheduleEntity propertySchedule = propertyScheduleRepository.findById(pkPropertySchedule)
                .orElseThrow(() -> new RuntimeException("PropertySchedule not found"));

        LocalDate scheduledDate = calculateNextDate(propertySchedule.getDayOfWeek(), propertySchedule.getStartTime());

        // Verifica se já existe agendamento
        boolean exists = schedulerRepository.existsByPropertyScheduleIdAndScheduledDate(
                pkPropertySchedule,
                scheduledDate
        );

        if (exists) {
            return Optional.empty();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        PropertyEntity property = propertyRepository.findById(pkProperty)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        // Atualiza o status do PropertySchedule para UNAVAILABLE
        propertySchedule.setStatus("UNAVAILABLE");
        propertyScheduleRepository.save(propertySchedule);

        SchedulingEntity scheduling = new SchedulingEntity();
        scheduling.setPropertySchedule(propertySchedule);
        scheduling.setProperty(property);
        scheduling.setUser(user);
        scheduling.setScheduledDate(scheduledDate);

        return Optional.of(schedulerRepository.save(scheduling));
    }

    public List<SchedulingEntity> findByUserPkUser(UUID pkUser) {
        return schedulerRepository.findByUserPkUser(pkUser);
    }

    public List<SchedulingEntity> findByCompanyId(UUID companyId) {
        return schedulerRepository.findByCompany(companyId);
    }

    public SchedulingEntity findLastScheduling() {
        return schedulerRepository.findLastScheduling();
    }

    @Transactional
    public void delete(UUID pkScheduling) {
        Optional<SchedulingEntity> schedulingOptional = schedulerRepository.findById(pkScheduling);
        if (schedulingOptional.isPresent()) {
            SchedulingEntity scheduling = schedulingOptional.get();

            // Recuperar o schedule relacionado
            PropertyScheduleEntity propertySchedule = scheduling.getPropertySchedule();
            if (propertySchedule == null) {
                throw new RuntimeException("PropertySchedule não encontrado para o agendamento fornecido");
            }

            // Buscar todos os pagamentos que possam estar relacionados ao agendamento
            List<SchedulingPaymentEntity> payments = schedulingPaymentRepository.findByScheduledDateAndPropertyAndScheduleDetails(
                    scheduling.getScheduledDate(),
                    scheduling.getProperty(),
                    String.format(
                            "Data: %s, Dia da Semana: %s, Horário: %s - %s",
                            scheduling.getScheduledDate(),
                            propertySchedule.getDayOfWeek(),
                            propertySchedule.getStartTime(),
                            propertySchedule.getEndTime()
                    )
            );

            // Remover todos os pagamentos relacionados
            payments.forEach(schedulingPaymentRepository::delete);

            // Atualiza o status do PropertySchedule para AVAILABLE
            // PropertyScheduleEntity propertySchedule = scheduling.getPropertySchedule();
            propertySchedule.setStatus("AVAILABLE");
            propertyScheduleRepository.save(propertySchedule);

            // Remove o agendamento
            schedulerRepository.delete(scheduling);
        } else {
            throw new RuntimeException("Agendamento não encontrado");
        }
    }

}

// 322ccf00-26b1-43a7-9bfb-a799ebd771fb/
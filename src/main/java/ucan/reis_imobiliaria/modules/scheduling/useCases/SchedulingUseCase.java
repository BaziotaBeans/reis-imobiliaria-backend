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
import ucan.reis_imobiliaria.modules.user.entities.User;
import ucan.reis_imobiliaria.modules.user.repository.UserRepository;

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

    public Optional<SchedulingEntity> createScheduling(UUID pkPropertySchedule, UUID pkProperty) {

        boolean exists = schedulerRepository.existsByPropertyScheduleIdAndCreatedAt(pkPropertySchedule,
                LocalDate.now());

        if (exists) {
            return Optional.empty();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Optional<User> userOptional = userRepository.findByUsername(username);

        User user = userOptional.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PropertyScheduleEntity propertySchedule = propertyScheduleRepository.findById(pkPropertySchedule)
                .orElseThrow(() -> new RuntimeException("PropertySchedule not found"));
        PropertyEntity property = propertyRepository.findById(pkProperty)
                .orElseThrow(() -> new RuntimeException("Property not found")); // Assumindo que você tem esse
                                                                                // repositório

        SchedulingEntity scheduling = new SchedulingEntity();
        scheduling.setPropertySchedule(propertySchedule);
        scheduling.setProperty(property);
        scheduling.setUser(user);

        return Optional.of(schedulerRepository.save(scheduling));
    }

    public List<SchedulingEntity> findByUserPkUser(UUID pkUser) {
        return schedulerRepository.findByUserPkUser(pkUser);
    }

    public List<SchedulingEntity> findByCompanyId(UUID companyId) {
        return schedulerRepository.findByCompany(companyId);
    }

    @Transactional
    public void delete(UUID pkScheduling) {
        Optional<SchedulingEntity> schedulingOptional = schedulerRepository.findById(pkScheduling);

        schedulerRepository.delete(schedulingOptional.get());
    }

}

// 322ccf00-26b1-43a7-9bfb-a799ebd771fb/
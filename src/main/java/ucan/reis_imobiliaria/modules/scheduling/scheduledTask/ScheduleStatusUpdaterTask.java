package ucan.reis_imobiliaria.modules.scheduling.scheduledTask;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ucan.reis_imobiliaria.modules.property.PropertyScheduleRepository;
import ucan.reis_imobiliaria.modules.property.entities.PropertyScheduleEntity;
import ucan.reis_imobiliaria.modules.scheduling.SchedulingRepository;
import ucan.reis_imobiliaria.modules.scheduling.entities.SchedulingEntity;

import java.time.LocalDate;
import java.util.List;

@Component
public class ScheduleStatusUpdaterTask {

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private PropertyScheduleRepository propertyScheduleRepository;

    // Executa todos os dias à meia-noite
    //@Scheduled(cron = "0 0 0 * * *") // Formato: segundo, minuto, hora, dia, mês, dia da semana
    @Scheduled(cron = "*/30 * * * * *")
    @Transactional
    public void updateExpiredSchedules() {
        // Busca todos os agendamentos cuja data exata já passou
        List<SchedulingEntity> expiredSchedules = schedulingRepository.findAllByScheduledDateBefore(LocalDate.now());

        for (SchedulingEntity scheduling : expiredSchedules) {
            PropertyScheduleEntity propertySchedule = scheduling.getPropertySchedule();

            // Atualiza o status do PropertySchedule para 'AVAILABLE'
            propertySchedule.setStatus("AVAILABLE");
            propertyScheduleRepository.save(propertySchedule);

            // Remove o agendamento expirado (se não for necessário manter no banco)
            //schedulingRepository.delete(scheduling);
        }

        System.out.println("CronJob Executando AGENDAMENTO...");
    }
}

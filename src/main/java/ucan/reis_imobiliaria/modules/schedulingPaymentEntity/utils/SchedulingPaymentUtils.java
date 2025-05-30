package ucan.reis_imobiliaria.modules.schedulingPaymentEntity.utils;

import ucan.reis_imobiliaria.modules.scheduling.entities.SchedulingEntity;

public class SchedulingPaymentUtils {
    static public String generateScheduleDetails(SchedulingEntity scheduling) {
        return String.format("Agendamento: %s, Nota: %s",
                scheduling.getScheduledDate(),
                scheduling.getNote() != null ? scheduling.getNote() : "Sem nota");
    }

    static public Double calculateTotalValue(SchedulingEntity scheduling) {
        // Regra de negócio para calcular o valor (ex.: baseado no imóvel ou agendamento)
        return 100.0; // Exemplo: valor fixo ou baseado no contexto
    }

}

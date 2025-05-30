package ucan.reis_imobiliaria.modules.scheduling.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class SchedulingUtil {
    static public LocalDate calculateNextDate(DayOfWeek scheduledDay, LocalTime scheduledStartTime) {
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        DayOfWeek currentDay = today.getDayOfWeek();

        // Se for o mesmo dia da semana
        if (currentDay == scheduledDay) {
            // Se o horário agendado ainda não passou, pode ser hoje
            if (scheduledStartTime.isAfter(currentTime)) {
                return today;
            }
            // Se o horário já passou, agenda para próxima semana
            return today.plusWeeks(1);
        }

        // Calcula dias até o próximo dia desejado
        int daysUntilNext = scheduledDay.getValue() - currentDay.getValue();
        if (daysUntilNext <= 0) {
            daysUntilNext += 7;
        }

        return today.plusDays(daysUntilNext);
    }
}

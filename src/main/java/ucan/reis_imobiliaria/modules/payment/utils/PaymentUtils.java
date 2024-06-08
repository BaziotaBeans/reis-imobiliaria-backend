package ucan.reis_imobiliaria.modules.payment.utils;

import java.time.LocalDateTime;

public class PaymentUtils {
    static public LocalDateTime calculateEndDate(String paymentModality) {
        LocalDateTime endDate = LocalDateTime.now();
        switch (paymentModality) {
            case "Mensal":
                endDate = endDate.plusMonths(1);
                break;
            case "Trimestral":
                endDate = endDate.plusMonths(3);
                break;
            case "Semestral":
                endDate = endDate.plusMonths(6);
                break;
            case "Anual":
                endDate = endDate.plusYears(1);
                break;
            default:
                // Se nulo ou vazio, não define endDate
                return null;
        }
        return endDate;
    }
}

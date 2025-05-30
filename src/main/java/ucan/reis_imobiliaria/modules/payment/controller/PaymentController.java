package ucan.reis_imobiliaria.modules.payment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import ucan.reis_imobiliaria.modules.payment.PaymentRepository;
import ucan.reis_imobiliaria.modules.payment.dto.PaymentDTO;
import ucan.reis_imobiliaria.modules.payment.entities.PaymentEntity;
import ucan.reis_imobiliaria.modules.payment.useCases.PaymentUseCase;
import ucan.reis_imobiliaria.modules.schedulingPaymentEntity.entities.SchedulingPaymentEntity;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    @Autowired
    private PaymentUseCase paymentUseCase;

    @Autowired
    private PaymentRepository paymentRepository;

    // Endpoint para criar um novo pagamento
    @PostMapping("/")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            paymentUseCase.processPayment(paymentDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        List<PaymentEntity> payments = new ArrayList<PaymentEntity>();

        paymentRepository.findAll().forEach(payments::add);

        if (payments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    ///api/payments/by-reference?reference=valor

    @GetMapping("/by-reference")
    public ResponseEntity<?> findByReference(@RequestParam("reference") String reference) {
        Optional<PaymentEntity> payment = paymentRepository.findByReference(reference);

        if (payment.isPresent()) {
            return new ResponseEntity<>(payment.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Payment with reference " + reference + " not found");
        }
    }

    @GetMapping("/last")
    public ResponseEntity<PaymentEntity> findLastPayment() {
        return paymentUseCase.findLastPayment().map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/company-user-associated/{userId}")
    public ResponseEntity<?> getPaymentsAssociatedWithUser(@PathVariable UUID userId) {
        if (userId == null) {
            return new ResponseEntity<>("User ID cannot be null", HttpStatus.BAD_REQUEST);
        }

        try {
            List<PaymentEntity> payments = paymentUseCase.findPaymentsByCompanyUser(userId);
            return payments.isEmpty()
                    ? new ResponseEntity<>("No payments found", HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

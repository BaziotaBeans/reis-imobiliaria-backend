package ucan.reis_imobiliaria.modules.schedulingPaymentEntity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ucan.reis_imobiliaria.modules.schedulingPaymentEntity.dto.PaymentRequestDTO;
import ucan.reis_imobiliaria.modules.schedulingPaymentEntity.entities.SchedulingPaymentEntity;
import ucan.reis_imobiliaria.modules.schedulingPaymentEntity.useCases.SchedulingPaymentUseCase;
import ucan.reis_imobiliaria.modules.user.entities.User;
import ucan.reis_imobiliaria.modules.user.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/scheduling-payments")
public class SchedulingPaymentController {

    @Autowired
    SchedulingPaymentUseCase schedulingPaymentUseCase;

    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<SchedulingPaymentEntity>> getAllPayments() {
        List<SchedulingPaymentEntity> payments = schedulingPaymentUseCase.findAllPayments();
        return payments.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        try {
            SchedulingPaymentEntity payment = schedulingPaymentUseCase.createPayment(paymentRequest);
            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/last")
    public ResponseEntity<?> getLastPayment() {
        try {
            SchedulingPaymentEntity lastPayment = schedulingPaymentUseCase.findLastPayment();
            return new ResponseEntity<>(lastPayment, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPaymentById(@PathVariable UUID paymentId) {
        try {
            SchedulingPaymentEntity payment = schedulingPaymentUseCase.findPaymentById(paymentId);
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/company-user-associated/{userId}")
//    public ResponseEntity<?> getPaymentsAssociatedWithUser(@PathVariable UUID userId) {
//        try {
//            List<SchedulingPaymentEntity> payments = schedulingPaymentUseCase.findPaymentsByUser(userId);
//            return payments.isEmpty()
//                    ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
//                    : new ResponseEntity<>(payments, HttpStatus.OK);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/company-user-associated/{userId}")
    public ResponseEntity<?> getPaymentsAssociatedWithUser(@PathVariable UUID userId) {
        if (userId == null) {
            return new ResponseEntity<>("User ID cannot be null", HttpStatus.BAD_REQUEST);
        }

        try {
            List<SchedulingPaymentEntity> payments = schedulingPaymentUseCase.findPaymentsByCompanyUser(userId);
            return payments.isEmpty()
                    ? new ResponseEntity<>("No payments found", HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package ucan.reis_imobiliaria.modules.scheduling.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ucan.reis_imobiliaria.modules.scheduling.entities.SchedulingEntity;
import ucan.reis_imobiliaria.modules.scheduling.useCases.SchedulingUseCase;

@RestController
@RequestMapping("/api/scheduling")
public class SchedulingController {

    @Autowired
    SchedulingUseCase schedulingUseCase;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{pkPropertySchedule}/{pkProperty}")
    public ResponseEntity<?> createScheduling(@PathVariable UUID pkPropertySchedule, @PathVariable UUID pkProperty) {
        Optional<SchedulingEntity> scheduling = schedulingUseCase.createScheduling(pkPropertySchedule, pkProperty);

        if (!scheduling.isPresent()) {
            return ResponseEntity.badRequest().body("Agendamento já existe para este PropertySchedule no dia de hoje.");
        }
        return ResponseEntity.ok(scheduling.get());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/findByUserId/{id}")
    public ResponseEntity<?> findByUserPkUser(@PathVariable("id") UUID pkUser) {
        List<SchedulingEntity> schedulings = new ArrayList<SchedulingEntity>();

        schedulingUseCase.findByUserPkUser(pkUser).forEach(schedulings::add);

        if (schedulings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(schedulings, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('COMPANY')")
    @GetMapping("/findByCompany/{id}")
    public ResponseEntity<?> findByCompanyId(@PathVariable("id") UUID pkCompany) {
        List<SchedulingEntity> schedulings = new ArrayList<SchedulingEntity>();

        schedulingUseCase.findByCompanyId(pkCompany).forEach(schedulings::add);

        if (schedulings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(schedulings, HttpStatus.OK);
    }

    @DeleteMapping("/{pkScheduling}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> delete(@PathVariable UUID pkScheduling) {
        try {
            
            schedulingUseCase.delete(pkScheduling);

            return ResponseEntity.ok("Agendamento removido com sucesso");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao remover o agendamento \n" + e.getMessage());
        }
    }
}

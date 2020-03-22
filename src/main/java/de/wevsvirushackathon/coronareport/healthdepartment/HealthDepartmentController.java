package de.wevsvirushackathon.coronareport.healthdepartment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/healthDepartments")
public class HealthDepartmentController {

    private HealthDepartmentRepository healthDepartmentRepository;

    public HealthDepartmentController(HealthDepartmentRepository healthDepartmentRepository) {
        this.healthDepartmentRepository = healthDepartmentRepository;
    }

    @GetMapping
    public ResponseEntity<List<HealthDepartmentDTO>> getHealthDepartments() {
        Iterable<HealthDepartment> all = this.healthDepartmentRepository.findAll();
        List<HealthDepartmentDTO> dtoList = StreamSupport.stream(all.spliterator(), false).map(h -> {
            HealthDepartmentDTO healthDepartmentDTO = new HealthDepartmentDTO();
            healthDepartmentDTO.fullName = h.getFullName();
            healthDepartmentDTO.id = h.getId();
            return healthDepartmentDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    class HealthDepartmentDTO {
        String fullName;
        String id;
    }

}

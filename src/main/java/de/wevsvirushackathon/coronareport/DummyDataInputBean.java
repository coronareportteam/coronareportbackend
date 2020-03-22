package de.wevsvirushackathon.coronareport;

import de.wevsvirushackathon.coronareport.healthdepartment.HealthDepartment;
import de.wevsvirushackathon.coronareport.healthdepartment.HealthDepartmentRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Component
public class DummyDataInputBean implements ApplicationListener<ContextRefreshedEvent> {

    private HealthDepartmentRepository healthDepartmentRepository;

    public DummyDataInputBean(HealthDepartmentRepository healthDepartmentRepository) {
        this.healthDepartmentRepository = healthDepartmentRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<HealthDepartment> departmentList = new LinkedList<>();
        HealthDepartment fk = new HealthDepartment();
        fk.setFullName("Gesundheitsamt Friedrichshain-Kreuzberg");
        fk.setId("Friedrichshain-Kreuzberg");
        fk.setPassCode(UUID.fromString("aba0ec65-6c1d-4b7b-91b4-c31ef16ad0a2"));
        departmentList.add(fk);
        HealthDepartment s = new HealthDepartment();
        s.setFullName("Gesundheitsamt Spandau");
        s.setId("Spandau");
        s.setPassCode(UUID.fromString("ca3f3e9a-414a-4117-a623-59b109b269f1"));
        departmentList.add(s);
        this.healthDepartmentRepository.saveAll(departmentList);
    }

}

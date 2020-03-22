package de.wevsvirushackathon.coronareport;

import de.wevsvirushackathon.coronareport.healthdepartment.HealthDepartment;
import de.wevsvirushackathon.coronareport.healthdepartment.HealthDepartmentRepository;
import de.wevsvirushackathon.coronareport.user.ClientService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Component
public class DummyDataInputBean implements ApplicationListener<ContextRefreshedEvent> {

    public static int counter;

    private ClientService clientService;
    private HealthDepartmentRepository healthDepartmentRepository;

    public DummyDataInputBean(ClientService clientService, HealthDepartmentRepository healthDepartmentRepository) {
        this.clientService = clientService;
        this.healthDepartmentRepository = healthDepartmentRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<HealthDepartment> departmentList = new LinkedList<>();
        HealthDepartment fk = new HealthDepartment();
        fk.setFullName("Gesundheitsamt Friedrichshain-Kreuzberg");
        fk.setId("Friedrichshain-Kreuzberg");
        fk.setPassCode(UUID.randomUUID());
        departmentList.add(fk);
        HealthDepartment s = new HealthDepartment();
        s.setFullName("Gesundheitsamt Spandau");
        s.setId("Spandau");
        s.setPassCode(UUID.randomUUID());
        departmentList.add(s);
        this.healthDepartmentRepository.saveAll(departmentList);
    }

}

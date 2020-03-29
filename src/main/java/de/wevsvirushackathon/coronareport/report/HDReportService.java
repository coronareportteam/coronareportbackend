package de.wevsvirushackathon.coronareport.report;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wevsvirushackathon.coronareport.diary.DiaryEntry;
import de.wevsvirushackathon.coronareport.diary.DiaryEntryRepository;
import de.wevsvirushackathon.coronareport.client.Client;
import de.wevsvirushackathon.coronareport.client.ClientRepository;

@Service
public class HDReportService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private ClientRepository clientRepository;
	@Autowired
	private DiaryEntryRepository diaryRepository;

    @Autowired
    public HDReportService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<HDClient> getClientsByHDId(String healthDepartmentId) {
    	
        List<Client> clients = this.clientRepository.findAllByHealthDepartmentId(healthDepartmentId);
        
        if(clients == null) {
        	return new ArrayList<>();
        }
        
        List<HDClient> hdClients = clients.stream().map( client -> {
        	
        	HDClient hdClient = modelMapper.map(client, HDClient.class);
        	

        	
        	List<DiaryEntry> diaryEntries =  diaryRepository.findAllByClientOrderByDateTimeDesc(client);
        	hdClient.setDiaryEntires(diaryEntries);
        
        	if(!diaryEntries.isEmpty()) {
      
        		if(diaryEntries.get(0).getDateTime() != null) {
        			hdClient.setDateTimeOfLastReport(diaryEntries.get(0).getDateTime().toLocalDateTime());
        		}
        		
        		hdClient.setCurrentBodyTemperature(diaryEntries.get(0).getBodyTemperature());
        		
        	}
        	
        	determineStatus(hdClient);
        	

        	
        	
        	return hdClient;
        
        	
        }).collect(Collectors.toList());
        
        return hdClients;
    }

	private void determineStatus(HDClient hdClient) {
		
		if(hdClient.getCurrentBodyTemperature() > 39.0) {
        	hdClient.getMonitoringStatus().add(MonitoringStatus.CHECK_HEALTH);
        	hdClient.setMonitoringMessage(MonitoringStatus.CHECK_HEALTH.getMessage());
		}
		
//		if(hdClient.getDateTimeOfLastReport()!= null && hdClient.) {
//			
//		}
		
		if(hdClient.getMonitoringStatus().isEmpty()) {
			hdClient.getMonitoringStatus().add(MonitoringStatus.OK);
			hdClient.setMonitoringMessage(MonitoringStatus.OK.getMessage());
		}
	}

}

package de.wevsvirushackathon.coronareport.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository
        extends CrudRepository<Client, Long> {
	
		public Client findByClientCode(String clientCode);
		
		
}
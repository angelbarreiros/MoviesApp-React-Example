package es.udc.paproject.backend.model.entities;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends PagingAndSortingRepository<User, Long> {
	
	boolean existsByUserName(String userName);

	Optional<User> findByUserName(String userName);
	
}

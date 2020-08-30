package tn.elan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import tn.elan.model.Medias;

@Repository
public interface MediasRepository extends JpaRepository<Medias, Long> {
	
	
	@Query(nativeQuery = true,
			   value = "select * from Medias m where m.type='evenement'")List<Medias> listevenement();
	@Query(nativeQuery = true,
			   value = "select * from Medias m where m.type='atelier'")List<Medias> listatelier();
	
	@Query(nativeQuery = true,
			   value = "select * from Medias m where m.type='press'")List<Medias> listpress();
	

	

}

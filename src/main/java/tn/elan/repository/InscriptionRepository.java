package tn.elan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.elan.model.Inscription;
import tn.elan.model.Statistique;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long>{
	@Query(nativeQuery = true,
			   value = "select * from Inscription i order by i.date_inscription desc limit 1")Inscription findInscription();
}

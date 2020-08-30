package tn.elan.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import tn.elan.model.Statistique;
@Repository
public interface StatistiqueRepository extends JpaRepository<Statistique, Long>{
	@Query(nativeQuery = true,
			   value = "select * from Statistique s order by s.date_statistique desc limit 1")Statistique findstatistique();
}

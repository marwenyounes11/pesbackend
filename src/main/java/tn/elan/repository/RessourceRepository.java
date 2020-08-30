package tn.elan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.elan.model.Ressource;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource, Long>{
	@Query(nativeQuery = true,
			   value = "select * from Ressource r where r.type='coach'")List<Ressource> listcoach();
	
	@Query(nativeQuery = true,
			   value = "select * from Ressource r where r.type='ctp'")List<Ressource> listctp();

	@Query(nativeQuery = true,
			   value = "select * from Ressource r where r.type='manager'")List<Ressource> listmanager();

	Ressource findByCv(String cv);
}

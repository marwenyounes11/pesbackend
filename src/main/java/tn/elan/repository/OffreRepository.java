package tn.elan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.elan.model.Offre;
@Repository
public interface OffreRepository extends JpaRepository<Offre, Long>{

}

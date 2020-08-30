package tn.elan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.elan.model.Candidat;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Long>{

}

package tn.elan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.elan.model.Candidature;

@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Long>{

}

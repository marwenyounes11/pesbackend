package tn.elan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.elan.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{

}

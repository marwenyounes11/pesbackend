package tn.elan.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.elan.model.Inscription;

import tn.elan.repository.InscriptionRepository;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class InscriptionController {
	@Autowired
	InscriptionRepository repository;
	@PostMapping("/inscription")
	public Inscription inscrire( @RequestBody Inscription inscription) {
		LocalDateTime date = LocalDateTime.now();
		inscription.setDateInscription(date);
		return repository.save(inscription);
	}
	
	@GetMapping("/inscriptions/lastdate")
	  public Inscription getInscriptionByDate() {
	    System.out.println("Get  last Inscription ...");
	 
	    Inscription inscription = repository.findInscription();
	    
	 
	    return inscription;
	  }
	

}

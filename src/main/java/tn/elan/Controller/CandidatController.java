package tn.elan.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import tn.elan.domaine.Response;
import tn.elan.exception.ResourceNotFoundException;
import tn.elan.model.Candidat;

import tn.elan.repository.CandidatRepository;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class CandidatController {
	@Autowired 	CandidatRepository  repository;
	 @GetMapping("/candidats/{id}")
	 public ResponseEntity<Candidat> getCandidatById(@PathVariable(value = "id") Long Id)
				throws ResourceNotFoundException {
		 Candidat candidat = repository.findById(Id)
					.orElseThrow(() -> new ResourceNotFoundException("Candidat not found for this id :: " + Id));
			return ResponseEntity.ok().body(candidat);
		}
	 
	 @GetMapping("/candidats")
	  public List<Candidat> getAllCandidats() {
	     System.out.println("Get all Candidats...");
	 
	    List<Candidat> candidats = new ArrayList<>();
	    repository.findAll().forEach(candidats::add);
	 
	    return candidats;
	  }
	 
	 @PostMapping("/candidats")
		public ResponseEntity<Response> createCandidat( @RequestBody Candidat candidat){
		 Candidat candid =new Candidat();
		 
			 candid.setCandidature(candidat.getCandidature());
			 candid.setContact(candidat.getContact());
		Candidat cand = repository.save(candid);

		if (cand != null)
		{
			return new ResponseEntity<Response>(new Response (""),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Response>(new Response ("Candidat not saved"),HttpStatus.BAD_REQUEST);	
		}

		}
	 

		@DeleteMapping("/candidats/{id}")
		public Map<String, Boolean> deleteCandidat(@PathVariable(value = "id") Long candidatId)
				throws ResourceNotFoundException {
			Candidat candidat = repository.findById(candidatId)
					.orElseThrow(() -> new ResourceNotFoundException("Candidat not found  id :: " + candidatId));
			repository.delete(candidat);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return response;
		}

		@DeleteMapping("/candidats/delete")
		public ResponseEntity<String> deleteCandidats() {
		  System.out.println("Delete All Candidats...");
		  repository.deleteAll();
		  return new ResponseEntity<>("All Candidats have been deleted!", HttpStatus.OK);
		}


		@PutMapping("/candidats/{id}")
		public ResponseEntity<Candidat> updateCandidat(@PathVariable("id") long id, @RequestBody Candidat candidat) {
		  System.out.println("Maj Candidat avec ID = " + id + "...");
		  Optional<Candidat> candidatInfo = repository.findById(id);
		  if (candidatInfo.isPresent()) {
		  	Candidat cand = candidatInfo.get();
		     cand.setContact(candidat.getContact());
		     cand.setCandidature(candidat.getCandidature());
		     cand.setEmail(candidat.getEmail());
		     cand.setNom(candidat.getNom());
		     cand.setPrenom(candidat.getPrenom());
		     cand.setTelephone(candidat.getTelephone());
		         
		    return new ResponseEntity<>(repository.save(cand), HttpStatus.OK);
		  } else {
		    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }
		}
}

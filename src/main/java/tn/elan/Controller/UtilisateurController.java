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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.elan.exception.ResourceNotFoundException;
import tn.elan.model.Utilisateur;
import tn.elan.repository.UtilisateurRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UtilisateurController {
	@Autowired
	UtilisateurRepository repository;
	
	 @GetMapping("/users")
	  public List<Utilisateur> getAllUtilisateur() {
	    System.out.println("Get all Utilisateur...");
	 
	    List<Utilisateur> Utilisateur = new ArrayList<>();
	    repository.findAll().forEach(Utilisateur::add);
	 
	    return Utilisateur;
	  }
	
	@GetMapping("/users/{id}")
	public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable(value = "id") Long UtilisateurId)
			throws ResourceNotFoundException {
		Utilisateur Utilisateur = repository.findById(UtilisateurId)
				.orElseThrow(() -> new ResourceNotFoundException("Utilisateur not found for this id :: " + UtilisateurId));
		return ResponseEntity.ok().body(Utilisateur);
	}

	 
	 @GetMapping("/users/5/{login}")
	  public   ResponseEntity<Utilisateur> getUtilisateurByLogin(@PathVariable String login) 
		  throws ResourceNotFoundException {
		  Utilisateur Utilisateur = repository.findByLogin(login)
				  .orElseThrow(() -> new ResourceNotFoundException("Usernot found for this login : "));
		   return ResponseEntity.ok().body(Utilisateur);
	  } 
	
	@PostMapping("/users")
	public Utilisateur createUtilisateur( @RequestBody Utilisateur Utilisateur) {
		return repository.save(Utilisateur);
	}
	

	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUtilisateur(@PathVariable(value = "id") Long UtilisateurId)
			throws ResourceNotFoundException {
		Utilisateur Utilisateur = repository.findById(UtilisateurId)
				.orElseThrow(() -> new ResourceNotFoundException("Utilisateur not found  id :: " + UtilisateurId));

		repository.delete(Utilisateur);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	  
	 
	  @DeleteMapping("/users/delete")
	  public ResponseEntity<String> deleteAllUtilisateur() {
	    System.out.println("Delete All Utilisateur...");
	 
	    repository.deleteAll();
	 
	    return new ResponseEntity<>("All Utilisateurs have been deleted!", HttpStatus.OK);
	  }
	 
	

	  @PutMapping("/users/{id}")
	  public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable("id") long id, @RequestBody Utilisateur Utilisateur) {
	    System.out.println("Update Utilisateur with ID = " + id + "...");
	 
	    Optional<Utilisateur> UtilisateurInfo = repository.findById(id);
	 
	    if (UtilisateurInfo.isPresent()) {
	    	Utilisateur utilisateur = UtilisateurInfo.get();
	    	utilisateur.setRole(Utilisateur.getRole());
	    	utilisateur.setNom(Utilisateur.getNom());
	    	utilisateur.setLogin(Utilisateur.getLogin()); 
	    	utilisateur.setPwd(Utilisateur.getPwd());
	      return new ResponseEntity<>(repository.save(Utilisateur), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
}

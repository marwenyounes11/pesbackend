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
import tn.elan.model.Statistique;
import tn.elan.repository.StatistiqueRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class StatistiqueController {
	@Autowired
	StatistiqueRepository repository;
	
	 @GetMapping("/statistiques")
	  public List<Statistique> getAllStatistique() {
	    System.out.println("Get all Statistique...");
	 
	    List<Statistique> Statistique = new ArrayList<>();
	    repository.findAll().forEach(Statistique::add);
	 
	    return Statistique;
	  }
	 
	 @GetMapping("/statistiques/lastdate")
	  public Statistique getStatistiqueByDate() {
	    System.out.println("Get  last Statistique ...");
	 
	    Statistique statistique = repository.findstatistique();
	    
	 
	    return statistique;
	  }
	
	@GetMapping("/statistiques/{id}")
	public ResponseEntity<Statistique> getStatistiqueById(@PathVariable(value = "id") Long StatistiqueId)
			throws ResourceNotFoundException {
		Statistique Statistique = repository.findById(StatistiqueId)
				.orElseThrow(() -> new ResourceNotFoundException("Statistique not found for this id :: " + StatistiqueId));
		return ResponseEntity.ok().body(Statistique);
	}

	 
	
	
	@PostMapping("/statistiques")
	public Statistique createStatistique( @RequestBody Statistique Statistique) {
		return repository.save(Statistique);
	}
	

	@DeleteMapping("/statistiques/{id}")
	public Map<String, Boolean> deleteStatistique(@PathVariable(value = "id") Long StatistiqueId)
			throws ResourceNotFoundException {
		Statistique Statistique = repository.findById(StatistiqueId)
				.orElseThrow(() -> new ResourceNotFoundException("Statistique not found  id :: " + StatistiqueId));

		repository.delete(Statistique);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	  
	 
	  @DeleteMapping("/statistiques/delete")
	  public ResponseEntity<String> deleteAllStatistique() {
	    System.out.println("Delete All Statistique...");
	 
	    repository.deleteAll();
	 
	    return new ResponseEntity<>("All Statistiques have been deleted!", HttpStatus.OK);
	  }
	 
	

	  @PutMapping("/statistique/{id}")
	  public ResponseEntity<Statistique> updateStatistique(@PathVariable("id") long id, @RequestBody Statistique Statistique) {
	    System.out.println("Update Statistique with ID = " + id + "...");
	 
	    Optional<Statistique> StatistiqueInfo = repository.findById(id);
	 
	    if (StatistiqueInfo.isPresent()) {
	    	Statistique statistique = StatistiqueInfo.get();
	    	statistique.setDateStatistique(statistique.getDateStatistique());
	    	statistique.setNbCertifier(statistique.getNbCertifier());
	    	statistique.setNbEmbaucher(statistique.getNbEmbaucher());
	    	statistique.setNbFormer(statistique.getNbFormer());
	    	
	      return new ResponseEntity<>(repository.save(statistique), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
}

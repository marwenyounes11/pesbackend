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


import tn.elan.domaine.Response;
import tn.elan.exception.ResourceNotFoundException;
import tn.elan.model.Offre;
import tn.elan.repository.OffreRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class OffreController {
@Autowired 	OffreRepository  repository;

@GetMapping("/offres/{id}")
public ResponseEntity<Offre> getOffreById(@PathVariable(value = "id") Long Id)
		throws ResourceNotFoundException {
	Offre offre = repository.findById(Id)
			.orElseThrow(() -> new ResourceNotFoundException("offre not found for this id :: " + Id));
	return ResponseEntity.ok().body(offre);
}

@GetMapping("/offres")
  public List<Offre> getAllOffres() {
     System.out.println("Get all offres...");
 
    List<Offre> offres = new ArrayList<>();
    repository.findAll().forEach(offres::add);
 
    return offres;
  }

@PostMapping("/offres")
public ResponseEntity<Response> createOffre(@RequestBody Offre offre) {
	

Offre off = repository.save(offre);

if (off != null)
{
	return new ResponseEntity<Response>(new Response (""),HttpStatus.OK);
}
else
{
	return new ResponseEntity<Response>(new Response ("Offre not saved"),HttpStatus.BAD_REQUEST);	
}

}


@DeleteMapping("/offres/{id}")
public Map<String, Boolean> deleteOffre(@PathVariable(value = "id") Long offreId)
		throws ResourceNotFoundException {
	Offre offre = repository.findById(offreId)
			.orElseThrow(() -> new ResourceNotFoundException("Offre not found  id :: " + offreId));
	repository.delete(offre);
	Map<String, Boolean> response = new HashMap<>();
	response.put("deleted", Boolean.TRUE);
	return response;
}

@DeleteMapping("/offres/delete")
public ResponseEntity<String> deleteOffres() {
  System.out.println("Delete All offres...");
  repository.deleteAll();
  return new ResponseEntity<>("All offres have been deleted!", HttpStatus.OK);
}


@PutMapping("/offres/{id}")
public ResponseEntity<Offre> updateOffre(@PathVariable("id") long id, @RequestBody Offre offre) {
  System.out.println("Update Offre with ID = " + id + "...");
  Optional<Offre> offreInfo = repository.findById(id);
  if (offreInfo.isPresent()) {
  	Offre off = offreInfo.get();
         off.setNomPoste(offre.getNomPoste());
         off.setReference(offre.getReference());
         off.setDescriptionPoste(offre.getDescriptionPoste());
         off.setDateEcheance(offre.getDateEcheance());
         
         
    return new ResponseEntity<>(repository.save(off), HttpStatus.OK);
  } else {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}


}

package tn.elan.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tn.elan.domaine.Response;
import tn.elan.exception.ResourceNotFoundException;
import tn.elan.model.Ressource;
import tn.elan.repository.RessourceRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class RessourceController {
	@Autowired  ServletContext context;
	@Autowired 	RessourceRepository  repository;
	
	 @GetMapping("/ressources/{id}")
		public ResponseEntity<Ressource> getRessourceById(@PathVariable(value = "id") Long Id)
				throws ResourceNotFoundException {
			Ressource ressource = repository.findById(Id)
					.orElseThrow(() -> new ResourceNotFoundException("Ressource not found for this id :: " + Id));
			return ResponseEntity.ok().body(ressource);
		}
	 
	 @GetMapping("/ressources/5/{cv}")
		public ResponseEntity<Ressource> getRessourceByCv(@PathVariable(value = "cv") String cv)
				throws ResourceNotFoundException {
			Ressource ressource = repository.findByCv(cv);
			return ResponseEntity.ok().body(ressource);
		}
	 
	 @GetMapping(path="/Imgressources/{id}")
	 public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
		 Ressource ressource   = repository.findById(id).get();
		 return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+ressource.getImage()));
	 }
	 @GetMapping(path="/Cvressources/{id}")
	 public byte[] getCv(@PathVariable("id") Long id) throws Exception{
		 Ressource ressource   = repository.findById(id).get();
		 return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+ressource.getCv()));
	 }
	
	@GetMapping("/ressources")
	  public List<Ressource> getAllressources() {
	     System.out.println("Get all ressources...");
	 
	    List<Ressource> ressources = new ArrayList<>();
	    repository.findAll().forEach(ressources::add);
	 
	    return ressources;
	  }
	

	@GetMapping("/ressourcectp")
	  public List<Ressource> getRessourceCtp() {
	     System.out.println("Get  Ressource ctp...");
	 
	    List<Ressource> ressource = new ArrayList<>();
	    repository.listctp().forEach(ressource::add);
	 
	    return ressource;
	  }
	
	@GetMapping("/ressourcecoach")
	  public List<Ressource> getRessourceCoach() {
	     System.out.println("Get  Ressource coach...");
	 
	    List<Ressource> ressource = new ArrayList<>();
	    repository.listcoach().forEach(ressource::add);
	 
	    return ressource;
	  }
	

	@GetMapping("/ressourcemanager")
	  public List<Ressource> getRessourceManager() {
	     System.out.println("Get  Ressource Manager...");
	 
	    List<Ressource> ressource = new ArrayList<>();
	    repository.listmanager().forEach(ressource::add);
	 
	    return ressource;
	  }
	 
	 @GetMapping ("/getAllRessources")
	 public ResponseEntity<List<String>> getAll()
	 {
		 List<String> listArt = new ArrayList<String>();
		 String filesPath = context.getRealPath("/Images");
		 File filefolder = new File(filesPath);
		 if (filefolder != null)
		 {
			for (File file :filefolder.listFiles())
			{
				if(!file.isDirectory())
				{
				  String encodeBase64 = null;
				  try {
					  String extension = FilenameUtils.getExtension(file.getName());
					  FileInputStream fileInputStream = new FileInputStream(file);
				      byte[] bytes = new byte[(int)file.length()];
				      fileInputStream.read(bytes);
				      encodeBase64 = Base64.getEncoder().encodeToString(bytes);
				      listArt.add("data:image/"+extension+";base64,"+encodeBase64);
				      fileInputStream.close();
				      
				      
				  }catch (Exception e){
					  
				  }
				}
			}
		 }
		 return new ResponseEntity<List<String>>(listArt,HttpStatus.OK);
	 }
	 @PostMapping("/ressources")
	 public ResponseEntity<Response> createRessource (@RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2,
			 @RequestParam("ressource") String ressource) throws JsonParseException , JsonMappingException , Exception
	 {
		 System.out.println("Ok .............");
        Ressource arti = new ObjectMapper().readValue(ressource, Ressource.class);
        boolean isExit = new File(context.getRealPath("/Images/")).exists();
        if (!isExit)
        {
        	new File (context.getRealPath("/Images/")).mkdir();
        	System.out.println("mk dir.............");
        }
        String filename1 = file1.getOriginalFilename();
        String filename2 = file2.getOriginalFilename();
        String newFileName1 = FilenameUtils.getBaseName(filename1)+"."+FilenameUtils.getExtension(filename1);
        String newFileName2 = FilenameUtils.getBaseName(filename2)+"."+FilenameUtils.getExtension(filename2);
        File serverFile1 = new File (context.getRealPath("/Images/"+File.separator+newFileName1));
        File serverFile2 = new File (context.getRealPath("/Images/"+File.separator+newFileName2));
        try
        {
        	System.out.println("Image");
        	 FileUtils.writeByteArrayToFile(serverFile1,file1.getBytes());
        	 FileUtils.writeByteArrayToFile(serverFile2,file2.getBytes());
        	 
        }catch(Exception e) {
        	e.printStackTrace();
        }
       
        arti.setImage(newFileName1);
        arti.setCv(newFileName2);
        Ressource art = repository.save(arti);
        if (art != null)
        {
        	return new ResponseEntity<Response>(new Response (""),HttpStatus.OK);
        }
        else
        {
        	return new ResponseEntity<Response>(new Response ("Ressource not saved"),HttpStatus.BAD_REQUEST);	
        }
	 }
	 @DeleteMapping("/ressources/{id}")
		public Map<String, Boolean> deleteRessource(@PathVariable(value = "id") Long ressourceId)
				throws ResourceNotFoundException {
			Ressource ressource = repository.findById(ressourceId)
					.orElseThrow(() -> new ResourceNotFoundException("Ressource not found  id :: " + ressourceId));
			repository.delete(ressource);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return response;
		}
		  	 
		@DeleteMapping("/ressources/delete")
		  public ResponseEntity<String> deleteAllressources() {
		    System.out.println("Delete All ressources...");
		    repository.deleteAll();
		    return new ResponseEntity<>("All ressources have been deleted!", HttpStatus.OK);
		}
		
		 @PutMapping("/ressources/{id}")
		  public ResponseEntity<Ressource> updateRessource(@PathVariable("id") long id, @RequestBody Ressource ressource) {
		    System.out.println("Update Ressource with ID = " + id + "...");
		    Optional<Ressource> ressourceInfo = repository.findById(id);
		    if (ressourceInfo.isPresent()) {
		    	Ressource ressources = ressourceInfo.get();
		           ressources.setNom(ressource.getNom());
		           ressources.setPrenom(ressource.getPrenom());
		           ressources.setEmail(ressource.getEmail());
		           ressources.setTelephone(ressource.getTelephone());
		           ressources.setDescription(ressource.getDescription());
		           ressources.setType(ressource.getType());
		           ressources.setImage(ressource.getImage());
		           ressources.setCv(ressource.getCv());
		      return new ResponseEntity<>(repository.save(ressources), HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
		  }
}

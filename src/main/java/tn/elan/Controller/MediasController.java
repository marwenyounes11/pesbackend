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
import tn.elan.model.Medias;
import tn.elan.repository.MediasRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MediasController {
	@Autowired  ServletContext context;
	@Autowired 	MediasRepository  repository;
	
	 @GetMapping("/medias/{id}")
		public ResponseEntity<Medias> getMediasById(@PathVariable(value = "id") Long Id)
				throws ResourceNotFoundException {
			Medias medias = repository.findById(Id)
					.orElseThrow(() -> new ResourceNotFoundException("Medias not found for this id :: " + Id));
			return ResponseEntity.ok().body(medias);
		}
	 
	 @GetMapping(path="/Imgmedias/{id}")
	 public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
		 Medias medias   = repository.findById(id).get();
		 return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+medias.getImage()));
	 }
	
	
	 
	@GetMapping("/medias")
	  public List<Medias> getAllMedias() {
	     System.out.println("Get all Medias...");
	 
	    List<Medias> medias = new ArrayList<>();
	    repository.findAll().forEach(medias::add);
	 
	    return medias;
	  }
	

	
	
	
	
	@GetMapping("/mediasevenement")
	  public List<Medias> getMediasEvenement() {
	     System.out.println("Get  Medias evenement...");
	 
	    List<Medias> medias = new ArrayList<>();
	    repository.listevenement().forEach(medias::add);
	 
	    return medias;
	  }
	
	@GetMapping("/mediasatelier")
	  public List<Medias> getMediasAtelier() {
	     System.out.println("Get  Medias atelier...");
	 
	    List<Medias> medias = new ArrayList<>();
	    repository.listatelier().forEach(medias::add);
	 
	    return medias;
	  }
	
	@GetMapping("/mediaspress")
	  public List<Medias> getMediasPress() {
	     System.out.println("Get  Medias press...");
	 
	    List<Medias> medias = new ArrayList<>();
	    repository.listpress().forEach(medias::add);
	 
	    return medias;
	  }
	
	
	 
	 @GetMapping ("/getMedias")
	 public ResponseEntity<List<String>> getAll()
	 {
		 List<String> listMed = new ArrayList<String>();
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
				      listMed.add("data:image/"+extension+";base64,"+encodeBase64);
				      fileInputStream.close();
				      
				      
				  }catch (Exception e){
					  
				  }
				}
			}
		 }
		 return new ResponseEntity<List<String>>(listMed,HttpStatus.OK);
	 }
	 @PostMapping("/medias")
	 public ResponseEntity<Response> createMedias (@RequestParam("file1") MultipartFile file1,
			 @RequestParam("medias") String medias) throws JsonParseException , JsonMappingException , Exception
	 {
		 System.out.println("Ok .............");
        Medias med = new ObjectMapper().readValue(medias, Medias.class);
        boolean isExit = new File(context.getRealPath("/Images/")).exists();
        if (!isExit)
        {
        	new File (context.getRealPath("/Images/")).mkdir();
        	System.out.println("mk dir.............");
        }
        String filename1 = file1.getOriginalFilename();
        String newFileName1 = FilenameUtils.getBaseName(filename1)+"."+FilenameUtils.getExtension(filename1);
        File serverFile1 = new File (context.getRealPath("/Images/"+File.separator+newFileName1));
        try
        {
        	System.out.println("Image");
        	 FileUtils.writeByteArrayToFile(serverFile1,file1.getBytes());
        	 
        }catch(Exception e) {
        	e.printStackTrace();
        }

       
        med.setImage(newFileName1);
        Medias meds = repository.save(med);
        if (meds!= null)
        {
        	return new ResponseEntity<Response>(new Response (""),HttpStatus.OK);
        }
        else
        {
        	return new ResponseEntity<Response>(new Response ("Medias not saved"),HttpStatus.BAD_REQUEST);	
        }
	 }
	 @DeleteMapping("/medias/{id}")
		public Map<String, Boolean> deleteMedias(@PathVariable(value = "id") Long mediasId)
				throws ResourceNotFoundException {
			Medias medias = repository.findById(mediasId)
					.orElseThrow(() -> new ResourceNotFoundException("Medias not found  id :: " + mediasId));
			repository.delete(medias);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return response;
		}
		  	 
		@DeleteMapping("/medias/delete")
		  public ResponseEntity<String> deleteAllmedias() {
		    System.out.println("Delete All Medias...");
		    repository.deleteAll();
		    return new ResponseEntity<>("All Medias have been deleted!", HttpStatus.OK);
		}
		
		 @PutMapping("/medias/{id}")
		  public ResponseEntity<Medias> updateMedias(@PathVariable("id") long id, @RequestBody Medias Medias) {
		    System.out.println("Update Medias with ID = " + id + "...");
		    Optional<Medias> mediasInfo = repository.findById(id);
		    if (mediasInfo.isPresent()) {
		    	Medias medias = mediasInfo.get();
		    	medias.setType(Medias.getType());
		    	medias.setTitre(Medias.getTitre());
		        medias.setImage(Medias.getImage());   
		      return new ResponseEntity<>(repository.save(medias), HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
		  }

}

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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import javax.servlet.ServletContext;

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
import tn.elan.model.Article;
import tn.elan.repository.ArticleRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ArticleController {
	
	@Autowired  ServletContext context;
	@Autowired 	ArticleRepository  repository;
	
	 @GetMapping("/articles/{id}")
		public ResponseEntity<Article> getArticleById(@PathVariable(value = "id") Long Id)
				throws ResourceNotFoundException {
			Article Article = repository.findById(Id)
					.orElseThrow(() -> new ResourceNotFoundException("Article not found for this id :: " + Id));
			return ResponseEntity.ok().body(Article);
		}
	 
	 @GetMapping(path="/Imgarticles/{id}")
	 public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
		 Article Article   = repository.findById(id).get();
		 return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+Article.getImage()));
	 }
	
	@GetMapping("/articles")
	  public List<Article> getAllarticles() {
	     System.out.println("Get all articles...");
	 
	    List<Article> articles = new ArrayList<>();
	    repository.findAll().forEach(articles::add);
	 
	    return articles;
	  }
	
	@GetMapping("/larticles")
	  public List<Article> getLastAllarticles() {
	     System.out.println("Get last articles...");
	 
	    List<Article> articles = new ArrayList<>();
	    repository.listArticle().forEach(articles::add);
	 
	    return articles;
	  }
	 
	 @GetMapping ("/getAll")
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
	 @PostMapping("/articles")
	 public ResponseEntity<Response> createArticle (@RequestParam("file") MultipartFile file,
			 @RequestParam("article") String article) throws JsonParseException , JsonMappingException , Exception
	 {
		 System.out.println("Ok .............");
        Article arti = new ObjectMapper().readValue(article, Article.class);
        boolean isExit = new File(context.getRealPath("/Images/")).exists();
        if (!isExit)
        {
        	new File (context.getRealPath("/Images/")).mkdir();
        	System.out.println("mk dir.............");
        }
        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
        File serverFile = new File (context.getRealPath("/Images/"+File.separator+newFileName));
        try
        {
        	System.out.println("Image");
        	 FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
        	 
        }catch(Exception e) {
        	e.printStackTrace();
        }

       
        arti.setImage(newFileName);
        Article art = repository.save(arti);
        if (art != null)
        {
        	return new ResponseEntity<Response>(new Response (""),HttpStatus.OK);
        }
        else
        {
        	return new ResponseEntity<Response>(new Response ("Article not saved"),HttpStatus.BAD_REQUEST);	
        }
	 }
	 @DeleteMapping("/articles/{id}")
		public Map<String, Boolean> deleteArticle(@PathVariable(value = "id") Long ArticleId)
				throws ResourceNotFoundException {
			Article Article = repository.findById(ArticleId)
					.orElseThrow(() -> new ResourceNotFoundException("Article not found  id :: " + ArticleId));
			repository.delete(Article);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return response;
		}
		  	 
		@DeleteMapping("/articles/delete")
		  public ResponseEntity<String> deleteAllarticles() {
		    System.out.println("Delete All articles...");
		    repository.deleteAll();
		    return new ResponseEntity<>("All articles have been deleted!", HttpStatus.OK);
		}
		
		 @PutMapping("/articles/{id}")
		  public ResponseEntity<Article> updateArticle(@PathVariable("id") long id, @RequestBody Article Article) {
		    System.out.println("Update Article with ID = " + id + "...");
		    Optional<Article> ArticleInfo = repository.findById(id);
		    
		    if (ArticleInfo.isPresent()) {
		    	Article article = ArticleInfo.get();
		           article.setAuteur(Article.getAuteur());
		           article.setCorps(Article.getCorps());
		           article.setDate(Article.getDate());
		           article.setImage(Article.getImage());
		           article.setTitre(Article.getTitre());
		           
		      return new ResponseEntity<>(repository.save(article), HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
		  }
}

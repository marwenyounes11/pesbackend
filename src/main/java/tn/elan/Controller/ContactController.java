package tn.elan.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.elan.Service.EmailService;
import tn.elan.exception.ResourceNotFoundException;
import tn.elan.model.Contact;
import tn.elan.model.Inscription;
import tn.elan.repository.ContactRepository;
import tn.elan.repository.InscriptionRepository;






@CrossOrigin
@RestController
@RequestMapping("/api")
public class ContactController {
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	InscriptionRepository  inrepository;
	@Autowired 	ContactRepository  repository;

	@GetMapping("/contacts/{id}")
	public ResponseEntity<Contact> getContactById(@PathVariable(value = "id") Long Id)
			throws ResourceNotFoundException {
		Contact contact = repository.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("contact not found for this id :: " + Id));
		return ResponseEntity.ok().body(contact);
	}

	@GetMapping("/contacts")
	  public List<Contact> getAllContacts() {
	     System.out.println("Get all contacts...");
	 
	    List<Contact> contacts = new ArrayList<>();
	    repository.findAll().forEach(contacts::add);
	 
	    return contacts;
	  }
	
	
	
	
	

	@PostMapping("/contacts")
	public Boolean createContact(@RequestBody Contact contact) {
		
	/**	Inscription inscription = inrepository.findInscription();
		 String username = inscription.getUsername();
		 String password = inscription.getPassword();
		Properties props = new Properties();
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.starttls.enable","true");
    	props.put("mail.smtp.host","smtp.office365.com");
    	props.put("mail.smtp.ssl.trust", "smtp.office365.com");
    	props.put("mail.smtp.port","587");
    	Session session = Session.getInstance(props,
    			new javax.mail.Authenticator() {
    			protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication(username, password);
    			}
    			});
    	try {
    		// Etape 2 : Création de l'objet Message
    		Message message = new MimeMessage(session);
    		message.setFrom(new InternetAddress(contact.getEmail()));
    		message.setRecipients(Message.RecipientType.TO,
    		InternetAddress.parse("devWeb03@elan.tn"));
    		message.setSubject(contact.getSujet());
    		message.setText(contact.getMessage());
    		// Etape 3 : Envoyer le message
    		Transport.send(message);
    		System.out.println("Message_envoye");
    		} catch (MessagingException e) {
    		throw new RuntimeException(e);
    		} **/
		SimpleMailMessage mailMessage = new SimpleMailMessage();

	        mailMessage.setTo("marwenyounes1@gmail.com");
	        mailMessage.setSubject(contact.getSujet());
	        mailMessage.setText(contact.getMessage());
	        mailMessage.setFrom(contact.getEmail());
	        javaMailSender.send(mailMessage);
		return true ;
    }
	

	

	


	@DeleteMapping("/contacts/{id}")
	public Map<String, Boolean> deleteContact(@PathVariable(value = "id") Long contactId)
			throws ResourceNotFoundException {
		Contact contact = repository.findById(contactId)
				.orElseThrow(() -> new ResourceNotFoundException("Contact not found  id :: " + contactId));
		repository.delete(contact);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping("/contacts/delete")
	public ResponseEntity<String> deleteContacts() {
	  System.out.println("Delete All Contacts...");
	  repository.deleteAll();
	  return new ResponseEntity<>("All Contacts have been deleted!", HttpStatus.OK);
	}


	@PutMapping("/contacts/{id}")
	public ResponseEntity<Contact> updateContact(@PathVariable("id") long id, @RequestBody Contact contact) {
	  System.out.println("Update Contact with ID = " + id + "...");
	  Optional<Contact> contactInfo = repository.findById(id);
	  if (contactInfo.isPresent()) {
	  	Contact con = contactInfo.get();
	         con.setCandidats(contact.getCandidat().toString());
	         con.setMessage(contact.getMessage());
	         con.setSujet(contact.getSujet());
	         
	    return new ResponseEntity<>(repository.save(con), HttpStatus.OK);
	  } else {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  }
	}

}

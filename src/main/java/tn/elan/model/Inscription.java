package tn.elan.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "inscription")
public class Inscription {
	@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private LocalDateTime dateInscription;
	private String username;
	 private String password;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public LocalDateTime getDateInscription() {
		return dateInscription;
	}
	public void setDateInscription(LocalDateTime dateInscription) {
		this.dateInscription = dateInscription;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public Inscription(long id, LocalDateTime dateInscription, String username, String password) {
		super();
		this.id = id;
		this.dateInscription = dateInscription;
		this.username = username;
		this.password = password;
	}
	public Inscription() {
	}
	 
}

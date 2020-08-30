package tn.elan.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "candidat")
public class Candidat {
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nom;
	private String prenom;
	private String telephone;
	private String email;
	@ManyToOne
	@JoinColumn(name = "contact_id")
	@JsonBackReference
	private Contact contact;
	@ManyToOne
	@JoinColumn(name = "candidature_id")
	@JsonBackReference(value="candidat-candidature")
	private Candidature candidature;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	public Candidature getCandidature() {
		return candidature;
	}
	public void setCandidature(Candidature candidature) {
		this.candidature = candidature;
	}
	
	public Candidat(long id, String nom, String prenom, String telephone, String email, Contact contact,
			Candidature candidature) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.email = email;
		this.contact = contact;
		this.candidature = candidature;
	}
	public Candidat() {
		
	}
	
}

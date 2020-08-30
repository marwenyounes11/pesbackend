package tn.elan.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "offre")
public class Offre {
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private long id;
	 private String reference;
	 private String nomPoste;
	 private String descriptionPoste;
	 private Date dateEcheance;
	 private String candidatures;
	 
	 @OneToMany(mappedBy = "offre",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch=FetchType.EAGER)
	 @JsonManagedReference 
  Set<Candidature> candidature;
	 
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getNomPoste() {
		return nomPoste;
	}
	public void setNomPoste(String nomPoste) {
		this.nomPoste = nomPoste;
	}
	public String getDescriptionPoste() {
		return descriptionPoste;
	}
	public void setDescriptionPoste(String descriptionPoste) {
		this.descriptionPoste = descriptionPoste;
	}
	public Date getDateEcheance() {
		return dateEcheance;
	}
	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}
	
	public String getCandidatures() {
		return candidatures;
	}
	public void setCandidatures(String candidatures) {
		this.candidatures = candidatures;
	}
	public Set<Candidature> getCandidature() {
		return candidature;
	}
	public void setCandidature(Set<Candidature> candidature) {
		this.candidature = candidature;
	}
	
	
	public Offre(long id, String reference, String nomPoste, String descriptionPoste, Date dateEcheance,
			String candidatures, Set<Candidature> candidature) {
		super();
		this.id = id;
		this.reference = reference;
		this.nomPoste = nomPoste;
		this.descriptionPoste = descriptionPoste;
		this.dateEcheance = dateEcheance;
		this.candidatures = candidatures;
		this.candidature = candidature;
	}
	public Offre() {
	}
	 
	 
	 
}

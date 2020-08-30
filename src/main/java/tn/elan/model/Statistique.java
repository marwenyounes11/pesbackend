package tn.elan.model;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "statistique")
public class Statistique {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private Date dateStatistique;
	 private int nbFormer;
	 private int nbEmbaucher;
	 private int nbCertifier;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getDateStatistique() {
		return dateStatistique;
	}
	public void setDateStatistique(Date dateStatistique) {
		this.dateStatistique = dateStatistique;
	}
	public int getNbFormer() {
		return nbFormer;
	}
	public void setNbFormer(int nbFormer) {
		this.nbFormer = nbFormer;
	}
	public int getNbEmbaucher() {
		return nbEmbaucher;
	}
	public void setNbEmbaucher(int nbEmbaucher) {
		this.nbEmbaucher = nbEmbaucher;
	}
	public int getNbCertifier() {
		return nbCertifier;
	}
	public void setNbCertifier(int nbCertifier) {
		this.nbCertifier = nbCertifier;
	}
	public Statistique() {
	}
	public Statistique(long id, Date dateStatistique, int nbFormer, int nbEmbaucher, int nbCertifier) {
		super();
		this.id = id;
		this.dateStatistique = dateStatistique;
		this.nbFormer = nbFormer;
		this.nbEmbaucher = nbEmbaucher;
		this.nbCertifier = nbCertifier;
	}
	
	
	
	 
}

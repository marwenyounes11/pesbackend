package tn.elan.dto;

public class CandidatureDto {
	private long id;
	private String nom;
	private String prenom;
	private String telephone;
	private String email;
	private String cv;
	private String diplome;
	private String type;
	private long idOffre;
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
	public String getCv() {
		return cv;
	}
	public void setCv(String cv) {
		this.cv = cv;
	}
	public String getDiplome() {
		return diplome;
	}
	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getIdOffre() {
		return idOffre;
	}
	public void setIdOffre(long idOffre) {
		this.idOffre = idOffre;
	}
	public CandidatureDto() {
		
	}
	public CandidatureDto(long id, String nom, String prenom, String telephone, String email, String cv, String diplome,
			String type, long idOffre) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.email = email;
		this.cv = cv;
		this.diplome = diplome;
		this.type = type;
		this.idOffre = idOffre;
	}
	
	
	
}

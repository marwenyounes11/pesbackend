package tn.elan.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medias")
public class Medias {
	 @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	 private String type;
	 private String titre;
	 private String image;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	
	public Medias(long id, String type, String titre, String image) {
		super();
		this.id = id;
		this.type = type;
		this.titre = titre;
		this.image = image;
	}
	public Medias() {
		
	}
	 

}

package com.tk.baas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Picture {
	
	
    private String id;
    private String pictureUrl;
    private String tinyPictureUrl;
    
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getTinyPictureUrl() {
		return tinyPictureUrl;
	}

	public void setTinyPictureUrl(String tinyPictureUrl) {
		this.tinyPictureUrl = tinyPictureUrl;
	}
    
    
}

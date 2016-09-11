package com.tk.baas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ProDetailPicture {
	
    private String id;
    private String detailPicUrl;
      
    @Id
  	@GeneratedValue(generator="system-uuid")
  	@GenericGenerator(name="system-uuid", strategy="uuid")  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDetailPicUrl() {
		return detailPicUrl;
	}
	public void setDetailPicUrl(String detailPicUrl) {
		this.detailPicUrl = detailPicUrl;
	}
      
      
}

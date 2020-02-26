package it.bigdata.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private Integer id;
	private Date CreationDate;
	private String Location;
	private int Views;
	private String fake;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return this.CreationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.CreationDate = creationDate;
	}

	public String getLocation() {
		return this.Location;
	}

	public void setLocation(String location) {
		this.Location = location;
	}

	public int getViews() {
		return this.Views;
	}

	public void setViews(int views) {
		this.Views = views;
	}

	public String getFake() {
		return this.fake;
	}

	public void setFake(String fake) {
		this.fake = fake;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", CreationDate=" + CreationDate + ", Location=" + Location + ", Views=" + Views
				+ ", fake=" + fake + "]";
	}


}

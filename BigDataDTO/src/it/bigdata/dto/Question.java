package it.bigdata.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String fake;
	private Integer score;
	private String title;
	private Integer OwnerUserId;
	private List<Tag> tagQuestions;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFake() {
		return fake;
	}
	public void setFake(String fake) {
		this.fake = fake;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getOwnerUserId() {
		return OwnerUserId;
	}
	public void setOwnerUserId(Integer ownerUserId) {
		OwnerUserId = ownerUserId;
	}
	public List<Tag> getTagQuestions() {
		return tagQuestions;
	}
	public void setTagQuestions(List<Tag> tagQuestions) {
		this.tagQuestions = tagQuestions;
	}
	
}

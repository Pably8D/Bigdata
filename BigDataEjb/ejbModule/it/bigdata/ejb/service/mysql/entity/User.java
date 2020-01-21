package it.bigdata.ejb.service.mysql.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreationDate")
	private Date creationDate;
	@Column(name = "Location")
	private String location;
	@Column(name = "Views")
	private int views;
	@Column(name = "fake")
	private String fake;
	@OneToMany(mappedBy = "user")
	private List<Question> questions;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getViews() {
		return this.views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public List<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public String getFake() {
		return this.fake;
	}

	public void setFake(String fake) {
		this.fake = fake;
	}

	public Question addQuestion(Question question) {
		this.getQuestions().add(question);
		question.setUser(this);
		return question;
	}

	public Question removeQuestion(Question question) {
		this.getQuestions().remove(question);
		question.setUser((User) null);
		return question;
	}
}

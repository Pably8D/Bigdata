package it.bigdata.ejb.service.mysql.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q")
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	@Column(name = "fake")
	private String fake;
	@Column(name = "score")
	private int score;
	@Column(name = "title")
	private String title;
	@ManyToOne
	@JoinColumn(name = "OwnerUserId", insertable = false, updatable = false)
	private User user;
	@Column(name = "OwnerUserId")
	private int OwnerUserId;
	@OneToMany(mappedBy = "question")
	private List<TagQuestion> tagQuestions;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFake() {
		return this.fake;
	}

	public void setFake(String fake) {
		this.fake = fake;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<TagQuestion> getTagQuestions() {
		return this.tagQuestions;
	}

	public void setTagQuestions(List<TagQuestion> tagQuestions) {
		this.tagQuestions = tagQuestions;
	}

	public int getOwnerUserId() {
		return this.OwnerUserId;
	}

	public void setOwnerUserId(int ownerUserId) {
		this.OwnerUserId = ownerUserId;
	}

	public TagQuestion addTagQuestion(TagQuestion tagQuestion) {
		this.getTagQuestions().add(tagQuestion);
		tagQuestion.setQuestion(this);
		return tagQuestion;
	}

	public TagQuestion removeTagQuestion(TagQuestion tagQuestion) {
		this.getTagQuestions().remove(tagQuestion);
		tagQuestion.setQuestion((Question) null);
		return tagQuestion;
	}
}

package it.bigdata.ejb.service.mysql.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tag_question")
@NamedQuery(name = "TagQuestion.findAll", query = "SELECT t FROM TagQuestion t")
public class TagQuestion implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "IdQuestion", insertable = true, updatable = false)
	private int question_id;
	@Column(name = "IdTag", insertable = true, updatable = false)
	private int tag_id;
	@ManyToOne
	@JoinColumn(name = "IdQuestion", insertable = false, updatable = false)
	private Question question;
	@ManyToOne
	@JoinColumn(name = "IdTag", insertable = false, updatable = false)
	private Tag tag;
	@Column(name = "fake")
	private String fake;


	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Tag getTag() {
		return this.tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public String getFake() {
		return this.fake;
	}

	public void setFake(String fake) {
		this.fake = fake;
	}

	public int getQuestion_id() {
		return this.question_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	public int getTag_id() {
		return this.tag_id;
	}

	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}
}

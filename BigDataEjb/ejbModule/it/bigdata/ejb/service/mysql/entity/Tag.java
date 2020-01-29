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
@NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	@Column(name = "TagName")
	private String tagName;
	@ManyToOne
	@JoinColumn(name = "IdCategory")
	private Category category;
	@OneToMany(mappedBy = "tag")
	private List<TagQuestion> tagQuestions;
	@Column(name = "fake")
	private String fake;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTagName() {
		return this.tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<TagQuestion> getTagQuestions() {
		return this.tagQuestions;
	}

	public void setTagQuestions(List<TagQuestion> tagQuestions) {
		this.tagQuestions = tagQuestions;
	}

	public String getFake() {
		return this.fake;
	}

	public void setFake(String fake) {
		this.fake = fake;
	}

	public TagQuestion addTagQuestion(TagQuestion tagQuestion) {
		this.getTagQuestions().add(tagQuestion);
		tagQuestion.setTag(this);
		return tagQuestion;
	}

	public TagQuestion removeTagQuestion(TagQuestion tagQuestion) {
		this.getTagQuestions().remove(tagQuestion);
		tagQuestion.setTag((Tag) null);
		return tagQuestion;
	}
}

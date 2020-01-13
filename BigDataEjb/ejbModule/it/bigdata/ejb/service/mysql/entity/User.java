package it.bigdata.ejb.service.mysql.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQuery(
   name = "User.findAll",
   query = "SELECT u FROM User u"
)
public class User implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   private int id;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "creation_date"
   )
   private Date creationDate;
   @Column(
      name = "location"
   )
   private String location;
   @Column(
      name = "views"
   )
   private int views;
   @Column(
      name = "fake"
   )
   private String fake;
   @OneToMany(
      mappedBy = "user"
   )
   private List<Question> questions;

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
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
      question.setUser((User)null);
      return question;
   }
}

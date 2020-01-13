package it.bigdata.ejb.service.mysql.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(
   name = "Category.findAll",
   query = "SELECT c FROM Category c"
)
public class Category implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   private int id;
   @Column(
      name = "category_name"
   )
   private String categoryName;
   @Column(
      name = "fake"
   )
   private String fake;
   @OneToMany(
      mappedBy = "category"
   )
   private List<Tag> tags;

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getCategoryName() {
      return this.categoryName;
   }

   public void setCategoryName(String categoryName) {
      this.categoryName = categoryName;
   }

   public List<Tag> getTags() {
      return this.tags;
   }

   public void setTags(List<Tag> tags) {
      this.tags = tags;
   }

   public String getFake() {
      return this.fake;
   }

   public void setFake(String fake) {
      this.fake = fake;
   }

   public Tag addTag(Tag tag) {
      this.getTags().add(tag);
      tag.setCategory(this);
      return tag;
   }

   public Tag removeTag(Tag tag) {
      this.getTags().remove(tag);
      tag.setCategory((Category)null);
      return tag;
   }
}

package it.bigdata.dto;

import java.io.Serializable;

public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;
   Integer id;
   String name;
   Category category;

   public Tag(Integer id, String name, Category category) {
      this.id = id;
      this.name = name;
      this.category = category;
   }

   public Integer getId() {
      return this.id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Category getCategory() {
      return this.category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }

   public String toString() {
      return "{id:\"" + this.id + "\", \"tagName:\"" + this.name + ", " + this.category + "}";
   }
}

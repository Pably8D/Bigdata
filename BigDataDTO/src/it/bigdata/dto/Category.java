package it.bigdata.dto;

public class Category {
   Integer id;
   String name;

   public Category(Integer id, String name) {
      this.id = id;
      this.name = name;
   }

   public Integer getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return "Category : {\"id\":" + this.id + ", \"name\":" + this.name + "}";
   }
}

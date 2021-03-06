package it.bigdata.dto.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Tables {
   USER("User", new String[]{"Id", "CreationDate", "Location", "Views"}),
   QUESTION("Question", new String[]{"Id", "Score", "Title", "OwnerUserId", "Tag", "Category"}),
   TAGS("Tag", new String[]{"Id", "name"}),
   CATEGORY("Category", new String[]{"Id", "name"});

   String name;
   String[] cloumns;

   private Tables(String name, String[] cloumns) {
      this.name = name;
      this.cloumns = cloumns;
   }

   public static List<String> getNamesOfTables() {
      List<String> l = new ArrayList();
      Tables[] var4;
      int var3 = (var4 = values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         Tables t = var4[var2];
         l.add(t.name());
      }

      return l;
   }

   public static List<String> getColumnsOf(String tableName) {
      List<String> l = null;
      if (getNamesOfTables().contains(tableName)) {
         l = Arrays.asList(valueOf(tableName).cloumns);
      }

      return l;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String[] getCloumns() {
      return this.cloumns;
   }

   public void setCloumns(String[] cloumns) {
      this.cloumns = cloumns;
   }
}

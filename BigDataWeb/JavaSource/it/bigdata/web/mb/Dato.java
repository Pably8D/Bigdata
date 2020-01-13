package it.bigdata.web.mb;

import java.io.Serializable;

public class Dato implements Serializable {
   private static final long serialVersionUID = 1L;
   private String column;
   private Object value;
   private Boolean rendered;

   public Object getValue() {
      return this.value;
   }

   public void setValue(Object value) {
      this.value = value;
   }

   public Boolean getRendered() {
      return this.rendered;
   }

   public void setRendered(Boolean rendered) {
      this.rendered = rendered;
   }

   public String getColumn() {
      return this.column;
   }

   public void setColumn(String column) {
      this.column = column;
   }

   public Dato(String column, Boolean rendered) {
      this.column = column;
      this.rendered = rendered;
   }

   public Dato() {
   }
}

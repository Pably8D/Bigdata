package it.bigdata.ejb.service.mysql;

import java.util.HashMap;
import java.util.List;

public interface MySqlCrud {
   Long insert(int var1, HashMap<String, String> var2, String var3);

   Long update(int var1, HashMap<String, String> var2, HashMap<String, String> var3, String var4);

   Long delete(String var1);

   Long sql(HashMap<String, HashMap<String, String>> var1, HashMap<String, List<String>> var2);
}

package it.bigdata.ejb.service.mongo;

import it.bigdata.dto.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface MongoCRUD {
   Set<String> getAllUser();

   Set<Tag> getAllTag();

   Set<String> getQuestionsByUser(String var1);

   Set<String> getAnswerByUser(String var1);

   Set<String> getQuestionsByTags(List<String> var1);

   Set<String> getAllUserGroupByLocation(String var1);

   Long insert(int var1, HashMap<String, String> var2, String var3);

   Long update(int var1, HashMap<String, String> var2, HashMap<String, String> var3, String var4);

   Long delete(String var1);

   Long sql(HashMap<String, HashMap<String, String>> var1, HashMap<String, List<String>> var2);
}

package it.bigdata.ejb.service.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.AggregationOptions;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import it.bigdata.dto.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@Local({MongoCRUD.class})
@LocalBean
public class MongoCRUDImp extends MongoClientProvider implements MongoCRUD {
   public Set<String> getAllUserGroupByLocation(String groupByLocation) {
      DBCollection coll = this.getDB().getCollection("question");
      DBObject query = new BasicDBObject();
      query.put("Score", "001");
      coll.find(query);
      Set<String> list = new HashSet();
      return list;
   }

   public Set<Tag> getAllTag() {
      Iterator cursor = this.getDB().getCollection("question").distinct("TagsDB").iterator();
      Gson gson = new Gson();
      HashSet list = new HashSet();

      try {
         while(cursor.hasNext()) {
            String obj = gson.toJson(cursor.next());
            Tag t = (Tag)gson.fromJson(obj, Tag.class);
            list.add(t);
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      return list;
   }

   public Set<String> getQuestionsByUser(String id) {
      return null;
   }

   public Set<String> getAnswerByUser(String id) {
      return null;
   }

   public Set<String> getQuestionsByTags(List<String> tags) {
      return null;
   }

   public Set<String> getAllUser() {
      return null;
   }

   public Long insert(int numTransactionSelected, HashMap<String, String> column2Value, String tableSelected) {
      ObjectMapper mapper = new ObjectMapper();
      Map<String, String> t = null;
      Gson gson = new Gson();
      DBCollection collection = this.getDB().getCollection(tableSelected.toLowerCase());
      DBCursor c = collection.find().sort(new BasicDBObject("Id", -1)).limit(1);

      while(c.hasNext()) {
         String obj = gson.toJson(c.next());

         try {
            t = (Map)mapper.readValue(obj, HashMap.class);
         } catch (Exception var15) {
            var15.printStackTrace();
         }
      }

      int id = (new Double(Math.random())).intValue();
      if (t != null) {
         id = new Integer((String)t.get("Id"));
      }

      Date startTime = new Date();
      List<DBObject> documents = new ArrayList();
      if (numTransactionSelected != 0) {
         while(true) {
            if (numTransactionSelected <= 0) {
               collection.insert(documents);
               break;
            }

            BasicDBObject document = new BasicDBObject();
            document.put("Id", id);
            document.put("fake", "1");
            ++id;
            Iterator var14 = column2Value.keySet().iterator();

            while(var14.hasNext()) {
               String c1 = (String)var14.next();
               document.put(c1, column2Value.get(c1));
            }

            documents.add(document);
            --numTransactionSelected;
         }
      }

      Date endTime = new Date();
      return new Long(endTime.getTime() - startTime.getTime());
   }

   public Long update(int numTransactionSelected, HashMap<String, String> column2Value, HashMap<String, String> column2ValueWhere, String tableSelected) {
      DBCollection collection = this.getDB().getCollection(tableSelected.toLowerCase());
      BasicDBObject newDocument = new BasicDBObject();
      Date startTime = new Date();
      Iterator var9 = column2Value.keySet().iterator();

      while(var9.hasNext()) {
         String col = (String)var9.next();
         if (!column2Value.keySet().isEmpty()) {
            newDocument.put(col, column2ValueWhere.get(col));
         }
      }

      BasicDBObject searchQuery = new BasicDBObject();
      Iterator var10 = column2ValueWhere.keySet().iterator();

      while(var10.hasNext()) {
         String col = (String)var10.next();
         if (!column2ValueWhere.keySet().isEmpty()) {
            searchQuery.append(col, column2ValueWhere.get(col));
         }
      }

      collection.update(searchQuery, newDocument);
      Date endTime = new Date();
      return new Long(endTime.getTime() - startTime.getTime());
   }

   public Long searchFakeData(String tableSelected) {
      DBCollection collection = this.getDB().getCollection(tableSelected.toLowerCase());
      BasicDBObject searchQuery = new BasicDBObject();
      searchQuery.append("fake", "1");
      return new Long(collection.count(searchQuery));
   }

   public Long delete(String tableSelected) {
      DBCollection collection = this.getDB().getCollection(tableSelected.toLowerCase());
      Date startTime = new Date();
      BasicDBObject searchQuery = new BasicDBObject();
      searchQuery.append("fake", "1");
      Date endTime = new Date();
      collection.remove(searchQuery);
      return new Long(endTime.getTime() - startTime.getTime());
   }

   public Long sql(HashMap<String, HashMap<String, String>> tables2Columnvalue, HashMap<String, List<String>> tables2columnsGroupBy) {
      Date startTime = null;
      Date endTime = null;
      DBObject groupBy = null;
      DBObject sort = null;
      List<DBObject> pipeline = null;
      DBObject where = new BasicDBObject();
      DBObject groupByForUser = null;
      DBObject whereForUser = new BasicDBObject();
      Cursor output;
      List pipeline2;
      DBObject p;
      if (tables2Columnvalue.keySet().size() == 1) {
         Entry<String, HashMap<String, String>> entry = (Entry)tables2Columnvalue.entrySet().iterator().next();
         String tableSelected = (String)entry.getKey();
         HashMap<String, String> column2Value = (HashMap)entry.getValue();
         DBCollection collection = this.getDB().getCollection(tableSelected.toLowerCase());
         if (column2Value != null && tables2columnsGroupBy.get(tableSelected) != null && ((List)tables2columnsGroupBy.get(tableSelected)).isEmpty()) {
            this.createWhereCondition(column2Value, where);
         } else {
            groupBy = this.createGroupByCondition((List)tables2columnsGroupBy.get(tableSelected));
            this.createWhereConditionForGroupBy(column2Value, where);
            BasicDBObject sortFields = new BasicDBObject("count", -1);
            sort = new BasicDBObject("$sort", sortFields);
         }

         startTime = new Date();
         DBObject obj;
         
         if (where != null && !where.keySet().isEmpty() && groupBy != null && !groupBy.keySet().isEmpty()) {
            pipeline2 = Arrays.asList(where, groupBy, sort);
            AggregationOutput outputAggr = collection.aggregate(where, new DBObject[]{groupBy, sort});
            Iterator var18 = outputAggr.results().iterator();

            while(var18.hasNext()) {
               obj = (DBObject)var18.next();
               System.out.println(obj);
            }
         } else if (where != null && !where.keySet().isEmpty()) {
            DBCursor cursor;
            if (groupBy != null && !groupBy.keySet().isEmpty()) {
               cursor = collection.find();
            } else {
               cursor = collection.find(where);
               if (cursor.hasNext()) {
                  p = cursor.next();
                  System.out.println(p);
               }
            }
         } else {
            pipeline2 = Arrays.asList(groupBy, sort);
            output = collection.aggregate(pipeline2, AggregationOptions.builder().allowDiskUse(true).build());
            if (output.hasNext()) {
               obj = (DBObject)output.next();
               System.out.println(obj);
            }
         }
      } else {
         DBCollection coll = this.getDB().getCollection("user");
         if (tables2Columnvalue.get("user") != null) {
            this.createWhereConditionForGroupBy((HashMap)tables2Columnvalue.get("user"), whereForUser);
         } else {
            this.createWhereConditionForGroupBy((HashMap)tables2Columnvalue.get("USER"), whereForUser);
         }

         List colOf;
         if (tables2columnsGroupBy.get("user") != null) {
            colOf = (List)tables2columnsGroupBy.get("user");
            groupByForUser = this.createGroupByCondition(colOf);
         } else {
            colOf = (List)tables2columnsGroupBy.get("USER");
            groupByForUser = this.createGroupByCondition(colOf);
         }

         DBObject lookupFields = new BasicDBObject("from", "question");
         lookupFields.put("localField", "OnerwUserId");
         lookupFields.put("foreignField", "Id");
         lookupFields.put("as", "questions");
         DBObject lookup = new BasicDBObject("$lookup", lookupFields);
         if (tables2columnsGroupBy.containsKey("question")) {
            groupBy = this.createGroupByCondition((List)tables2columnsGroupBy.get("question"));
         } else {
            groupBy = this.createGroupByCondition((List)tables2columnsGroupBy.get("QUESTION"));
         }

         this.createWhereConditionForGroupBy((HashMap)tables2Columnvalue.get("question"), where);
         BasicDBObject sortFields = new BasicDBObject("count", -1);
         new BasicDBObject("$sort", sortFields);
         pipeline2 = Arrays.asList(whereForUser, groupByForUser, lookup, where, groupBy);
         pipeline = new ArrayList();
         Iterator var28 = pipeline2.iterator();

         while(var28.hasNext()) {
            p = (DBObject)var28.next();
            if (p != null && !p.keySet().isEmpty()) {
               pipeline.add(p);
            }
         }

         System.out.println(pipeline);
         startTime = new Date();
         output = coll.aggregate(pipeline, AggregationOptions.builder().maxTime(30L, TimeUnit.MINUTES).allowDiskUse(true).build());
         int c = 0;
         if (output.hasNext()) {
            DBObject obj = (DBObject)output.next();
            System.out.println(obj);
         }

         System.out.println(c);
      }

      endTime = new Date();
      return new Long(endTime.getTime() - startTime.getTime());
   }

   private DBObject createGroupByCondition(List<String> columnsGroupBy) {
      DBObject groupBy = null;
      Map<String, Object> dbObjIdMap = new HashMap();
      Iterator var5 = columnsGroupBy.iterator();

      while(var5.hasNext()) {
         String col = (String)var5.next();
         dbObjIdMap.put(col, "$".concat("questions.").concat(col));
      }

      if (!dbObjIdMap.isEmpty()) {
         groupBy = new BasicDBObject("$group", (new BasicDBObject("_id", new BasicDBObject(dbObjIdMap))).append("count", new BasicDBObject("$sum", 1)));
      }

      return groupBy;
   }

   private DBObject createWhereCondition(HashMap<String, String> column2Value, DBObject where) {
      Iterator var4 = column2Value.keySet().iterator();

      while(var4.hasNext()) {
         String col = (String)var4.next();
         if (col.contains("Date")) {
            where.put(col, new BasicDBObject("$gte", column2Value.get(col)));
         } else if (col.contains("Location")) {
            where.put(col, new BasicDBObject("$regex", column2Value.get(col)));
         } else {
            where.put(col, new BasicDBObject("$eq", column2Value.get(col)));
         }
      }

      return where;
   }

   private DBObject createWhereConditionForGroupBy(HashMap<String, String> column2Value, DBObject where) {
      if (column2Value != null) {
         Iterator var4 = column2Value.keySet().iterator();

         while(var4.hasNext()) {
            String col = (String)var4.next();
            if (col.contains("Date")) {
               where.put("$gte", new BasicDBObject(col, column2Value.get(col)));
            } else {
               where.put("$match", new BasicDBObject(col, column2Value.get(col)));
            }
         }
      }

      return where;
   }
}
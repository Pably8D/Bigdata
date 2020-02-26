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
import it.bigdata.dto.constants.Tables;
import sun.security.jca.GetInstance.Instance;

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
@Local({ MongoCRUD.class })
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

	@SuppressWarnings("rawtypes")
	public Set<Tag> getAllTag() {

		Iterator cursor = this.getDB().getCollection("QUESTION").distinct("TagCategory").iterator();
		Gson gson = new Gson();
		HashSet list = new HashSet();

		try {
			while (cursor.hasNext()) {
				String obj = gson.toJson(cursor.next());
				Tag t = (Tag) gson.fromJson(obj, Tag.class);
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
		Map<String, Object> t = null;
		Gson gson = new Gson();
		DBCollection collection = this.getDB().getCollection(tableSelected.toUpperCase());
		DBCursor c = collection.find().sort(new BasicDBObject("Id", -1)).limit(1);
		int id=0;
		while (c.hasNext()) {
			String obj = gson.toJson(c.next());

			try {
				t = (Map) mapper.readValue(obj, HashMap.class);
			} catch (Exception var15) {
				var15.printStackTrace();
			}
		}

//		int id = (new Double(Math.random())).intValue();
		if (t != null) {
			if(t.get("Id") instanceof Double)
			id = new Integer(((Double) t.get("Id")).intValue());
			else {
			id = new Integer(((Integer) t.get("Id")).intValue());
			}
		}

		Date startTime = new Date();
		List<DBObject> documents = new ArrayList();
		if (numTransactionSelected != 0) {
			while (numTransactionSelected > 0) {
				BasicDBObject document = new BasicDBObject();
				document.put("Id", id);
				document.put("fake", "1");
				++id;
				Iterator var14 = column2Value.keySet().iterator();

				for (String c1:column2Value.keySet()) {
					document.put(c1, column2Value.get(c1));
				}
				collection.insert(document);
//				documents.add(document);
				--numTransactionSelected;
			}
//				collection.insert(documents);
		}

		Date endTime = new Date();
		return new Long(endTime.getTime() - startTime.getTime());
	}

	public Long update(int numTransactionSelected, HashMap<String, String> column2Value,
			HashMap<String, String> column2ValueWhere, String tableSelected) {

		DBCollection collection = this.getDB().getCollection(tableSelected.toUpperCase());
		BasicDBObject newDocument = new BasicDBObject();


		for (String col :column2Value.keySet()) {
			if (!column2Value.keySet().isEmpty() && column2Value.get(col)!=null && !column2Value.get(col).isEmpty()) {
				newDocument.put(col, column2Value.get(col));
			}
		}

		BasicDBObject searchQuery = new BasicDBObject();

		for (String col :column2ValueWhere.keySet()) {
			if (!column2ValueWhere.keySet().isEmpty() &&  column2ValueWhere.get(col)!=null && !column2ValueWhere.get(col).isEmpty()) {
				searchQuery.append(col, column2ValueWhere.get(col));
			}
		}
		BasicDBObject setQuery = new BasicDBObject();
		setQuery.append("$set", newDocument);
		Date startTime = new Date();
		collection.update(searchQuery, setQuery, false, true);
		Date endTime = new Date();
		return new Long(endTime.getTime() - startTime.getTime());
	}

	public Long searchFakeData(String tableSelected) {
		DBCollection collection = this.getDB().getCollection(tableSelected.toUpperCase());
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.append("fake", "1");
		return new Long(collection.count(searchQuery));
	}

	public Long delete(String tableSelected) {
		DBCollection collection = this.getDB().getCollection(tableSelected.toUpperCase());
		Date startTime = new Date();
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.append("fake", "1");
		collection.remove(searchQuery);
		Date endTime = new Date();
		System.out.println("-------------------"+(endTime.getTime() - startTime.getTime()));
		return new Long(endTime.getTime() - startTime.getTime());
	}

	public Long sql(HashMap<String, HashMap<String, String>> tables2Columnvalue,
			HashMap<String, List<String>> tables2columnsGroupBy) {
		Date startTime = null;
		Date endTime = null;
		DBObject groupBy = null;
		DBObject unwind = null;
		DBObject sort = null;
		DBCollection collection = null;
		List<DBObject> pipeline = null;
		DBObject where = null;
		DBObject groupByForUser = null;
		DBObject whereForUser = new BasicDBObject();
		Cursor output;
		List pipeline2;
		DBObject p;
		if (tables2Columnvalue.containsKey(Tables.USER.getName().toUpperCase())
				&& tables2Columnvalue.containsKey(Tables.QUESTION.getName().toUpperCase())) {

			DBCollection coll = this.getDB().getCollection("user");
			if (tables2Columnvalue.get("user") != null) {
				this.createWhereConditionForGroupBy((HashMap) tables2Columnvalue.get("user"), whereForUser);
			} else {
				this.createWhereConditionForGroupBy((HashMap) tables2Columnvalue.get("USER"), whereForUser);
			}

			List colOf;
			if (tables2columnsGroupBy.get("user") != null) {
				colOf = (List) tables2columnsGroupBy.get("user");
				groupByForUser = this.createGroupByCondition(colOf);
			} else {
				colOf = (List) tables2columnsGroupBy.get("USER");
				groupByForUser = this.createGroupByCondition(colOf);
			}

			DBObject lookupFields = new BasicDBObject("from", "question");
			lookupFields.put("localField", "OnerwUserId");
			lookupFields.put("foreignField", "Id");
			lookupFields.put("as", "questions");
			DBObject lookup = new BasicDBObject("$lookup", lookupFields);
			if (tables2columnsGroupBy.containsKey("question")) {
				groupBy = this.createGroupByCondition((List) tables2columnsGroupBy.get("question"));
			} else {
				groupBy = this.createGroupByCondition((List) tables2columnsGroupBy.get("QUESTION"));
			}

			this.createWhereConditionForGroupBy((HashMap) tables2Columnvalue.get("question"), where);
			BasicDBObject sortFields = new BasicDBObject("count", -1);
			new BasicDBObject("$sort", sortFields);
			pipeline2 = Arrays.asList(whereForUser, groupByForUser, lookup, where, groupBy);
			pipeline = new ArrayList();
			Iterator var28 = pipeline2.iterator();

			while (var28.hasNext()) {
				p = (DBObject) var28.next();
				if (p != null && !p.keySet().isEmpty()) {
					pipeline.add(p);
				}
			}

			System.out.println("--------NOSQL---------->" + pipeline);
			startTime = new Date();
			output = coll.aggregate(pipeline,
					AggregationOptions.builder().maxTime(30L, TimeUnit.MINUTES).allowDiskUse(true).build());
			int c = 0;
			if (output.hasNext()) {
				DBObject obj = (DBObject) output.next();
				System.out.println(obj);
			}

			System.out.println("------RESULT NOSQL------>" + c);
		} else {
			for (String table : tables2Columnvalue.keySet()) {
				if (!table.equalsIgnoreCase(Tables.TAGS.getName().concat("S"))) {
					collection = this.getDB().getCollection(table.toUpperCase());
					pipeline = queryForsingleTable(table, tables2Columnvalue, tables2columnsGroupBy, collection, where,
							groupBy, unwind);
				}
			}

			System.out.println("NOSQL-------------------------------->" + pipeline);
			startTime = new Date();

			output = collection.aggregate(pipeline, AggregationOptions.builder().allowDiskUse(true).build());

		}

		endTime = new Date();
		return new Long(endTime.getTime() - startTime.getTime());
	}

	private List<DBObject> queryForsingleTable(String table,
			HashMap<String, HashMap<String, String>> tables2Columnvalue,
			HashMap<String, List<String>> tables2columnsGroupBy, DBCollection collection, DBObject where,
			DBObject groupBy, DBObject unwind) {
		List<DBObject> pipeline = null;

		HashMap<String, String> column2Value = tables2Columnvalue.get(table);
		// WHERE CONDITION
		if (column2Value != null && !column2Value.isEmpty()) {
			where = createWhereCondition(column2Value);
		}
		// GROUP BY
		if (!tables2columnsGroupBy.isEmpty()) {
			groupBy = createGroupByCondition(tables2columnsGroupBy.get(table));
			if (tables2columnsGroupBy.get(table).contains(Tables.TAGS.getName())
					|| tables2columnsGroupBy.get(table).contains(Tables.CATEGORY.getName())) {
				unwind = new BasicDBObject("$unwind", "$TagCategory");
			}
		}

		if (groupBy != null && where != null) {
			if (unwind != null) {
				pipeline = Arrays.asList(where, unwind, groupBy);
			} else {
				pipeline = Arrays.asList(where, groupBy);
			}
		} else if (groupBy != null && where == null) {
			if (unwind != null) {
				pipeline = Arrays.asList(unwind, groupBy);
			} else {
				pipeline = Arrays.asList(groupBy);
			}
		} else {
			pipeline = Arrays.asList(where);
		}
		return pipeline;
	}

	private DBObject createGroupByCondition(List<String> columnsGroupBy) {
		DBObject groupBy = null;
		Map<String, Object> dbObjIdMap = new HashMap<String, Object>();

		if (columnsGroupBy != null && !columnsGroupBy.isEmpty())
			for (String col : columnsGroupBy) {
				if (col.equalsIgnoreCase(Tables.TAGS.getName())) {
					dbObjIdMap.put(col, "$TagCategory.name");
				} else if (col.equalsIgnoreCase(Tables.CATEGORY.getName())) {
					dbObjIdMap.put(col, "$TagCategory.Category.name");
				} else {
					dbObjIdMap.put(col, "$".concat(col));
				}
			}

		if (!dbObjIdMap.isEmpty()) {
			groupBy = new BasicDBObject("$group", (new BasicDBObject("_id", new BasicDBObject(dbObjIdMap)))
					.append("count", new BasicDBObject("$sum", 1)));
		}

		return groupBy;
	}

	private DBObject createGroupByCondition2(List<String> columnsGroupBy) {
		DBObject groupBy = null;
		Map<String, Object> dbObjIdMap = new HashMap();
		if (columnsGroupBy != null && !columnsGroupBy.isEmpty())
			for (String col : columnsGroupBy) {
				dbObjIdMap.put(col, "$".concat("question.").concat(col));
			}

		if (!dbObjIdMap.isEmpty()) {
			groupBy = new BasicDBObject("$group", (new BasicDBObject("_id", new BasicDBObject(dbObjIdMap)))
					.append("count", new BasicDBObject("$sum", 1)));
		}

		return groupBy;
	}

	private DBObject createWhereCondition(HashMap<String, String> column2Value) {

		DBObject where = new BasicDBObject();
		for (String col : column2Value.keySet()) {
			if (col.contains("Date")) {
				where.put("$gte", new BasicDBObject(col, column2Value.get(col)));
			} else {
				where.put("$match", new BasicDBObject(col, column2Value.get(col)));
			}
//			if (col.contains("Date")) {
//				where.put(col, new BasicDBObject("$gte", column2Value.get(col)));
//			} else if (col.contains("Location")) {
//				where.put(col, new BasicDBObject("$match", column2Value.get(col)));
//			} else {
//				where.put(col, new BasicDBObject("$eq", column2Value.get(col)));
//			}
		}

		return where;
	}

	private DBObject createWhereConditionForGroupBy(HashMap<String, String> column2Value, DBObject where) {
		
		if (column2Value != null) {
			Iterator var4 = column2Value.keySet().iterator();

			while (var4.hasNext()) {
				String col = (String) var4.next();
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

package it.bigdata.ejb.service.cockroachdb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.LockMode;
import org.hibernate.Session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import it.bigdata.dto.constants.Tables;
import it.bigdata.dto.Category;
import it.bigdata.dto.Question;
import it.bigdata.dto.Tag;
import it.bigdata.dto.User;

/**
 * Session Bean implementation class CockroachdbCrudImpl
 */
@Stateless
@Local(CockroachdbCrud.class)
public class CockroachdbCrudImpl extends BaseService implements CockroachdbCrud {

	private String STACKOVERFLOW = "stackoverflow.";

	/**
	 * Default constructor.
	 */
	public CockroachdbCrudImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see CockroachdbCrud#delete(String)
	 */
	public Long delete(String tableSelected) {
		Date startTime = null;
		Date endTime = null;
			try {
				StringBuilder sb = new StringBuilder("DELETE FROM ").append(STACKOVERFLOW.concat(tableSelected.toLowerCase()))
						  .append(" WHERE fake = '1' ");
				
				String query = sb.toString();
				startTime = new Date();
				em.createNativeQuery(query).executeUpdate();
				endTime = new Date();
				
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			return new Long(endTime.getTime() - startTime.getTime());
	}

	/**
	 * @see CockroachdbCrud#update(int, HashMap<String,String>,
	 *      HashMap<String,String>, String)
	 */
	public Long update(int numTransactionSelected, HashMap<String, String> column2Value,HashMap<String, String> column2ValueWhere, String tableSelected) {
		Map<String, String> col2val = new HashMap(column2Value);
		Gson gson = new Gson();
		Date startTime = null;
		Date endTime = null;
		String obj;
		ObjectMapper mapper = new ObjectMapper();
		if (tableSelected.equalsIgnoreCase(Tables.USER.getName().toUpperCase())) {
			try {
				obj = gson.toJson(col2val);
				User ute=(User) mapper.readValue(obj, User.class);
				ute.setId(new Integer(0));
				ute.setFake("1");
				startTime = new Date();

				StringBuilder sb = new StringBuilder("UPDATE ")
						.append(STACKOVERFLOW.concat(Tables.USER.getName().toLowerCase()))
						.append(" SET location = ? , creationdate = ? , view = ?  ").append(" WHERE fake = ? ");

				String query = sb.toString();

				Query preparedStatement = em.createNativeQuery(query);
				preparedStatement.setParameter(1, "test");
				preparedStatement.setParameter(2, (ute.getCreationDate()!=null)? ute.getCreationDate() : startTime);
				preparedStatement.setParameter(3, ute.getViews());
				preparedStatement.setParameter(4, ute.getFake());
				preparedStatement.executeUpdate();


				endTime = new Date();
			} catch (Exception var15) {
				var15.printStackTrace();
			}
		}

		if (tableSelected.equalsIgnoreCase(Tables.QUESTION.getName())) {
			try {
				Question q = new Question();

				for (String col:column2Value.keySet()) {
					if (!((String) column2Value.get(col)).isEmpty()) {
							if (col.equals("Score")) {
								q.setScore(new Integer((String) column2Value.get(col)));
							}
							if (col.equals("Title")) {
								q.setTitle((String) column2Value.get(col));
							}
							if (col.equals("OwnerUserId")) {
								q.setOwnerUserId(new Integer((String) column2Value.get(col)));
							}
						}
				}
				q.setId(0);
				q.setFake("1");
				Tag tq = new Tag(1,	"test", new Category(1, "test"));
				List<Tag> tags = new ArrayList<Tag>();
				tags.add(tq);
				q.setTagQuestions(tags);
				startTime = new Date();
					StringBuilder sb = new StringBuilder("UPDATE ").append(STACKOVERFLOW.concat(Tables.QUESTION.getName().toLowerCase()))
							  .append(" SET Title= ?, OwnerUserId=?, Score=?, TagCategory=? ")
							  .append(" WHERE fake=? ");
					
					String query = sb.toString();
					
					Query preparedStatement = em.createNativeQuery(query);
					preparedStatement.setParameter(1, q.getTitle());
					preparedStatement.setParameter(2, q.getOwnerUserId());
					preparedStatement.setParameter(3, q.getScore());
					preparedStatement.setParameter(4, q.getTagQuestions());
					preparedStatement.setParameter(5, q.getFake());
					preparedStatement.executeUpdate();


				endTime = new Date();
			} catch (Exception var14) {
				var14.printStackTrace();
			}
		}

		return new Long(endTime.getTime() - startTime.getTime());
	}


	/**
	 * @see CockroachdbCrud#sql(HashMap<String,HashMap<String,String>>,
	 *      HashMap<String,List<String>>)
	 */
	public Long sql(HashMap<String, HashMap<String, String>> tables2Columnvalue, HashMap<String, List<String>> columnsGroupBy) {
		System.out.println("----ciccia");

		String select = "SELECT *  ";
		String query = "";
		String from = " FROM ";
		String groupby = " GROUP BY ";
		String where = " WHERE  1=1 ";
//		String having = " HAVING count(*) >7000";
		String having = " ";

		List<String> tables = new ArrayList<String>(tables2Columnvalue.keySet());
		for (String table : tables) {

			if (table.equalsIgnoreCase(Tables.TAGS.getName().concat("S"))
					|| table.equalsIgnoreCase(Tables.CATEGORY.getName())) {
				from = from.concat(" , jsonb_array_elements(stackoverflow.question.tagcategory) AS items ");
				if (tables2Columnvalue.get(table) != null && !tables2Columnvalue.get(table).isEmpty()) {
					where = where.concat(" and ");
					if (table.equalsIgnoreCase(Tables.TAGS.getName().concat("S"))) {
						where = where.concat("items->>\'id' ="
								.concat(tables2Columnvalue.get(table).get((Tables.TAGS.getCloumns()[0]))));
					} else {
						where = where.concat("tems-> \'Category\'->>\'id\' =".concat(tables2Columnvalue.get(table)
								.get(table.equalsIgnoreCase(Tables.CATEGORY.getCloumns()[0]))));
					}
				}
			} else {
				from = from.concat("  " + STACKOVERFLOW + table.toLowerCase() + "  ");
				// where part for this table
				for (String column : tables2Columnvalue.get(table).keySet()) {
					where = where.concat(" and ");
					if (column.equalsIgnoreCase("Location")) {
						where = where.concat(" " + column + " like '%"
								+ (String) ((HashMap) tables2Columnvalue.get(table)).get(column) + "%' ");
					} else {
						where = where.concat(" " + column + " = '"
								+ (String) ((HashMap) tables2Columnvalue.get(table)).get(column)+ "' " );
					}
				}
			}
			if (columnsGroupBy.get(table) != null) {
				for (String col : columnsGroupBy.get(table)) {
					if (col.equalsIgnoreCase(Tables.TAGS.getName())
							|| col.equalsIgnoreCase(Tables.CATEGORY.getName())) {
						groupby = groupby.concat("items->>\'name\' ,");
					} else {
						groupby = groupby.concat(col).concat(" ,");
					}
				}
				groupby = groupby.substring(0, groupby.length() - 1);
			}
		}

		if (!groupby.replace(" GROUP BY ", "").isEmpty()) {
			select = select.replace("*", "");
			select = select.concat(groupby.replace(" GROUP BY ", ""));
			select = select.concat(", count(*) ");
		}
		query = select.concat(from);
		query = query.concat(where);
		if (!groupby.replace(" GROUP BY ", "").isEmpty()) {
			query = query.concat(groupby);
			query = query.concat(having);
		}
		query = query.concat(";");
		System.out.println("NEWSQL-------------------------------->" + query);
		Date startTime = new Date();
		Query q = em.createNativeQuery(query);
		q.getResultList();
		Date endTime = new Date();
		System.out.println(endTime.getTime() - startTime.getTime());
		return new Long(endTime.getTime() - startTime.getTime());
	}

	/**
	 * @see CockroachdbCrud#insert(int, HashMap<String,String>, String)
	 */
	public Long insert(int numTransactionSelected, HashMap<String, String> column2Value, String tableSelected) {
		System.out.println("newSQL---------INIZIO----------"+new Date());
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> col2val = new HashMap<String, String>();

			for(String col:column2Value.keySet()) {
				col2val.put(col.substring(0, 1).toLowerCase().concat(col.substring(1, col.length())),	(String) column2Value.get(col));
			}

			Gson gson = new Gson();
			Date startTime = null;
			Date endTime = null;
			String obj;

//			em.unwrap(Session.class).doWork(connection -> {
//	            long maxTimeOutMil = TimeUnit.SECONDS.toMillis(5000000);
//	            connection.createStatement().execute("set statement_timeout = " + maxTimeOutMil);
//	        });
			
			if (tableSelected.equalsIgnoreCase(Tables.USER.getName().toUpperCase())) {
				try {
					obj = gson.toJson(col2val);
					User ute=(User) mapper.readValue(obj, User.class);
					ute.setId(new Integer(0));
					ute.setFake("1");
					startTime = new Date();
					StringBuilder sb = null;
					String query = null;
					Query preparedStatement = null;
					while ( numTransactionSelected > 0) {
						sb = new StringBuilder("INSERT INTO ").append(STACKOVERFLOW.concat(Tables.USER.getName().toLowerCase()))
								  .append("(Id, CreationDate, Location, View, fake) ")
								  .append("VALUES (?,?,?,?,?)");
						
						query = sb.toString();
						
						preparedStatement = em.createNativeQuery(query);
						preparedStatement.setParameter(1, ute.getId().toString());
						preparedStatement.setParameter(2, ute.getCreationDate());
						preparedStatement.setParameter(3, ute.getLocation());
						preparedStatement.setParameter(4, ute.getViews());
						preparedStatement.setParameter(5, ute.getFake());
						preparedStatement.executeUpdate();
						if(numTransactionSelected%10000==0) {
							System.out.println("----- "+numTransactionSelected);
//						em.clear();
						}
						--numTransactionSelected;
					}

					endTime = new Date();
				} catch (Exception var15) {
					System.out.println("newSQL---------FINE----------"+new Date());
					var15.printStackTrace();
				}
			}

			if (tableSelected.equalsIgnoreCase(Tables.QUESTION.getName())) {
				try {
					Question q = new Question();

					for (String col:column2Value.keySet()) {
						if (!((String) column2Value.get(col)).isEmpty()) {
								if (col.equals("Score")) {
									q.setScore(new Integer((String) column2Value.get(col)));
								}
								if (col.equals("Title")) {
									q.setTitle((String) column2Value.get(col));
								}
								if (col.equals("OwnerUserId")) {
									q.setOwnerUserId(new Integer((String) column2Value.get(col)));
								}
							}
					}
					q.setId(0);
					q.setFake("1");
					Tag tq = new Tag(1,	"test", new Category(1, "test"));
					List<Tag> tags = new ArrayList<Tag>();
					tags.add(tq);
					q.setTagQuestions(tags);
					startTime = new Date();
					while ( numTransactionSelected > 0) {
						StringBuilder sb = new StringBuilder("INSERT INTO ").append(STACKOVERFLOW.concat(Tables.QUESTION.getName().toLowerCase()))
								  .append("(Id, OwnerUserId, Score, TagCategory, Title, fake) ")
								  .append("VALUES (?,?,?,?,?,?)");
						
						String query = sb.toString();
						getSession(em).setJdbcBatchSize(1000);
						Query preparedStatement = em.createNativeQuery(query);
						preparedStatement.setParameter(1, q.getId());
						preparedStatement.setParameter(2, q.getOwnerUserId());
						preparedStatement.setParameter(3, q.getScore());
						preparedStatement.setParameter(4, gson.toJson(q.getTagQuestions()));
						preparedStatement.setParameter(5, q.getTitle());
						preparedStatement.setParameter(6, q.getFake());
						
						preparedStatement.executeUpdate();
						
						
						
						--numTransactionSelected;
					}

					endTime = new Date();
				} catch (Exception var14) {
					var14.printStackTrace();
				}
			}

			return new Long(endTime.getTime() - startTime.getTime());
		}

}

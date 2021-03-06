package it.bigdata.ejb.service.mysql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.bigdata.dto.User;
import it.bigdata.dto.constants.Tables;
import it.bigdata.ejb.service.mysql.entity.Category;
import it.bigdata.ejb.service.mysql.entity.Question;
import it.bigdata.ejb.service.mysql.entity.Tag;
import it.bigdata.ejb.service.mysql.entity.TagQuestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Stateless
@Local({ MySqlCrud.class })
@LocalBean
public class MySqlCrudImp extends BaseService implements MySqlCrud {
	public Long insert(int numTransactionSelected, HashMap<String, String> column2Value, String tableSelected) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> col2val = new HashMap();
		Iterator var7 = column2Value.keySet().iterator();

//		while (var7.hasNext()) {
//			String i = (String) var7.next();
//			col2val.put(i.substring(0, 1).toLowerCase().concat(i.substring(1, i.length())),
//					(String) column2Value.get(i));
//		}

		Gson gson = new Gson();
		Date startTime = null;
		Date endTime = null;
		String obj;
		if (tableSelected.equalsIgnoreCase(Tables.CATEGORY.getName())) {
			try {
				obj = gson.toJson(column2Value);
				Category c = (Category) mapper.readValue(obj, Category.class);

				for (startTime = new Date(); numTransactionSelected > 0; --numTransactionSelected) {
					this.em.persist(c);
//					this.em.flush();
				}

				endTime = new Date();
			} catch (Exception var17) {
				var17.printStackTrace();
			}
		}

		if (tableSelected.equalsIgnoreCase(Tables.TAGS.getName())) {
			try {
				obj = gson.toJson(column2Value);
				Tag t = (Tag) mapper.readValue(obj, Tag.class);
				Category categoria = new Category();
				categoria.setId(0);
				categoria.setCategoryName("PROVA");
				t.setCategory(categoria);

				for (startTime = new Date(); numTransactionSelected > 0; --numTransactionSelected) {
					this.em.persist(t);
//					this.em.flush();
				}

				endTime = new Date();
			} catch (Exception var16) {
				var16.printStackTrace();
			}
		}

		if (tableSelected.equalsIgnoreCase(Tables.USER.getName())) {
			try {
				obj = gson.toJson(column2Value);
				Gson gsonB = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				User ute = gsonB.fromJson(obj, User.class);
				System.out.println(ute);
				startTime = new Date();
				while (numTransactionSelected > 0) {
					it.bigdata.ejb.service.mysql.entity.User u = new it.bigdata.ejb.service.mysql.entity.User();

					u.setCreationDate(ute.getCreationDate());
					u.setLocation(ute.getLocation());
					u.setFake("1");
					this.em.persist(u);
//					this.em.flush();

					--numTransactionSelected;
				}

				endTime = new Date();
			} catch (Exception var15) {
				var15.printStackTrace();
			}
		}

		if (tableSelected.equalsIgnoreCase(Tables.QUESTION.getName())) {
			try {
				gson.toJson(column2Value);
				Question q = new Question();
				Iterator var12 = column2Value.keySet().iterator();

				while (var12.hasNext()) {
					String col = (String) var12.next();
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

				q.setFake("1");
				TagQuestion tq = new TagQuestion();
				tq.setFake("1");
				tq.setTag_id(new Integer((String) column2Value.get("Tag")));
				tq.setQuestion(q);
				List<TagQuestion> tags = new ArrayList<TagQuestion>();
				tags.add(tq);
				q.setTagQuestions(tags);

				for (startTime = new Date(); numTransactionSelected > 0; --numTransactionSelected) {
					this.em.persist(q);
					this.em.flush();
				}

				endTime = new Date();
			} catch (Exception var14) {
				var14.printStackTrace();
			}
		}

		return new Long(endTime.getTime() - startTime.getTime());
	}

	public Long update(int numTransactionSelected, HashMap<String, String> column2Value,
			HashMap<String, String> column2ValueWhere, String tableSelected) {

		Map<String, String> col2val = new HashMap(column2Value);
		Gson gson = new Gson();
		Date startTime = null;
		Date endTime = null;
		ObjectMapper mapper = new ObjectMapper();
		String obj;
		if (tableSelected.equalsIgnoreCase(Tables.CATEGORY.getName())) {
			try {
				gson.toJson(col2val);
				Category c = new Category();
				c.setFake("1");
				Criteria criteria = this.getSession(this.em).createCriteria(Category.class);
				criteria.add(Restrictions.eq("fake", c.getFake()));
				int j = 0;
				List<Category> list = criteria.list();

				for (Iterator var16 = list.iterator(); var16.hasNext(); ++j) {
					Category i = (Category) var16.next();
					i.setCategoryName("prova" + j);
					this.em.persist(i);
					this.em.flush();
				}

				endTime = new Date();
			} catch (Exception var20) {
				var20.printStackTrace();
			}
		}

		Criteria criteria;
		List list;
		Iterator var30;
		if (tableSelected.equalsIgnoreCase(Tables.TAGS.getName())) {
			try {
				Tag t = new Tag();
				t.setFake("1");
				criteria = this.getSession(this.em).createCriteria(Tag.class);
				criteria.add(Restrictions.eq("fake", t.getFake()));
				int j = 0;
				startTime = new Date();
				list = criteria.list();

				for (var30 = list.iterator(); var30.hasNext(); ++j) {
					Tag i = (Tag) var30.next();
					i.setTagName("prova" + j);
					this.em.persist(i);
					this.em.flush();
				}

				endTime = new Date();
			} catch (Exception var19) {
				var19.printStackTrace();
			}
		}

		boolean j;
		if (tableSelected.equalsIgnoreCase(Tables.USER.getName())) {
			try {
				obj = gson.toJson(column2Value);
				Gson gsonB = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				User ute = gsonB.fromJson(obj, User.class);
				ute.setFake("1");
				System.out.println(ute);


				StringBuilder sb = new StringBuilder("UPDATE ").append(Tables.USER.getName().toLowerCase())
						.append(" SET Location = ? , creationDate = ? , Views = ?  ").append(" WHERE fake = ? ");

				String query = sb.toString();

				Query preparedStatement = em.createNativeQuery(query);
				preparedStatement.setParameter(1, "test");
				preparedStatement.setParameter(2, (ute.getCreationDate() != null) ? ute.getCreationDate() : startTime);
				preparedStatement.setParameter(3, (ute.getViews() != 0) ? ute.getViews() : column2Value.get("Views"));
				preparedStatement.setParameter(4, ute.getFake());
				startTime = new Date();
				preparedStatement.executeUpdate();

				endTime = new Date();
			} catch (Exception var18) {
				var18.printStackTrace();
			}
		}

		if (tableSelected.equalsIgnoreCase(Tables.QUESTION.getName())) {
			try {
				Question q = new Question();
				q.setFake("1");
				criteria = this.getSession(this.em).createCriteria(Question.class);
				criteria.add(Restrictions.eq("fake", q.getFake()));
				j = false;
				startTime = new Date();
				list = criteria.list();
				var30 = list.iterator();

				while (var30.hasNext()) {
					Question i = (Question) var30.next();
					this.em.persist(i);
					this.em.flush();
				}

				endTime = new Date();
			} catch (Exception var17) {
				var17.printStackTrace();
			}
		}

		return new Long(endTime.getTime() - startTime.getTime());
	}



	public Long delete(String tableSelected) {
		Date startTime = new Date();
		this.em.createNativeQuery("delete from " + tableSelected + " where fake = '1' ").executeUpdate();
		Date endTime = new Date();
		return new Long(endTime.getTime() - startTime.getTime());
	}

	public Long sql(HashMap<String, HashMap<String, String>> tables2Columnvalue,
			HashMap<String, List<String>> columnsGroupBy) {
		String select = "SELECT *  ";
		String query = "";
		String from;
		String groupby;

		from = " FROM ";
		String where = " WHERE  1=1 ";
		groupby = " GROUP BY ";

		for (String table : tables2Columnvalue.keySet()) {
			// from part
			if (!(table.replace("S", "")).equalsIgnoreCase(Tables.TAGS.getName())) {
				from = from.concat("  " + table.toLowerCase() + " ,");
			} else {
				from = from.concat("  " + table.replace("S", "").toLowerCase() + " ,");
			}

			// where part for this table
			for (String column : tables2Columnvalue.get(table).keySet()) {
				where = where.concat(" and ");
				if (column.equalsIgnoreCase("Location")) {
					where = where.concat(" " + column + " like '%"
							+ (String) ((HashMap) tables2Columnvalue.get(table)).get(column) + "%' ,");
				} else if (column.equalsIgnoreCase(Tables.QUESTION.getCloumns()[4])) {
					where = where.concat(" " + "and tag.Id" + " = "
							+ (String) ((HashMap) tables2Columnvalue.get(table)).get(column) + " ,");
				} else {
					where = where.concat(" " + column + " = '"
							+ (String) ((HashMap) tables2Columnvalue.get(table)).get(column) + "' ,");
				}
			}
			// groupby for this table
			if (columnsGroupBy.get(table) != null)
				for (String col : columnsGroupBy.get(table)) {
					if (col.equalsIgnoreCase(Tables.TAGS.getName())) {
						groupby = groupby
								.concat((col.toLowerCase() + ".").concat(Tables.TAGS.getCloumns()[0]).toLowerCase())
								.concat(" ,");
					} else {
						groupby = groupby.concat(col).concat(" ,");
					}
				}
		}
		if (tables2Columnvalue.keySet().contains(Tables.QUESTION.getName().toUpperCase())
				&& tables2Columnvalue.keySet().contains(Tables.TAGS.getName().toUpperCase().concat("S"))) {
			from = from.concat("tag_question ");
		} else {
			from = from.substring(0, from.length() - 1);
		}

		if (columnsGroupBy.containsKey(Tables.QUESTION.getName().toUpperCase())
				&& columnsGroupBy.get(Tables.QUESTION.getName().toUpperCase()).contains(Tables.TAGS.getName())
				|| where.contains("tag.Id")) {

			where = where.substring(0, where.length() - 1)
					.concat(" and tag_question.IdQuestion = question.id and tag.id=tag_question.IdTag ");
		} else {

			where = where.substring(0, where.length() - 1);
		}

		if (groupby.substring(groupby.length() - 1).equals(",")) {
			groupby = groupby.substring(0, groupby.length() - 1);
		}
		if (!groupby.replace(" GROUP BY ", "").isEmpty()) {
			select = select.replace("*", "");
			select = select.concat(groupby.replace(" GROUP BY ", ""));
			select = select.concat(" , COUNT(*)");
		}

		if (select.substring(select.length() - 1).equals(",")) {
			select = select.substring(0, select.length() - 1);
		}
		query = select.concat(from);

		query = query.concat(where);

		if (!groupby.replace(" GROUP BY ", "").isEmpty()) {

			query = query.concat(groupby);
		}

		System.out.println("---------SQL--------->" + query);
		Date startTime = new Date();
		this.em.createNativeQuery(query).getResultList();
		Date endTime = new Date();
		return new Long(endTime.getTime() - startTime.getTime());
	}
}

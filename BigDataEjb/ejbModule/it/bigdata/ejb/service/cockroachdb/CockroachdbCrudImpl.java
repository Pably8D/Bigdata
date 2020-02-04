package it.bigdata.ejb.service.cockroachdb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import it.bigdata.dto.constants.Tables;
import it.bigdata.ejb.service.mysql.entity.Category;
import it.bigdata.ejb.service.mysql.entity.Question;
import it.bigdata.ejb.service.mysql.entity.Tag;
import it.bigdata.ejb.service.mysql.entity.TagQuestion;
import it.bigdata.ejb.service.mysql.entity.User;

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
	public Long delete(String var1) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see CockroachdbCrud#update(int, HashMap<String,String>,
	 *      HashMap<String,String>, String)
	 */
	public Long update(int var1, HashMap<String, String> var2, HashMap<String, String> var3, String var4) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see CockroachdbCrud#sql(HashMap<String,HashMap<String,String>>,
	 *      HashMap<String,List<String>>)
	 */
	public Long sql(HashMap<String, HashMap<String, String>> tables2Columnvalue,
			HashMap<String, List<String>> columnsGroupBy) {
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
						where = where.concat(" " + column + " = "
								+ (String) ((HashMap) tables2Columnvalue.get(table)).get(column) );
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
		System.out.println(q.getResultList());
		Date endTime = new Date();
		System.out.println(endTime.getTime() - startTime.getTime());
		return new Long(endTime.getTime() - startTime.getTime());
	}

	/**
	 * @see CockroachdbCrud#insert(int, HashMap<String,String>, String)
	 */
	public Long insert(int numTransactionSelected, HashMap<String, String> column2Value, String tableSelected) {
		return null;
	}

}

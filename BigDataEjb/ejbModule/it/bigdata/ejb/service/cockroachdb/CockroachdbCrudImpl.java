package it.bigdata.ejb.service.cockroachdb;

import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Session Bean implementation class CockroachdbCrudImpl
 */
@Stateless
@Local(CockroachdbCrud.class)
public class CockroachdbCrudImpl extends BaseService implements CockroachdbCrud {

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
     * @see CockroachdbCrud#update(int, HashMap<String,String>, HashMap<String,String>, String)
     */
    public Long update(int var1, HashMap<String,String> var2, HashMap<String,String> var3, String var4) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see CockroachdbCrud#sql(HashMap<String,HashMap<String,String>>, HashMap<String,List<String>>)
     */
    public Long sql(HashMap<String,HashMap<String,String>> var1, HashMap<String,List<String>> var2) {
    	System.out.println("----ciccia");
//		Query q= getSession(em).createQuery("select count (*) from stackoverflow.user");
//    	Query q= getSession(em).createQuery("select count (*) from stackoverflow.user");//query string
    	Query q=  em.createNativeQuery("select count (*) from stackoverflow.user");
		
		System.out.println("----ciccia"+q.getResultList().get(0));
			return 1L;
    }

	/**
     * @see CockroachdbCrud#insert(int, HashMap<String,String>, String)
     */
    public Long insert(int var1, HashMap<String,String> var2, String var3) {
        // TODO Auto-generated method stub
			return null;
    }

}

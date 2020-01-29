package it.bigdata.ejb.service.cockroachdb;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;

public class BaseService {
	protected final Integer FLAG_ATTIVO = 1;
	protected final Integer FLAG_DISATTIVO = 0;
	protected final String SUPER_USER_SIGLA = "SU";
	protected final Integer CODICE_IMMAGINE_TIPO_RITRATTO = 1;
	protected final Integer CODICE_IMMAGINE_TIPO_FOTOGRAFIA = 2;
	protected final Integer CODICE_IMMAGINE_TIPO_RADIOGRAFIA = 3;
	protected final Integer MAX_RESULT = 5;
	@PersistenceContext(unitName = "BigDataEjbCockroach")
	EntityManager em;

	protected Session getSession(EntityManager em) {
		Session s= (Session) em.getDelegate();
		return s;
		
	}
//    private SessionUtil(String dbAddr) {
//
//
//        Configuration configuration = new Configuration();
//        
//
//        if (dbAddr != null) {
//            // Most drivers expect the user in a connection to be specified like:
//            //   postgresql://<user>@host:port/db
//            // but the PGJDBC expects the user as a parameter like:
//            //   postgresql://host:port/db?user=<user>
//            Pattern p = Pattern.compile("postgresql://((\\w+)@).*");
//            Matcher m = p.matcher(dbAddr);
//            if (m.matches()) {
//                String userPart = m.group(1);
//                String user = m.group(2);
//
//
//                String sep = "?";
//                if (dbAddr.contains("?")) {
//                    sep = "&";
//                }
//                dbAddr = String.format("%s%suser=%s", dbAddr.replace(userPart, ""), sep, user);
//            }
//
//            // Add the "jdbc:" prefix to the address and replace in configuration.
//            dbAddr = "jdbc:" + dbAddr;
//            configuration.setProperty("hibernate.connection.url", dbAddr);
//        }
//
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .applySettings(configuration.getProperties())
//                .build();
//
//        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//    }
}

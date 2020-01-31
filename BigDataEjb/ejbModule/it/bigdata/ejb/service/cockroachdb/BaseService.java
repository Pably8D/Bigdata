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

}

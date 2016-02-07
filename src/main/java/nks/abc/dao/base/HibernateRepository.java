package nks.abc.dao.base;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class HibernateRepository {

	protected final Class<?> domainClass;
	private SessionFactory sessionFactory;

	public HibernateRepository(Class<?>domainClass) {
		this.domainClass = domainClass;
	}

	@Autowired
	public void sessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected Criteria getCriteria() {
		return getSession().createCriteria(domainClass);
	}

}
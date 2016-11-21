package nks.abc.dao.specification.chunks;


import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;


public class EqualSpecification extends SingleSpecification {
	
	private String fieldName;
	private Object value;
	
	public EqualSpecification(){
		super();
	}
	
	public EqualSpecification(String field, Object value) {
		setCriteria(field, value);
	}

	public void setCriteria(String field, Object value) {
		this.fieldName = field;
		this.value = value;
	}

	@Override
	public Criterion toCriterion(Class<?> repositoryDomainClass) {
		SimpleExpression crit = Restrictions.eq(buildFieldName(repositoryDomainClass), value);
		return crit;
	}

	public String buildFieldName(Class<?> repositoryDomainClass) {
		if(getAlias() != null && getAlias().isNeeded(repositoryDomainClass)) {
			return this.getAlias().getAliasName() + "." + this.fieldName;
		}
		return this.fieldName; 
	}

}

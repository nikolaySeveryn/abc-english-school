package nks.abc.dao.newspecification.base;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;


public class EqualSpecification extends SimpleSpecification {
	
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
	public Criterion toCriterion() {
		String fieldName = null;
		if(getAlias() != null && getAlias().isNeeded()){
			fieldName = this.getAlias().getName() + "." + this.fieldName;
		}
		else{
			fieldName = this.fieldName; 
		}
		return Restrictions.eq(fieldName, value);
	}

}

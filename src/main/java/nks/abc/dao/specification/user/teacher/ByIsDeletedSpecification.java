package nks.abc.dao.specification.user.teacher;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import nks.abc.dao.base.CriterionSpecification;
import nks.abc.dao.base.HQLSpecification;

public class ByIsDeletedSpecification implements HQLSpecification {

	private Boolean isDeleted;
	
	public ByIsDeletedSpecification(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@Override
	public Map<String, Object> getParameters() {
		Map<String, Object> params = new HashMap<String, Object>(){{
			put("isDeleted", isDeleted);
		}};
		return Collections.unmodifiableMap(params);
	}

	@Override
	public String toCriteria() {
		
		String query = "SELECT t "
				+ "FROM Teacher t "
				+ "LEFT OUTER JOIN t.accountInfo a "
				+ "WHERE a.isDeleted = :isDeleted";
		return query;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((isDeleted == null) ? 0 : isDeleted.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ByIsDeletedSpecification other = (ByIsDeletedSpecification) obj;
		if (isDeleted == null) {
			if (other.isDeleted != null)
				return false;
		} else if (!isDeleted.equals(other.isDeleted))
			return false;
		return true;
	}

}

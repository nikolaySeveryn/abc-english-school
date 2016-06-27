package nks.abc.dao.base.interfaces;

import java.util.Map;

public interface HQLSpecification {
	String toCriteria();
	Map<String,Object> getParameters();
}

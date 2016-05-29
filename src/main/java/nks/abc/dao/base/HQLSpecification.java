package nks.abc.dao.base;

import java.util.Map;

public interface HQLSpecification {
	String toCriteria();
	Map<String,Object> getParameters();
}

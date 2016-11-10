package nks.abc.domain.user;

import java.util.List;
import java.util.Set;

public interface Student extends User{

	Long getAccountId();
	
	Parent getParent();
	void setParent(Parent parent);

	Set<Group> getGroups();
	void setGroups(Set<? extends Group> group);
	List<Group> getGroupsList();
	void setGroupsList(List<Group> groups);

	Integer getDiscount();
	void setDiscount(Integer discount);

	Integer getMoneyBalance();
	void setMoneyBalance(Integer moneyBalance);
}
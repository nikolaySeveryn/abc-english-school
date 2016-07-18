package nks.abc.bl.view.object.objects.user;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class StudentView extends UserView {

	private Long id;
	private Long accountId;
	private ParentInfoView parent;
	private List<GroupView> groups;
	@Min(0)
	@Max(100)
	private Integer discount;
	private Integer moneyBalance;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long id) {
		this.accountId = id;
	}
	public ParentInfoView getParent() {
		return parent;
	}
	public void setParent(ParentInfoView parent) {
		this.parent = parent;
	}
	public List<GroupView> getGroups() {
		return groups;
	}
	public void setGroups(List<GroupView> groups) {
		this.groups = groups;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getMoneyBalance() {
		return moneyBalance;
	}
	public void setMoneyBalance(Integer moneyBalance) {
		this.moneyBalance = moneyBalance;
	}
}

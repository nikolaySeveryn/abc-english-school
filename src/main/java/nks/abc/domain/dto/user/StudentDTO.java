package nks.abc.domain.dto.user;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class StudentDTO extends UserDTO {

	private Long id;
	private Long accountId;
	private ParentInfoDTO parent;
	private List<GroupDTO> groups;
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
	public ParentInfoDTO getParent() {
		return parent;
	}
	public void setParent(ParentInfoDTO parent) {
		this.parent = parent;
	}
	public List<GroupDTO> getGroups() {
		return groups;
	}
	public void setGroups(List<GroupDTO> groups) {
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

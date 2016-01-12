package nks.abc.dao.impl;

import org.springframework.stereotype.Repository;

import nks.abc.dao.StaffDAO;
import nks.abc.domain.entity.user.Staff;

@Repository
public class StaffDAOImpl extends BaseDAOImpl<Staff, Long> implements StaffDAO {

	public StaffDAOImpl() {
		super(Staff.class);
	}
}

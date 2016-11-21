package nks.abc.dao.repository.user;

import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.domain.user.Staff;

@Repository
public class StaffRepository extends BaseRepositoryImpl<Staff> {

	public StaffRepository() {
		super(Staff.class);
	}

}

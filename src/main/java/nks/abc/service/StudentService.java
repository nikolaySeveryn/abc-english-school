package nks.abc.service;

import nks.abc.domain.dto.user.StudentDTO;

public interface StudentService {
	StudentDTO getById(Long id);
	void save(StudentDTO student);
	void delete(Long... ids);
}

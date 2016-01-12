package nks.abc.service;

import java.util.List;

import nks.abc.domain.dto.user.StaffDTO;


public interface StaffService {
	
	public void insert(StaffDTO employee);
	public void update(StaffDTO employee);
	public void delete(Long id);
	public StaffDTO findById(Long id);
//	public List<StaffDTO> getAll(Integer offset, Integer limit);
	public List<StaffDTO> getAll();
}

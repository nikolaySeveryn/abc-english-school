package nks.abc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.dao.StaffDAO;
import nks.abc.domain.dto.convertor.user.StaffDTOConvertor;
import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.domain.entity.user.Staff;
import nks.abc.service.StaffService;

@Service("staffService")
@Transactional(readOnly=true)
public class StaffServiceImpl implements StaffService {
	
	@Autowired
	private StaffDAO staffDAO;

	@Override
	@Transactional(readOnly=false)
	public void insert(StaffDTO employeeDTO) {
		//TODO: unque login
		Staff employee = convertFromDTO(employeeDTO);
		employee.updatePassword(employeeDTO.getPassword());
		staffDAO.insert(employee);
	}

	@Override
	@Transactional(readOnly=false)
	public void update(StaffDTO employeeDTO) {
		System.out.println("update :" + convertFromDTO(employeeDTO));
		staffDAO.update(convertFromDTO(employeeDTO));
	}
	
	private Staff convertFromDTO(StaffDTO employeeDTO){
		Staff employee = new Staff();
		StaffDTOConvertor.toEntity(employeeDTO, employee);
		return employee;
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(StaffDTO book) {
		// TODO: resrict the removing of the last admin
		
	}

	@Override
	public StaffDTO findById(Long id) {
		StaffDTO employee = new StaffDTO();
		StaffDTOConvertor.toDTO(staffDAO.findById(id), employee);
		return employee;
	}

	@Override
	public List<StaffDTO> getAll() {
		List<StaffDTO> allStaff = new ArrayList<StaffDTO>();
		System.out.println("entity size: " + staffDAO.getAll().size());
		StaffDTOConvertor.toDTO(staffDAO.getAll(), allStaff);
		System.out.println("dto size: " + allStaff.size());
		return  allStaff;
	}


}

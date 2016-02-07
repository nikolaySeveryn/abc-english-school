package nks.abc.test.domain.entity.user;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import nks.abc.dao.base.HibernateSpecification;
import nks.abc.dao.repository.user.AccountRepository;
import nks.abc.dao.repository.user.AdministratorRepository;
import nks.abc.dao.repository.user.TeacherRepository;
import nks.abc.dao.specification.user.account.AccountInfoSpecificationFactory;
import nks.abc.dao.specification.user.administrator.AdministratorSpecificationFactory;
import nks.abc.dao.specification.user.teacher.TeacherSpecificationFactory;
import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.domain.entity.user.AccountInfo;
import nks.abc.domain.entity.user.Administrator;
import nks.abc.domain.entity.user.Teacher;
import nks.abc.service.exception.NoCurrentUserException;
import nks.abc.service.exception.NoUserLoginException;
import nks.abc.service.exception.NoIdException;
import nks.abc.service.exception.RightsDeprivingException;
import nks.abc.service.exception.ServiceDisplayedErorr;
import nks.abc.service.impl.StaffServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StaffServiceTest {
	
	@Mock
	private AdministratorRepository adminDAO;
	@Mock
	private TeacherRepository teacherDAO;
	@Mock
	private AccountRepository accountDAO;
	
	@InjectMocks
	private StaffServiceImpl service = new StaffServiceImpl();
	
	private StaffDTO emptyEmployee;
	private StaffDTO employee;
	private String passHash;
	
	@Before
	public void initMocks(){
		when(adminDAO.getSpecificationFactory()).thenReturn(new AdministratorSpecificationFactory());
		when(teacherDAO.getSpecificaitonFactory()).thenReturn(new TeacherSpecificationFactory());
		when(accountDAO.getSpecificationFactory()).thenReturn(new AccountInfoSpecificationFactory());
	}
	
	@Before
	public void init(){
		emptyEmployee = new StaffDTO();
		emptyEmployee.setIsAdministrator(false);
		emptyEmployee.setIsTeacher(false);
		employee = new StaffDTO();
		
		employee.setBirthday(new Date());
		employee.setEmail("email@mail.ma");
		employee.setFirstName("fname");
		employee.setLogin("login");
		employee.setPassword("password");
		employee.setPatronomic("patronomic");
		employee.setPhoneNum("12345654321");
		employee.setSirName("sirname");
		employee.setIsAdministrator(false);
		employee.setIsTeacher(false);
		passHash = "X03MO1qnZdYdgyfeuILPmQ==";
	}
	

	/*
	 *  add tests
	 */
	@Test(expected=NoUserLoginException.class)
	public void insertStaffWithoutLogin(){
		service.add(emptyEmployee);
	}
	
	@Test(expected=ServiceDisplayedErorr.class)
	public void existingUser(){
		String userName = "username";
		emptyEmployee.setLogin(userName);
		when(accountDAO.uniqueQuery(accountDAO.getSpecificationFactory().byLogin(userName)))
				.thenReturn(new AccountInfo());
		service.add(emptyEmployee);
	}
	
	@Test(expected=ServiceDisplayedErorr.class)
	public void noTeahcerOrAdmin(){
		String userName = "login";
		emptyEmployee.setLogin(userName);
		when(accountDAO.uniqueQuery(accountDAO.getSpecificationFactory().byLogin(userName)))
			.thenReturn(null);
		emptyEmployee.setIsAdministrator(false);
		emptyEmployee.setIsTeacher(false);
		service.add(emptyEmployee);
	}
	
	@Test
	public void correctTeacher(){
		ArgumentCaptor<Teacher> teacherCaptor = ArgumentCaptor.forClass(Teacher.class);
		employee.setIsTeacher(true);
		employee.setIsAdministrator(false);
		service.add(employee);
		verify(teacherDAO).insert(teacherCaptor.capture());
		compareAccountInfo(employee, teacherCaptor.getValue().getAccountInfo());
		assertFalse(teacherCaptor.getValue().getAccountInfo().isAdministrator());
		assertTrue(teacherCaptor.getValue().getAccountInfo().isTeacher());
		verify(adminDAO, never()).insert(any(Administrator.class));
	}
	
	@Test
	public void correctAdmin(){
		ArgumentCaptor<Administrator> adminCaptor = ArgumentCaptor.forClass(Administrator.class);
		employee.setIsTeacher(false);
		employee.setIsAdministrator(true);
		service.add(employee);
		verify(adminDAO).insert(adminCaptor.capture());
		compareAccountInfo(employee, adminCaptor.getValue().getAccountInfo());
		assertTrue(adminCaptor.getValue().getAccountInfo().isAdministrator());
		assertFalse(adminCaptor.getValue().getAccountInfo().isTeacher());
		
		verify(teacherDAO, never()).insert(any(Teacher.class));
	}
	
	@Test
	public void correctTeacherAndAdmin(){
		ArgumentCaptor<Teacher> teacherCaptor = ArgumentCaptor.forClass(Teacher.class);
		ArgumentCaptor<Administrator> adminCaptor = ArgumentCaptor.forClass(Administrator.class);
		employee.setIsTeacher(true);
		employee.setIsAdministrator(true);
		service.add(employee);
		verify(teacherDAO).insert(teacherCaptor.capture());
		verify(adminDAO).insert(adminCaptor.capture());
		assertEquals(teacherCaptor.getValue().getAccountInfo(), adminCaptor.getValue().getAccountInfo());
		assertSame(teacherCaptor.getValue().getAccountInfo(), adminCaptor.getValue().getAccountInfo());
		
		compareAccountInfo(employee, teacherCaptor.getValue().getAccountInfo());
		assertTrue(teacherCaptor.getValue().getAccountInfo().isAdministrator());
		assertTrue(teacherCaptor.getValue().getAccountInfo().isTeacher());
		
	}
	
	/*
	 * update test
	 */
	@Test(expected=NoCurrentUserException.class)
	public void emptyCurentUserNameUpdate(){
		service.update(emptyEmployee, null);
		verifyThatUpdateDoesNotCalled();
	}
	
	@Test(expected=NoCurrentUserException.class)
	public void noUsername(){
		service.update(employee, "");
		verifyThatUpdateDoesNotCalled();
	}


	
	@Test(expected=NoIdException.class)
	public void updateWithoutId(){
		service.update(emptyEmployee, "user");
		verifyThatUpdateDoesNotCalled();
	}
	
	@Test(expected=RightsDeprivingException.class)
	public void updateDeprivingRights(){
		String currentUserLogin = "user";
		employee.setId(4L);
		employee.setIsAdministrator(false);
		AccountInfo account = new AccountInfo();
		account.setAccountId(4L);
		when(
				accountDAO.uniqueQuery(accountDAO.getSpecificationFactory().byLoginAndDeleted(currentUserLogin,false))
			).thenReturn(account);
		
		service.update(employee, currentUserLogin);
		
		verifyThatUpdateDoesNotCalled();
	}
	
	// TODO: need more update tests
	@Test
	public void UpdateSuccess(){
		employee.setId(4L);
		String currentUserLogin = "user";
		AccountInfo currentUser = new AccountInfo();
		currentUser.setLogin(currentUserLogin);
		currentUser.setAccountId(3L);
		when(
				accountDAO.uniqueQuery(accountDAO.getSpecificationFactory().byLoginAndDeleted(currentUserLogin,false))
				).thenReturn(currentUser);
		employee.setIsAdministrator(true);
		employee.setIsTeacher(true);
		service.update(employee, currentUserLogin);
		ArgumentCaptor<Teacher> teacherCaptor = ArgumentCaptor.forClass(Teacher.class);
		ArgumentCaptor<Administrator>adminCaptor = ArgumentCaptor.forClass(Administrator.class);
		
		verify(teacherDAO).update(teacherCaptor.capture());
		verify(adminDAO).update(adminCaptor.capture());
		
		assertSame(teacherCaptor.getValue().getAccountInfo(), adminCaptor.getValue().getAccountInfo());
		compareAccountInfoWithoutPassword(employee, teacherCaptor.getValue().getAccountInfo());
		compareAccountInfoWithoutPassword(employee, adminCaptor.getValue().getAccountInfo());
	}
	
	
	@Test(expected=NoCurrentUserException.class)
	public void DeleteNoCurrentUserEmptyName(){
		service.delete(5L, "");
	}
	
	@Test(expected=NoCurrentUserException.class)
	public void DeleteCurrentUserNotFound(){
		service.delete(5L, "sedsd");
	}
	
	@Test(expected=RightsDeprivingException.class)
	public void deleteHimself(){
		String currentUserLogin = "user";
		AccountInfo currentUser = new AccountInfo();
		currentUser.setAccountId(5L);
		currentUser.setLogin(currentUserLogin);
		when(accountDAO.uniqueQuery(accountDAO.getSpecificationFactory().byLoginAndDeleted(currentUserLogin,false))).thenReturn(currentUser);
		service.delete(5L, currentUserLogin);
	}
	
	
	private void compareAccountInfoWithoutPassword(StaffDTO dto, AccountInfo account){
		assertEquals(dto.getBirthday(), account.getBirthday());
		assertEquals(dto.getEmail(), account.getEmail());
		assertEquals(dto.getFirstName(), account.getFirstName());
		assertEquals(dto.getIsAdministrator(), account.isAdministrator());
		assertEquals(dto.getIsTeacher(), account.isTeacher());
		assertFalse(account.isStudent());
		assertEquals(dto.getLogin(), account.getLogin());
		assertEquals(dto.getPhoneNum(), account.getPhoneNum());
		assertEquals(dto.getSirName(), account.getSirName());
		assertEquals(dto.getPatronomic(), account.getPatronomic());
		
	}
	
	private void compareAccountInfo(StaffDTO dto, AccountInfo account){
		compareAccountInfoWithoutPassword(dto, account);
		assertEquals(passHash, account.getPasswordHash());
	}
	
	private void verifyThatUpdateDoesNotCalled() {
		verify(teacherDAO, never()).update(any(Teacher.class));
		verify(adminDAO, never()).update(any(Administrator.class));
	}
}

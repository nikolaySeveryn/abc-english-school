package nks.abc.test.domain.entity.user;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import nks.abc.core.exception.service.NoCurrentUserException;
import nks.abc.core.exception.service.NoIdException;
import nks.abc.core.exception.service.NoUserLoginException;
import nks.abc.core.exception.service.RightsDeprivingException;
import nks.abc.core.exception.service.ServiceDisplayedErorr;
import nks.abc.dao.base.interfaces.BaseHibernateRepository;
import nks.abc.dao.base.interfaces.CriterionSpecification;
import nks.abc.dao.repository.user.AccountRepository;
import nks.abc.dao.repository.user.AdministratorRepository;
import nks.abc.dao.repository.user.TeacherRepository;
import nks.abc.dao.specification.user.account.AccountInfoSpecificationFactory;
import nks.abc.dao.specification.user.administrator.AdministratorSpecificationFactory;
import nks.abc.dao.specification.user.teacher.TeacherSpecificationFactory;
import nks.abc.domain.entity.user.Account;
import nks.abc.domain.entity.user.Administrator;
import nks.abc.domain.entity.user.Teacher;
import nks.abc.domain.view.converter.user.AccountViewConverter;
import nks.abc.domain.view.object.objects.user.StaffView;
import nks.abc.service.impl.StaffServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StaffServiceTest {
	
	@Mock
	private AdministratorRepository adminRepository = new AdministratorRepository();
	@Mock
	private TeacherRepository teacherRepository = new TeacherRepository();
	@Mock
	private AccountRepository accountRepository;
	@Spy
	private AccountViewConverter dtoConvertor = new AccountViewConverter();
	@Spy
	private AdministratorSpecificationFactory adminSpecifications = new AdministratorSpecificationFactory();
	@Spy
	private TeacherSpecificationFactory teacherSpecifications = new TeacherSpecificationFactory();
	
	@InjectMocks
	private StaffServiceImpl service = new StaffServiceImpl();
	
	private StaffView emptyEmployee;
	private StaffView employee;
	private String passHash;
	
	@Before
	public void initMocks(){
		when(adminRepository.specifications()).thenReturn(new AdministratorSpecificationFactory());
		when(teacherRepository.specifications()).thenReturn(new TeacherSpecificationFactory());
		when(accountRepository.specifications()).thenReturn(new AccountInfoSpecificationFactory());
		
	}
	
	@Before
	public void init(){
		emptyEmployee = new StaffView();
		emptyEmployee.setIsAdministrator(false);
		emptyEmployee.setIsTeacher(false);
		employee = new StaffView();
		
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
		when(accountRepository.uniqueQuery(accountRepository.specifications().byLogin(userName)))
				.thenReturn(new Account());
		service.add(emptyEmployee);
	}
	
	@Test(expected=ServiceDisplayedErorr.class)
	public void noTeahcerOrAdmin(){
		String userName = "login";
		emptyEmployee.setLogin(userName);
		when(accountRepository.uniqueQuery(accountRepository.specifications().byLogin(userName)))
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
		verify(teacherRepository).insert(teacherCaptor.capture());
		compareAccountInfo(employee, teacherCaptor.getValue().getAccountInfo());
		assertFalse(teacherCaptor.getValue().getAccountInfo().isAdministrator());
		assertTrue(teacherCaptor.getValue().getAccountInfo().isTeacher());
		verify(adminRepository, never()).insert(any(Administrator.class));
	}
	
	@Test
	public void correctAdmin(){
		ArgumentCaptor<Administrator> adminCaptor = ArgumentCaptor.forClass(Administrator.class);
		employee.setIsTeacher(false);
		employee.setIsAdministrator(true);
		service.add(employee);
		verify(adminRepository).insert(adminCaptor.capture());
		compareAccountInfo(employee, adminCaptor.getValue().getAccountInfo());
		assertTrue(adminCaptor.getValue().getAccountInfo().isAdministrator());
		assertFalse(adminCaptor.getValue().getAccountInfo().isTeacher());
		
		verify(teacherRepository, never()).insert(any(Teacher.class));
	}
	
	@Test
	public void correctTeacherAndAdmin(){
		ArgumentCaptor<Teacher> teacherCaptor = ArgumentCaptor.forClass(Teacher.class);
		ArgumentCaptor<Administrator> adminCaptor = ArgumentCaptor.forClass(Administrator.class);
		employee.setIsTeacher(true);
		employee.setIsAdministrator(true);
		service.add(employee);
		verify(teacherRepository).insert(teacherCaptor.capture());
		verify(adminRepository).insert(adminCaptor.capture());
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
		employee.setAccountId(4L);
		employee.setIsAdministrator(false);
		Account account = new Account();
		account.setAccountId(4L);
		when(
				accountRepository.uniqueQuery(accountRepository.specifications().byLoginAndDeleted(currentUserLogin,false))
			).thenReturn(account);
		
		service.update(employee, currentUserLogin);
		
		verifyThatUpdateDoesNotCalled();
	}
	
	@Test
	public void UpdateSuccess(){
		employee.setAccountId(4L);
		String currentUserLogin = "user";
		Account currentUser = new Account();
		currentUser.setLogin(currentUserLogin);
		currentUser.setAccountId(3L);
		when(
				accountRepository.uniqueQuery(accountRepository.specifications().byLoginAndDeleted(currentUserLogin,false))
				).thenReturn(currentUser);
		employee.setIsAdministrator(true);
		employee.setIsTeacher(true);
		service.update(employee, currentUserLogin);
		ArgumentCaptor<Teacher> teacherCaptor = ArgumentCaptor.forClass(Teacher.class);
		ArgumentCaptor<Administrator>adminCaptor = ArgumentCaptor.forClass(Administrator.class);
		ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
		
		verify(accountRepository).update(accountCaptor.capture());
		verify(teacherRepository).update(teacherCaptor.capture());
		verify(adminRepository).update(adminCaptor.capture());
		
		assertSame(teacherCaptor.getValue().getAccountInfo(), accountCaptor.getValue());
		assertSame(teacherCaptor.getValue().getAccountInfo(), adminCaptor.getValue().getAccountInfo());
		compareAccountInfoWithoutPassword(employee, teacherCaptor.getValue().getAccountInfo());
		compareAccountInfoWithoutPassword(employee, adminCaptor.getValue().getAccountInfo());
	}
	
	@Test
	public void UpdateNoTeacherAndNoAdminStaff(){
		employee.setAccountId(4L);
		String currentUserLogin = "user";
		Account currentUser = new Account();
		currentUser.setLogin(currentUserLogin);
		currentUser.setAccountId(3L);
		when(
				accountRepository.uniqueQuery(accountRepository.specifications().byLoginAndDeleted(currentUserLogin,false))
				).thenReturn(currentUser);
		employee.setIsAdministrator(false);
		employee.setIsTeacher(false);
		service.update(employee, currentUserLogin);
		
		ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
		verify(accountRepository).update(accountCaptor.capture());
		compareAccountInfoWithoutPassword(employee, accountCaptor.getValue());
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
		Account currentUser = new Account();
		currentUser.setAccountId(5L);
		currentUser.setLogin(currentUserLogin);
		when(accountRepository.uniqueQuery(accountRepository.specifications().byLoginAndDeleted(currentUserLogin,false))).thenReturn(currentUser);
		service.delete(5L, currentUserLogin);
	}
	
	
	private void compareAccountInfoWithoutPassword(StaffView dto, Account account){
		assertEquals(dto.getBirthday(), account.getPeronalInfo().getBirthday());
		assertEquals(dto.getEmail(), account.getPeronalInfo().getEmail());
		assertEquals(dto.getFirstName(), account.getPeronalInfo().getFirstName());
		assertEquals(dto.getIsAdministrator(), account.isAdministrator());
		assertEquals(dto.getIsTeacher(), account.isTeacher());
		assertFalse(account.isStudent());
		assertEquals(dto.getLogin(), account.getLogin());
		assertEquals(dto.getPhoneNum(), account.getPeronalInfo().getPhoneNum());
		assertEquals(dto.getSirName(), account.getPeronalInfo().getSirName());
		assertEquals(dto.getPatronomic(), account.getPeronalInfo().getPatronomic());
	}
	
	private void compareAccountInfo(StaffView dto, Account account){
		compareAccountInfoWithoutPassword(dto, account);
		assertEquals(passHash, account.getPasswordHash());
	}
	
	private void verifyThatUpdateDoesNotCalled() {
		verify(teacherRepository, never()).update(any(Teacher.class));
		verify(adminRepository, never()).update(any(Administrator.class));
	}
}

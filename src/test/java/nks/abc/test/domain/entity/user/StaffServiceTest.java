package nks.abc.test.domain.entity.user;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.core.exception.handler.ServiceErrorHandler;
import nks.abc.core.exception.service.NoCurrentUserException;
import nks.abc.core.exception.service.NoIdException;
import nks.abc.core.exception.service.NoUserLoginException;
import nks.abc.core.exception.service.RightsDeprivingException;
import nks.abc.core.exception.service.ServiceDisplayedErorr;
import nks.abc.dao.repository.user.AccountRepository;
import nks.abc.dao.repository.user.AdminRepository;
import nks.abc.dao.repository.user.TeacherRepository;
import nks.abc.domain.message.Email;
import nks.abc.domain.message.MailFactory;
import nks.abc.domain.message.MailService;
import nks.abc.domain.user.PasswordEncryptor;
import nks.abc.domain.user.impl.AccountImpl;
import nks.abc.domain.user.impl.AdministratorImpl;
import nks.abc.domain.user.impl.HumanResourcesImpl;
import nks.abc.domain.user.impl.MD5PasswordEncryptor;
import nks.abc.domain.user.impl.TeacherImpl;

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
	private AdminRepository adminRepository = new AdminRepository();
	@Mock
	private TeacherRepository teacherRepository = new TeacherRepository();
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private MailService mailSevice;
	@Spy
	private MailFactory mailFactory = new MailFactory();
	@Spy
	private PasswordEncryptor passwordEncriptor = new MD5PasswordEncryptor();
	@Spy
	private ErrorHandler errorHandler = new ServiceErrorHandler();
	
	@InjectMocks
	private HumanResourcesImpl service = new HumanResourcesImpl();
	
	private String passHash;
	
	@Before
	public void initMocks(){
//		when(adminRepository.specifications()).thenReturn(new AdministratorSpecificationFactory());
//		when(teacherRepository.specifications()).thenReturn(new TeacherSpecificationFactory());
//		when(accountRepository.specifications()).thenReturn(new AccountSpecificationFactory());
		
	}
	
	@Before
	public void init(){
//		emptyEmployee = new StaffView();
//		emptyEmployee.setIsAdministrator(false);
//		emptyEmployee.setIsTeacher(false);
//		employee = new StaffView();
//		
//		passHash = "X03MO1qnZdYdgyfeuILPmQ==";
//		employee.setBirthday(new Date());
//		employee.setEmail("email@mail.ma");
//		employee.setFirstName("fname");
//		employee.setPassword("password");
//		employee.setPasswordHash(passHash);
//		employee.setPatronomic("patronomic");
//		employee.setPhoneNum("12345654321");
//		employee.setSirName("sirname");
//		employee.setIsAdministrator(false);
//		employee.setIsTeacher(false);
	}
	
	@Test
	public void fakeTest(){
		
	}

	/*
	 *  add tests
	 */
//	@Test(expected=NoUserLoginException.class)
//	public void insertStaffWithoutLogin(){
//		service.add(emptyEmployee, null, null);
//	}
//	
//	@Test(expected=ServiceDisplayedErorr.class)
//	public void existingUser(){
//		String email = "username@mail.ma";
//		emptyEmployee.setEmail(email);
//		when(accountRepository.uniqueQuery(accountRepository.specifications().byEmail(email)))
//				.thenReturn(new Account());
//		service.add(emptyEmployee, null, null);
//	}
//	
//	@Test(expected=ServiceDisplayedErorr.class)
//	public void noTeahcerOrAdmin(){
//		String email = "username@mail.ma";
//		emptyEmployee.setEmail(email);
//		when(accountRepository.uniqueQuery(accountRepository.specifications().byEmail(email))).thenReturn(null);
//		emptyEmployee.setIsAdministrator(false);
//		emptyEmployee.setIsTeacher(false);
//		service.add(emptyEmployee, null, null);
//	}
//	
//	@Test
//	public void addCorrectTeacher(){
//		ArgumentCaptor<Teacher> teacherCaptor = ArgumentCaptor.forClass(Teacher.class);
//		ArgumentCaptor<Email>emailCaptor = ArgumentCaptor.forClass(Email.class);
//		employee.setIsTeacher(true);
//		employee.setIsAdministrator(false);
//		service.add(employee, null, null);
//		verify(teacherRepository).insert(teacherCaptor.capture());
//		verify(mailSevice).sendEmail(emailCaptor.capture());
//		assertEquals(employee.getEmail(), emailCaptor.getValue().getRecipient());
//		compareAccountInfoWithoutPassword(employee, teacherCaptor.getValue().getAccountInfo());
//		assertFalse(teacherCaptor.getValue().getAccountInfo().getIsAdministrator());
//		assertTrue(teacherCaptor.getValue().getAccountInfo().getIsTeacher());
//		verify(adminRepository, never()).insert(any(Administrator.class));
//	}
//	
//	@Test
//	public void addCorrectAdmin(){
//		ArgumentCaptor<Administrator> adminCaptor = ArgumentCaptor.forClass(Administrator.class);
//		ArgumentCaptor<Email>emailCaptor = ArgumentCaptor.forClass(Email.class);
//		employee.setIsTeacher(false);
//		employee.setIsAdministrator(true);
//		service.add(employee, null, null);
//		verify(adminRepository).insert(adminCaptor.capture());
//		verify(mailSevice).sendEmail(emailCaptor.capture());
//		assertEquals(employee.getEmail(), emailCaptor.getValue().getRecipient());
//		compareAccountInfoWithoutPassword(employee, adminCaptor.getValue().getAccountInfo());
//		assertTrue(adminCaptor.getValue().getAccountInfo().getIsAdministrator());
//		assertFalse(adminCaptor.getValue().getAccountInfo().getIsTeacher());
//		
//		verify(teacherRepository, never()).insert(any(Teacher.class));
//	}
//	
//	@Test
//	public void addCorrectTeacherAndAdmin(){
//		ArgumentCaptor<Teacher> teacherCaptor = ArgumentCaptor.forClass(Teacher.class);
//		ArgumentCaptor<Administrator> adminCaptor = ArgumentCaptor.forClass(Administrator.class);
//		ArgumentCaptor<Email>emailCaptor = ArgumentCaptor.forClass(Email.class);
//		employee.setIsTeacher(true);
//		employee.setIsAdministrator(true);
//		service.add(employee, null, null);
//		verify(teacherRepository).insert(teacherCaptor.capture());
//		verify(adminRepository).insert(adminCaptor.capture());
//		verify(mailSevice).sendEmail(emailCaptor.capture());
//		assertEquals(employee.getEmail(), emailCaptor.getValue().getRecipient());
//		assertEquals(teacherCaptor.getValue().getAccountInfo(), adminCaptor.getValue().getAccountInfo());
//		assertSame(teacherCaptor.getValue().getAccountInfo(), adminCaptor.getValue().getAccountInfo());
//		
//		compareAccountInfoWithoutPassword(employee, teacherCaptor.getValue().getAccountInfo());
//		assertTrue(teacherCaptor.getValue().getAccountInfo().getIsAdministrator());
//		assertTrue(teacherCaptor.getValue().getAccountInfo().getIsTeacher());
//	}
//	
//	
//	
//	/*
//	 * update test
//	 */
//	@Test(expected=NoCurrentUserException.class)
//	public void emptyCurentUserNameUpdate(){
//		service.update(emptyEmployee, null, null, null);
//		verifyThatUpdateDoesNotCalled();
//	}
//	
//	@Test(expected=NoCurrentUserException.class)
//	public void noUsername(){
//		service.update(employee, null, null, "");
//		verifyThatUpdateDoesNotCalled();
//	}
//
//
//	
//	@Test(expected=NoIdException.class)
//	public void updateWithoutId(){
//		service.update(emptyEmployee, null, null, "user");
//		verifyThatUpdateDoesNotCalled();
//	}
//	
//	@Test(expected=RightsDeprivingException.class)
//	public void updateDeprivingRights(){
//		String currentUserLogin = "user";
//		employee.setAccountId(4L);
//		employee.setIsAdministrator(false);
//		Account account = new Account();
//		account.setAccountId(4L);
//		when(
//				accountRepository.uniqueQuery(accountRepository.specifications().byEmailAndFired(currentUserLogin,false))
//			).thenReturn(account);
//		
//		service.update(employee, null, null, currentUserLogin);
//		
//		verifyThatUpdateDoesNotCalled();
//	}
//	
//	@Test
//	public void UpdateSuccess(){
//		employee.setAccountId(4L);
//		String currentUserEmail = "user@mail.ma";
//		Account currentUser = new Account();
//		currentUser.setEmail(currentUserEmail);
//		currentUser.setAccountId(3L);
//		when(accountRepository.uniqueQuery(accountRepository.specifications().byEmailAndFired(currentUserEmail,false))).thenReturn(currentUser);
//		employee.setIsAdministrator(true);
//		employee.setIsTeacher(true);
//		service.update(employee, null, null, currentUserEmail);
//		ArgumentCaptor<Teacher> teacherCaptor = ArgumentCaptor.forClass(Teacher.class);
//		ArgumentCaptor<Administrator>adminCaptor = ArgumentCaptor.forClass(Administrator.class);
//		ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
//		
//		verify(accountRepository).update(accountCaptor.capture());
//		verify(teacherRepository).update(teacherCaptor.capture());
//		verify(adminRepository).update(adminCaptor.capture());
//		
//		assertSame(teacherCaptor.getValue().getAccountInfo(), accountCaptor.getValue());
//		assertSame(teacherCaptor.getValue().getAccountInfo(), adminCaptor.getValue().getAccountInfo());
//		compareAccountInfo(employee, teacherCaptor.getValue().getAccountInfo());
//		compareAccountInfo(employee, adminCaptor.getValue().getAccountInfo());
//	}
//	
//	@Test
//	public void UpdateNoTeacherAndNoAdminStaff(){
//		employee.setAccountId(4L);
//		String currentUserEmail = "user@mail.ma";
//		Account currentUser = new Account();
//		currentUser.setEmail(currentUserEmail);
//		currentUser.setAccountId(3L);
//		when(
//				accountRepository.uniqueQuery(accountRepository.specifications().byEmailAndFired(currentUserEmail,false))
//				).thenReturn(currentUser);
//		employee.setIsAdministrator(false);
//		employee.setIsTeacher(false);
//		service.update(employee, null, null, currentUserEmail);
//		
//		ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
//		verify(accountRepository).update(accountCaptor.capture());
//		compareAccountInfoWithoutPassword(employee, accountCaptor.getValue());
//	}
//	
//	
//	@Test(expected=NoCurrentUserException.class)
//	public void DeleteNoCurrentUserEmptyName(){
//		service.delete(5L, "");
//	}
//	
//	@Test(expected=NoCurrentUserException.class)
//	public void DeleteCurrentUserNotFound(){
//		service.delete(5L, "sedsd");
//	}
//	
//	@Test(expected=RightsDeprivingException.class)
//	public void deleteHimself(){
//		String currentUserEmail = "user@mail.ma";
//		Account currentUser = new Account();
//		currentUser.setAccountId(5L);
//		currentUser.setEmail(currentUserEmail);
//		when(accountRepository.uniqueQuery(accountRepository.specifications().byEmailAndFired(currentUserEmail,false))).thenReturn(currentUser);
//		service.delete(5L, currentUserEmail);
//	}
//	
//	
//	private void compareAccountInfoWithoutPassword(StaffView dto, Account account){
//		assertEquals(dto.getBirthday(), account.getPeronalInfo().getBirthday());
//		assertEquals(dto.getFirstName(), account.getPeronalInfo().getFirstName());
//		assertEquals(dto.getIsAdministrator(), account.getIsAdministrator());
//		assertEquals(dto.getIsTeacher(), account.getIsTeacher());
//		assertFalse(account.getIsStudent());
//		assertEquals(dto.getEmail(), account.getEmail());
//		assertEquals(dto.getPhoneNum(), account.getPeronalInfo().getPhoneNum());
//		assertEquals(dto.getSirName(), account.getPeronalInfo().getSirName());
//		assertEquals(dto.getPatronomic(), account.getPeronalInfo().getPatronomic());
//	}
//	
//	private void compareAccountInfo(StaffView dto, Account account){
//		compareAccountInfoWithoutPassword(dto, account);
//		assertEquals(passHash, account.getPasswordHash());
//	}
//	
//	private void verifyThatUpdateDoesNotCalled() {
//		verify(teacherRepository, never()).update(any(Teacher.class));
//		verify(adminRepository, never()).update(any(Administrator.class));
//	}
}

package hw7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerAccountUnitTest {

	CustomerAccount custAcc = null;

	@BeforeEach
	void setup() throws SQLException {
	}

	//Check un-successful customer name update
	@Test
	void testUnSuccessfulCustomerNameUpdate() throws NoSuchCustomerAccountException, SQLException {
		CustomerAccountDAO custAccDAOSpy = mock(CustomerAccountDAO.class);
		doReturn(false).when(custAccDAOSpy).updateAccount();

		CustomerAccount custAcc = new CustomerAccount(custAccDAOSpy);
		boolean test = custAcc.updateCustomerName("123", "test");
		assertEquals(test, false);
	}


	//Check successful customer name update
	@Test
	void testSuccessfulCustomerNameUpdate() throws NoSuchCustomerAccountException, SQLException {
		CustomerAccountDAO custAccDAOmock = mock(CustomerAccountDAO.class);
		doReturn(true).when(custAccDAOmock).updateAccount();

		CustomerAccount custAcc = new CustomerAccount(custAccDAOmock);
		boolean test = custAcc.updateCustomerName("123", "test");
		assertEquals(test, true);
	}

	//Check exception scenario during customer name update 
	@Test
	void testExceptionForCustomerNameUpdate() throws NoSuchCustomerAccountException, SQLException {
		CustomerAccountDAO custAccDAOmock = mock(CustomerAccountDAO.class);
		when(custAccDAOmock.updateAccount()).thenThrow(new SQLException());

		CustomerAccount custAcc = new CustomerAccount(custAccDAOmock);
		Exception exception = assertThrows(NoSuchCustomerAccountException.class,
				() -> custAcc.updateCustomerName("123", "test"));

		assertEquals("No customer record with account number 123", exception.getMessage());
	}

	
	//Check new account creation
	@Test
	void testCreateAccountNewAccTrue() throws NoAccountCreatedException, SQLException {

		CustomerAccountDAO custAccDAOSpy = spy(CustomerAccountDAO.class);
		doReturn("12345").when(custAccDAOSpy).newAcctNumber("name", "123");

		CustomerAccount custAcc = new CustomerAccount(custAccDAOSpy);
		CustomerAccount acc = custAcc.createNewAccount("name", "123");

		assertEquals(acc.custAcctNumber, "12345");
		assertEquals(acc.custName, "name");
		assertEquals(acc.custPhone, "123");
	}

	//Check exception scenario during creation of New Account
	@Test
	void testCreateAccountNewAccException() throws NoAccountCreatedException, SQLException {

		CustomerAccountDAO custAccDAOSpy = spy(CustomerAccountDAO.class);
		when(custAccDAOSpy.newAcctNumber("name", "123")).thenThrow(new SQLException());

		CustomerAccount custAcc = new CustomerAccount(custAccDAOSpy);
		Exception exception = assertThrows(NoAccountCreatedException.class,
				() -> custAcc.createNewAccount("name", "123"));

		assertEquals("Account for name at 123 not created", exception.getMessage());
	}

	//Check successful account creation 
	@Test
	void testCreateAccountSaveAccTrue() throws NoAccountCreatedException, SQLException {

		CustomerAccountDAO custAccDAOSpy = spy(CustomerAccountDAO.class);
		doReturn("12345").when(custAccDAOSpy).newAcctNumber("name", "123");
		doReturn(true).when(custAccDAOSpy).saveAccount(any(CustomerAccount.class));

		CustomerAccount custAcc = new CustomerAccount(custAccDAOSpy);

		CustomerAccount acc = custAcc.createNewAccount("name", "123");
		assertEquals(acc.custAcctNumber, "12345");
		assertEquals(acc.custName, "name");
		assertEquals(acc.custPhone, "123");
	}

	//Check exception scenario during Save Account
	@Test
	void testCreateAccountSaveAccException() throws NoAccountCreatedException, SQLException {
		CustomerAccountDAO custAccDAOSpy = spy(CustomerAccountDAO.class);
		doReturn("12345").when(custAccDAOSpy).newAcctNumber("name", "123");
		when(custAccDAOSpy.saveAccount(any(CustomerAccount.class))).thenThrow(new SQLException());

		CustomerAccount custAcc = new CustomerAccount(custAccDAOSpy);

		Exception exception = assertThrows(NoAccountCreatedException.class,
				() -> custAcc.createNewAccount("name", "123"));
		assertEquals("Account for name at 123 not created with account number 12345", exception.getMessage());
	}

}

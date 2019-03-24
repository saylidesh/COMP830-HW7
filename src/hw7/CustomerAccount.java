package hw7;
import java.sql.SQLException;

public class CustomerAccount {
	
	public String custName;	
	public String custPhone;
	public String custAcctNumber;	
	private CustomerAccountDAO cad; //Code change so that we could pass the Mock/Spy object into this class
	
	public CustomerAccount(CustomerAccountDAO cad) {
		this.cad = cad;
	}
	
	public CustomerAccount createNewAccount(String name, String phone) throws SQLException, NoAccountCreatedException {
		
			String acctNum = "";
			try {
				      acctNum = cad.newAcctNumber(name, phone);
				      
			}
			catch (SQLException se) {
				// retry the call -- it always works the second time
				try {
					acctNum  = cad.newAcctNumber(name, phone);
				} 
				catch (SQLException se1) {
					throw new
					NoAccountCreatedException(String.format("Account for %s at %s not created", name, phone));
					
				}
				
			}
			this.custName = name;
			this.custPhone = phone;
			this.custAcctNumber = acctNum;
			
			try {
				cad.saveAccount(this);				
			}
			catch (SQLException se2) {
				if (se2.getErrorCode() != 803)throw new
				NoAccountCreatedException(String.format("Account for %s at %s not created with account number %s", name, phone, acctNum));
				
			}
		
			return this;  //Initially null object was being returned. Changed code to enable testing
			
	}
		
	
		
	public boolean updateCustomerName(String acctNum, String name) throws NoSuchCustomerAccountException {		
		boolean  success = false;
		try {			
			custName = name;
			if(cad.updateAccount())
				success = true;
			} 
		catch (SQLException se) {
			throw new 
			NoSuchCustomerAccountException(String.format("No customer record with account number %s", acctNum));		
		  } 
		
		return success;   //Initially null was being returned. Changed code to enable testing
				
	}
	
}




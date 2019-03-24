package hw7;
import java.sql.SQLException;
public class CustomerAccountDAO {
// Because I didn't want to write a real class for something I want you to mock anyway...
	public boolean saveAccount(CustomerAccount ca) throws SQLException {
		return true;
	}
	
	public boolean updateAccount() throws SQLException {
		return true;		
	}
	
	public boolean deleteAccount(CustomerAccount ca) throws SQLException {
		return true;
	}
	
	public CustomerAccount getAccount(String acctNum) throws SQLException {
		return null;
	}
	public String newAcctNumber(String name, String phone) throws SQLException {
		return "1111222233334444";
		
	}
	
}




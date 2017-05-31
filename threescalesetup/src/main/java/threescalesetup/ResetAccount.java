package threescalesetup;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import threescalesetup.operation.account.AccountOperation;
import threescalesetup.operation.account.ApplicationOperation;

public class ResetAccount {
	
	

	public static void main(String[] args) {
		Logger log = Logger.getLogger(ResetAccount.class.getName());
		
		String threescalehost = args[0];
		String accessToken = args[1];
		String applicationid = args[2];
		
		AccountOperation accountsopp = new AccountOperation(threescalehost);
		Map<String,String> accounts= accountsopp.get(accessToken);
		String defaultDeveloperAccountId = accounts.get("Developer");
		
		//System.out.println("defaultDeveloperAccountId->"+defaultDeveloperAccountId);
		log.log(Level.FINE, "Default Developer AccountId ID: {0} Found", defaultDeveloperAccountId);
		
		ApplicationOperation applicationOperation = new ApplicationOperation(threescalehost,defaultDeveloperAccountId);
		applicationOperation.delete(accessToken, applicationid);
		
		log.log(Level.INFO, "Application ID: {0} Deleted", applicationid);
	}

}

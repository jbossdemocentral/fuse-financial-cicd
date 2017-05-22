package threescalesetup;

import java.util.Map;

import threescalesetup.operation.account.AccountOperation;
import threescalesetup.operation.account.ApplicationOperation;

public class SetupAccount {

	public static void main(String[] args) {
		
		String threescalehost = args[0];
		String accessToken = args[1];
		String applicationplanid = args[2];
		String applicationname = args[3];
		String applicationdescription = args[3];
		

		AccountOperation accountsopp = new AccountOperation(threescalehost);
		
		Map<String,String> accounts= accountsopp.get(accessToken);
		String defaultDeveloperAccountId = accounts.get("Developer");
		
		System.out.println("defaultDeveloperAccountId->"+defaultDeveloperAccountId);
		
		ApplicationOperation applicationOperation = new ApplicationOperation(threescalehost,defaultDeveloperAccountId);
		applicationOperation.create(accessToken, applicationplanid, applicationname, applicationdescription);
		
	}

}

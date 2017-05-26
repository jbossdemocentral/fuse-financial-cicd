package threescalesetup;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import threescalesetup.operation.account.AccountOperation;
import threescalesetup.operation.account.ApplicationOperation;

public class SetupAccount {

	public static void main(String[] args) {
		
		Logger log = Logger.getLogger(SetupAccount.class.getName());
		
		String threescalehost = args[0];
		String accessToken = args[1];
		String applicationplanid = args[2];
		String applicationname = args[3];
		String applicationdescription = args[3];
		

		AccountOperation accountsopp = new AccountOperation(threescalehost);
		
		Map<String,String> accounts= accountsopp.get(accessToken);
		String defaultDeveloperAccountId = accounts.get("Developer");
		
		//System.out.println("defaultDeveloperAccountId->"+defaultDeveloperAccountId);
		log.log(Level.FINE, "Default Developer AccountId ID: {0} Found", defaultDeveloperAccountId);
		
		ApplicationOperation applicationOperation = new ApplicationOperation(threescalehost,defaultDeveloperAccountId);
		String applicationId = applicationOperation.create(accessToken, applicationplanid, applicationname, applicationdescription);
		
		log.log(Level.FINE, "Application, ID: {0} Created", applicationId);
		
		log.info("-----------------IMPROTANT REFERENCE-------------------");
		log.info("--Successfully setup 3scale Accounts..");
		log.log(Level.INFO,"--Application [{0}] id: [{1}] created within Developer Account of ID [{2}]....",new Object[]{ applicationname, applicationId, defaultDeveloperAccountId});
		log.info("-----------------IMPROTANT REFERENCE-------------------");
	}

}

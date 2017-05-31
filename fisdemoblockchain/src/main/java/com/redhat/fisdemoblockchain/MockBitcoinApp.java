package com.redhat.fisdemoblockchain;

import java.util.HashMap;
import java.util.Map;

import com.redhat.fisdemoblockchain.exception.AccountFormatException;
import com.redhat.fisdemoblockchain.exception.NoAccountFoundException;

public class MockBitcoinApp {
	
	private Map<String, Integer> accounts = new HashMap<String, Integer> ();
	private Ledger ledger = new Ledger();
	
	
	public MockBitcoinApp(){
		
		accounts.put("234567", 1000);
		accounts.put("567890", 2000);
		accounts.put("123456", 3000);
		accounts.put("789012", 3500);
		accounts.put("345678", 4000);
		accounts.put("901234", 4500);
		accounts.put("456789", 300);
		accounts.put("678901", 900);
		accounts.put("890123", 600);
		
	}
	
	public Integer getBalance(String acctid) throws NoAccountFoundException, AccountFormatException{
		if(acctid.length()<6)
			throw new AccountFormatException();
		Integer balance = accounts.get(acctid);
		
		if(balance == null) 
			throw new NoAccountFoundException();
		
		return balance;
	}
	
	public String transfer(String inputString){
		
		String[] inputs = inputString.split(",");
		
		if(inputs.length < 3)
			return "Transfer FORMAT ERROR!!!!";
		Integer amt;
		try{
			amt = Integer.valueOf(inputs[1]);
		}catch(Exception e){
			return "Transfer AMT FORMAT ERROR!!!!";
		}
		
		return this.transfer(inputs[0], amt, inputs[2]);
		
	}
	
	public String transfer(String acctid, int amt, String recptid){
		
		Integer acctBalance = accounts.get(acctid);
		Integer recptBalance = accounts.get(recptid);
		
		if(acctBalance != null && recptBalance != null){
			accounts.put(acctid, acctBalance-amt);
			accounts.put(acctid, recptBalance+amt);
		}else{
			return "Transfer: ONE of the account NOT FOUND!";
		}
		ledger.transfer(acctid, amt, recptid);
		return "Transfered Completed! $"+amt+" from "+acctid+" to "+recptid+" the remaining balance is: $"+(acctBalance-amt);
	}

}

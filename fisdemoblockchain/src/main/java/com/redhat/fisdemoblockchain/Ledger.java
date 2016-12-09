package com.redhat.fisdemoblockchain;

import java.util.ArrayList;
import java.util.List;

/**
 * The Mock Ledger simulates the blockchain (without crypto and everything that is important :) )
 * 
 * */

public class Ledger {

	List<TransferHistory> histories = new ArrayList<TransferHistory>();
	
	public boolean transfer(String acctid, Integer amt, String recptid){
		
		TransferHistory history = new TransferHistory();
		
		history.setAcctid(acctid);
		history.setAmt(amt);
		history.setRecptid(recptid);
		
		histories.add(history);
		
		return true;
	}
	
}

package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckOutRecord {
	private List<CheckoutRecordEntry> CheckOUtRecordList;
	public CheckOutRecord(){
		CheckOUtRecordList= new ArrayList<CheckoutRecordEntry>();
	}
	public List<CheckoutRecordEntry> getCheckOUtRecordList() {
		return CheckOUtRecordList;
	}
	public void addCheckOutEntry(CheckoutRecordEntry entry){
		CheckOUtRecordList.add(entry);
	}

}

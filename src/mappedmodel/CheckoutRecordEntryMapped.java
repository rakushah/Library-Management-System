package mappedmodel;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;

public class CheckoutRecordEntryMapped {
	private int sn;
	private int checkOutEntryId;
	
	private String bookName;
	private String memberName;
	private String issueDate;
	private String dueDate;
	private String status;
	private String isbn;;
	private String copyNum;
	private int memberId;


	public CheckoutRecordEntryMapped(int sn, String bookName, String memberName, String issueDate, String dueDate,
			String status,String copyNum) {
		this.sn = sn;
		this.bookName = bookName;
		this.memberName = memberName;
		this.issueDate = issueDate;
		this.dueDate = dueDate;
		this.status = status;
		this.copyNum=copyNum;
	}

	public int getSn() {
		return sn;
	}

	public String getBookName() {
		return bookName;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public String getStatus() {
		return status;
	}
	public String getCopyNum() {
		return copyNum;
	}
	public int getCheckOutEntryId() {
		return checkOutEntryId;
	}
	public void setCheckOutEntryId(int id){
		checkOutEntryId=id;
	}
	public void setIsbn(String isbn){
		this.isbn=isbn;
	}
	public String getIsbn(){
		return isbn;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
}

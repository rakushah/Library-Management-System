package model;

public class CheckoutRecordEntry {
	private BookCopy bookCopy;
	private LibraryMember member;
	private String issueDate;
	private String dueDate;
	private String status;

	public CheckoutRecordEntry(BookCopy bookCopy, LibraryMember member, String issueDate, String dueDate,String status) {
		this.bookCopy = bookCopy;
		this.member = member;
		this.issueDate = issueDate;
		this.dueDate = dueDate;
		this.status = status;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public LibraryMember getLibraryMember() {
		return member;
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

}

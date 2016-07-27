package model;

public class BookCopy {
	private String bookISBN;
	private int copyNum;

	public BookCopy(String bookISBN, int copyNum) {
		this.bookISBN = bookISBN;
		this.copyNum = copyNum;
	}

	public String getBookISBN() {
		return bookISBN;
	}

	public int getCopyNum() {
		return copyNum;
	}
	@Override
	public String toString(){
		return String.valueOf(copyNum);
	}

}

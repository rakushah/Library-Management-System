package model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Book {
	private SimpleStringProperty sn = new SimpleStringProperty("sn");
	private SimpleStringProperty isbn = new SimpleStringProperty("isbn");
	private SimpleStringProperty name = new SimpleStringProperty("name");
	private SimpleStringProperty remainingCopies = new SimpleStringProperty("remainingCopies");
	private SimpleStringProperty totalCopies = new SimpleStringProperty("totalCopies");
	private SimpleStringProperty checkOutLenght = new SimpleStringProperty("checkOutLenght");
	private SimpleStringProperty noOfCopies = new SimpleStringProperty("noOfCopies");
	private SimpleListProperty<Author> autherAsList = new SimpleListProperty<>();
	
	public ObservableList<Author> getAutherAsList() {
		return autherAsList.get();
	}

	public String getTotalCopies() {
		if (totalCopies.get().matches("totalCopies")) {
			return "0";
		}
		return totalCopies.get();
	}

	public void setTotalCopies(String totalCopies) {
		this.totalCopies.set(totalCopies);
	}

	public void setAutherAsList(List<Author> autherAsList) {
		ObservableList<Author> list = FXCollections.observableArrayList();

		for (int i = 0; i < autherAsList.size(); i++) {
			list.add(autherAsList.get(i));
		}
		this.autherAsList.setValue(list);
	}

	public Book(int sn, String isbn, String name, int remainingCopies, int checkoutLength) {
		setSn(sn);
		setCheckOutLenght(checkoutLength);
		setIsbn(isbn);
		setName(name);
		setRemainingCopies(remainingCopies);
	}

	public void setAuthorList(List<Author> authorList) {
		ObservableList<Author> list = FXCollections.observableArrayList();

		for (int i = 0; i < autherAsList.size(); i++) {
			list.add(autherAsList.get(i));
		}
		this.autherAsList.setValue(list);
	}

	public int getnoOfCopies() {
		return Integer.valueOf(noOfCopies.getValue());
	}

	public void setnoOfCopies(int noOfCopies) {
		this.noOfCopies.set(String.valueOf(noOfCopies));

	}

	public int getSn() {
		return Integer.valueOf(sn.getValue());
	}

	public void setSn(int sn) {
		this.sn.set(String.valueOf(sn));

	}

	public void setIsbn(String isbn) {
		this.isbn.set(isbn);
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public void setRemainingCopies(int remainingCopies) {
		this.remainingCopies.set(String.valueOf(remainingCopies));
	}

	public void setCheckOutLenght(int checkOutLenght) {
		this.checkOutLenght.set(String.valueOf(checkOutLenght));
	}

	public String getIsbn() {
		return isbn.getValue();
	}

	public String getName() {
		return name.getValue();
	}

	public int getRemainingCopies() {
		return Integer.valueOf(remainingCopies.getValue());
	}

	public int getCheckOutLenght() {
		return Integer.valueOf(checkOutLenght.getValue());
	}

	@Override
	public String toString() {
		return getIsbn() + " " + getName();
	}
}

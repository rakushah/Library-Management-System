package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LibraryMember {

	private IntegerProperty memberId;
	private StringProperty firstName;
	private StringProperty lastName;
	private ObjectProperty<Address> address;
	private StringProperty phoneNumber;

	public LibraryMember(int id, String fname,String lname){
		memberId =new SimpleIntegerProperty(id);
		firstName = new SimpleStringProperty(fname);
		lastName = new SimpleStringProperty(lname);
	}
	public LibraryMember(int id, String fname,String lname,Address address,String phoneNumber) {
		memberId =new SimpleIntegerProperty(id);
		firstName = new SimpleStringProperty(fname);
		lastName = new SimpleStringProperty(lname);
		this.address = new SimpleObjectProperty<Address>(address);
		this.phoneNumber = new SimpleStringProperty(phoneNumber);
	}

	public int getMemberId() {
		return memberId.get();
	}

	public void setMemberId(Integer memberId) {
		this.memberId.set(memberId);
	}

	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public Address getAddress() {
		return address.get();
	}

	public void setAddress(Address address) {
		this.address.set(address);
	}

	public String getPhoneNumber() {
		return phoneNumber.get();
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber.set(phoneNumber);
	}
	@Override
	public String toString(){
		return firstName.getValue()+" "+lastName.getValue();
	}



}

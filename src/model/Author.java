package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Author {
	private IntegerProperty sn=new SimpleIntegerProperty();
	private StringProperty firstName=new SimpleStringProperty();
	private StringProperty lastName=new SimpleStringProperty();
	private StringProperty credentials=new SimpleStringProperty();
	private StringProperty phone=new SimpleStringProperty();
	private ObjectProperty<Address> address=new SimpleObjectProperty<>();

	public Author(int sn,String firstName, String lastName, String credentials, String phone, Address address) {
		setSn(sn);
		setFirstName(firstName);
		setLastName(lastName);
		setCredentials(credentials);
		setPhone(phone);
		setAddress(address);
	}

	public int getSn() {
		return sn.getValue();
	}

	public void setSn(int sn) {
		this.sn.setValue(sn);
	}

	public String getFirstName() {
		return firstName.getValue();
	}

	public String getLastName() {
		return lastName.getValue();
	}

	public String getCredentials() {
		return credentials.getValue();
	}

	public String getPhone() {
		return phone.getValue();
	}

	public Address getAddress() {
		return address.getValue();
	}

	public void setFirstName(String firstName) {
		this.firstName.setValue(firstName);
	}

	public void setLastName(String lastName) {
		this.lastName.setValue(lastName);
	}

	public void setCredentials(String credentials) {
		this.credentials.setValue(credentials);
	}

	public void setPhone(String phone) {
		this.phone.setValue(phone);
	}

	public void setAddress(Address address) {
		this.address.setValue(address);
	}

	@Override
	public String toString(){
		return firstName.getValue()+ " "+lastName.getValue();
	}
	

}

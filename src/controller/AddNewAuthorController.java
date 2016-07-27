package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.ExecuteQuery;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Address;
import model.Author;
import utils.Utils;

public class AddNewAuthorController {
	AuthurDataSelectionControlller authurDataSelectionControlller;
	@FXML
	private TextField tffirstName;
	@FXML
	private TextField tfLastName;
	@FXML
	private TextField tfCredentials;
	@FXML
	private TextField tfphone;
	@FXML
	private TextField tfStreet;
	@FXML
	private TextField tfCity;
	@FXML
	private TextField tfState;
	@FXML
	private TextField tfZip;
	int addressId;
	Stage stage;
	@FXML
	public void saveAuthor() {
		String firstName = tffirstName.getText();
		String lastName = tfLastName.getText();
		String credentials = tfCredentials.getText();
		String phone = tfphone.getText();
		String street = tfStreet.getText();
		String city = tfCity.getText();
		String state = tfState.getText();
		int zip = Integer.parseInt(tfZip.getText());
		Address add = new Address(street, city, state, zip);
		ExecuteQuery executeQuery = new ExecuteQuery();
		ExecuteQuery executeQuery2 = new ExecuteQuery();
		ExecuteQuery executeQuery3= new ExecuteQuery();
		String queryAddress = "INSERT INTO Address(Street,City,State,Zip)  VALUES ('" + street + "','" + city + "','"
				+ state + "','" + tfZip.getText() + "')";
		String queryMaxAddressId = "Select Max(AddressId) as id From Address";
		
		try {
			boolean queryAddressStatus = executeQuery.executeQuery(queryAddress);
			ResultSet addressResultSet = executeQuery2.retrieveData(queryMaxAddressId);
			if (addressResultSet.next()) {
				addressId = addressResultSet.getInt("id");
				System.out.println(addressId);
			}
			String queryAuthor = "INSERT INTO Author(FirstName, LastName,Credential,Phone,AddressId)  VALUES ('" + firstName
					+ "','" + lastName + "','" + credentials + "','" + phone + "','" + addressId + "')";
			addressResultSet.close();
			String queryMaxAuthorId = "Select Max(AuthorId) as id From Author";
			ResultSet authroResultSet = executeQuery2.retrieveData(queryMaxAuthorId);
			int authorID=0;
			if (authroResultSet.next()) {
				authorID = authroResultSet.getInt("id");
			}
			authroResultSet.close();
			Author author = new Author(authorID, firstName, lastName, credentials, phone, add);
			authurDataSelectionControlller.addAuthor(author);

			boolean queryAuthorStatus = executeQuery3.executeQuery(queryAuthor);
			if (queryAddressStatus) {
				System.out.println("Address successfully added");
			}
			if (queryAuthorStatus) {
			//	System.out.println("Author successfully added");
				Utils.dialogShow("Author successfully added");
				stage.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setStage(Stage stage){
		this.stage=stage;
	}
	public void SetCallingForm(AuthurDataSelectionControlller authurDataSelectionControlller) {
		this.authurDataSelectionControlller = authurDataSelectionControlller;
	}
}

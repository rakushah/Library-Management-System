package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.ExecuteQuery;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import members.FrmAddNewMember;
import model.Address;
import model.LibraryMember;

public class AddNewMemberController {
	private FrmAddNewMember callingForm;
	@FXML
	private TextField id;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField street;
	@FXML
	private TextField city;
	@FXML
	private TextField state;
	@FXML
	private TextField zip;
	@FXML
	private TextField phoneNumber;

	@FXML
	private Button saveHandler;
	@FXML
	private Button cancelHandler;

	int memberId;
	String fName;
	String lName;
	String aStreet;
	String aCity;
	String aState;
	String aZip;
	String phoneNum;

	private boolean validateFields() {
		System.out.println("Checking fields.");
		boolean isEmpty = false;
		if (id.getText().equals("") || firstName.getText().equals("") || lastName.getText().equals("")
				|| city.getText().equals("") || zip.getText().equals("")) {
			isEmpty = true;
			System.out.println("Checking fields inside if.");
		}
		return isEmpty;
	}

	private void showEmptyAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) saveHandler.getScene().getWindow();
		alert.initOwner(stage);
		alert.setTitle("Empty Fields");
		alert.setHeaderText("You can not add empty data.");
		alert.setContentText("Please enter some data.");
		alert.showAndWait();
	}

	private boolean isNotNumber() {
		try{
		 Integer.parseInt(id.getText());
		 Integer.parseInt(zip.getText());
		 return false;
		}
		catch(NumberFormatException e) {
			return true;
		}
	}

	private void showNotNumberAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) saveHandler.getScene().getWindow();
		alert.initOwner(stage);
		alert.setTitle("Invalid");
		alert.setHeaderText("Check values for member Id and Zip.");
		alert.setContentText("Please enter only numeric data.");
		alert.showAndWait();
	}

	@FXML
	private void saveHandler() {
		System.out.println("Add new clicked");
		ResultSet memberIdRS = null;
		ResultSet addressIdRS = null;
		try {
			ExecuteQuery runQuery = new ExecuteQuery();
			if (validateFields()) {
				showEmptyAlert();
				return;
			}
			if(isNotNumber()) {
				showNotNumberAlert();
				return;
			}
			memberId = Integer.parseInt(id.getText());
			fName = firstName.getText();
			lName = lastName.getText();
			aStreet = street.getText();
			aCity = city.getText();
			aState = state.getText();
			aZip = zip.getText();
			phoneNum = phoneNumber.getText();
			String addrQuery = "INSERT INTO Address(Street, City,State,Zip) " + "VALUES ('" + aStreet + "','" + aCity
					+ "','" + aState + "','" + aZip + "')";

			String memIdQuery = "SELECT MemberId as id from Member WHERE MemberId='" + memberId + "'";
			memberIdRS = runQuery.retrieveData(memIdQuery);
			int memId = 0;
			if (memberIdRS.next()) {
				memId = memberIdRS.getInt("id");
			}
			//System.out.println("id from db: " + memId);
			memberIdRS.close();

			if (memberId != memId) {
				boolean addrIsSuccess = runQuery.executeQuery(addrQuery);
				String addrIdQuery = "SELECT max(AddressId) as id from Address";
				addressIdRS = runQuery.retrieveData(addrIdQuery);
				int addressId = 0;
				if (addressIdRS.next()) {
					addressId = addressIdRS.getInt("id");
				}
				addressIdRS.close();

				Address address = new Address(aStreet, aCity, aState, Integer.parseInt(aZip));

				String memQuery = "INSERT INTO Member(MemberId, FirstName, LastName,Phone,AddressId) " + "VALUES ('"
						+ memberId + "','" + fName + "','" + lName + "','" + phoneNum + "','" + addressId + "')";

				boolean memIsSuccess = runQuery.executeQuery(memQuery);

				LibraryMember member = new LibraryMember(memberId, fName, lName, address, phoneNum);
				MemberListController.addMember(member);
				System.out.println("Member Successfuly added.");

				Stage stage = (Stage) saveHandler.getScene().getWindow();
				stage.close();

			} else {
				System.out.println("cheching for alert box.");
				Alert alert = new Alert(AlertType.INFORMATION);
				Stage stage = (Stage) saveHandler.getScene().getWindow();
				alert.initOwner(stage);
				alert.setTitle("Invalid Id");
				alert.setHeaderText("Id should be unique.");
				alert.setContentText("Please enter another id. ");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("SQL Problem");
			alert.setContentText(e.getMessage());
		}

	}

	@FXML
	private void cancelHandler(ActionEvent event) {
		Stage stage = (Stage) cancelHandler.getScene().getWindow();
		stage.close();
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	public void SetCallingForm(FrmAddNewMember callingObj) {
		callingForm = callingObj;
	}
}

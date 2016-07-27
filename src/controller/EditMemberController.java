package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.ExecuteQuery;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Address;
import model.LibraryMember;

public class EditMemberController implements Initializable{
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
	private Button cancelHandler;
	@FXML
	private Button saveHandler;

	@FXML
	private void saveHandler() throws SQLException{
		System.out.println("Edit clicked");
		ExecuteQuery runQuery = new ExecuteQuery();
		String memberId = id.getText();
		String fName =firstName.getText();
		String lName =lastName.getText();
		String aStreet =street.getText();
		String aCity =city.getText();
		String aState =state.getText();
		String aZip =zip.getText();
		String phoneNum =phoneNumber.getText();

		String idQuery = "SELECT AddressId from Member Where MemberId='"+memberId+"'";
		ResultSet rsId =runQuery.retrieveData(idQuery);
		int addressId=0 ;

		while(rsId.next()) {
		   addressId =rsId.getInt("AddressId");
		}

		String addrQuery = "UPDATE Address" + " SET Street = '"+aStreet+"',City = '"+aCity+"',State='"+aState+"',Zip='"+aZip+"'"+
		                    " Where AddressId = '"+addressId+"'";
		//System.out.println(addrQuery);

		String memQuery ="UPDATE Member" + " SET FirstName = '"+fName+"',LastName = '"+lName+"',Phone='"+phoneNum+"'"+
                " Where MemberId = '"+memberId+"'";

		boolean addrIsSuccess = runQuery.executeQuery(addrQuery);
		boolean memIsSuccess;
		if(addrIsSuccess) {
			//Address  address = new Address(aStreet,aCity,aState,Integer.parseInt(aZip));
			memIsSuccess = runQuery.executeQuery(memQuery);

			if(memIsSuccess){

				reloadListData();
				System.out.println("Member Successfuly edited and saved.");
				rsId.close();

			}
			else {
				Alert alert = new Alert(AlertType.INFORMATION);
		        Stage stage = (Stage)saveHandler.getScene().getWindow();
		        alert.initOwner(stage);
		        alert.setTitle("Update Failed");
		        alert.setHeaderText("Member not updated.");
		        alert.setContentText("Please try again.");
		        alert.showAndWait();
			}

		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
	        Stage stage = (Stage)saveHandler.getScene().getWindow();
	        alert.initOwner(stage);
	        alert.setTitle("Update Failed");
	        alert.setHeaderText("Address not updated.");
	        alert.setContentText("Please try again.");
	        alert.showAndWait();
		}
		Stage stage = (Stage) cancelHandler.getScene().getWindow();
	    stage.close();
	}
	@FXML
	private void cancelHandler() {
		Stage stage = (Stage) cancelHandler.getScene().getWindow();
	    stage.close();

	}
	private void reloadListData() {
		ResultSet rs = null;
		ExecuteQuery runQuery = new ExecuteQuery();
		String memQuery ="Select MemberId,FirstName,LastName,Street,City,State,Zip,Phone FROM Member INNER JOIN Address on Member.AddressId=Address.AddressId";
		try {
			rs = runQuery.retrieveData(memQuery);
			MemberListController.memData.clear();
			while(rs.next()) {
				int memberId = rs.getInt("MemberId");
				String fName =rs.getString("FirstName");
				String lName =rs.getString("LastName");
				String aStreet =rs.getString("Street");
				String aCity =rs.getString("City");
				String aState =rs.getString("State");
				String aZip =rs.getString("Zip");
				String phoneNum =rs.getString("Phone");
				Address address = new Address(aStreet,aCity,aState,Integer.parseInt(aZip));
				LibraryMember member = new LibraryMember(memberId,fName,lName,address,phoneNum);
				MemberListController.addMember(member);
			}
			MemberListController mlc = new MemberListController();
			mlc.loadDataTable();
			rs.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {


	}
	public void SetCallingForm(LibraryMember member){
		id.setText(Integer.toString(member.getMemberId()));
		firstName.setText(member.getFirstName());
		lastName.setText(member.getLastName());
		street.setText(member.getAddress().getStreet());
		city.setText(member.getAddress().getCity());
		state.setText(member.getAddress().getState());
		zip.setText(Integer.toString(member.getAddress().getZip()));
		phoneNumber.setText(member.getPhoneNumber());
	}



}

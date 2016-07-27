package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import database.ExecuteQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import members.FrmAddNewMember;
import members.FrmEditMember;
import model.Address;
import model.Author;
import model.Book;
import model.LibraryMember;

public class MemberListController implements Initializable{
	Stage newMember;
	Stage editMember;
	@FXML
	private TextField searchMember;
	@FXML
	private TextField searchMemberName;

	@FXML
	private TableView tableView = new TableView<Book>();
	@FXML
	private TableColumn colSn;
	@FXML
	private TableColumn colId;
	@FXML
	private TableColumn colFirstName;
	@FXML
	private TableColumn colLastName;
	@FXML
	private TableColumn colAddress;
	@FXML
	private TableColumn colPhoneNumber;

	@FXML
	private Button deleteMemberbtn;
	@FXML
	private Button editMemberbtn;
	@FXML
	private Button searchByIdBtn;

	LibraryMember member;
	static ObservableList<LibraryMember> memData = FXCollections.observableArrayList();

	public static void addMember(LibraryMember member) {
		memData.add(member);
	}
	@FXML
	private void searchMemberByName() {
		ResultSet rs = null;
		ExecuteQuery runQuery = new ExecuteQuery();
		if(isNotEmpty()) {
			memData.clear();
			String searchName = searchMember.getText();
			String searchQuery ="Select MemberId,FirstName,LastName,Street,City,State,Zip,Phone FROM Member INNER JOIN Address on Member.FirstName='"+ searchName+"' and Address.AddressId=Member.AddressId";
			try {
				rs = runQuery.retrieveData(searchQuery);
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
					member = new LibraryMember(memberId,fName,lName,address,phoneNum);
					memData.add(member);
				}
				tableView.setItems(memData);
				rs.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
	        Stage stage = (Stage)searchMember.getScene().getWindow();
	        alert.initOwner(stage);
	        alert.setTitle("Empty Field");
	        alert.setHeaderText("Field is empty.");
	        alert.setContentText("Please enter a value.");
	        alert.showAndWait();
		}
	}
	private boolean isNumber() {
		try{
		 Integer.parseInt(searchMember.getText());
		 return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
	private boolean isNotEmpty() {

		if(searchMember.getText().equals("")){
			return false;
		}
		else
			return true;
	}
	@FXML
	private void searchMemberById() {
		ResultSet rs = null;

		ExecuteQuery runQuery = new ExecuteQuery();
		if(isNotEmpty()) {
			if(isNumber()){
				memData.clear();
				int searchId = Integer.parseInt(searchMember.getText());
				String searchQuery ="Select MemberId,FirstName,LastName,Street,City,State,Zip,Phone FROM Member INNER JOIN Address on Member.MemberId='"+ searchId+"' and Address.AddressId=Member.AddressId";
				try {
					rs = runQuery.retrieveData(searchQuery);
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
						member = new LibraryMember(memberId,fName,lName,address,phoneNum);
						memData.add(member);
					}
					tableView.setItems(memData);
					rs.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				Alert alert = new Alert(AlertType.INFORMATION);
		        Stage stage = (Stage)searchByIdBtn.getScene().getWindow();
		        alert.initOwner(stage);
		        alert.setTitle("Invalid input");
		        alert.setHeaderText("Input is not correct.");
		        alert.setContentText("Please enter only a number.");
		        alert.showAndWait();
			}
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
	        Stage stage = (Stage)searchMember.getScene().getWindow();
	        alert.initOwner(stage);
	        alert.setTitle("Empty Field");
	        alert.setHeaderText("Field is empty.");
	        alert.setContentText("Please enter a value.");
	        alert.showAndWait();
		}

	}


	public LibraryMember getMember() {
		return member;
	}

	@FXML
	void newMemberListener(ActionEvent event) {
		System.out.println("new button clicked");
		newMember= FrmAddNewMember.getNewMemberInstance();
		newMember.show();
	}

	@FXML
	void editMemberListener() {
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		if(selectedIndex>=0) {
		System.out.println("edit button clicked");
		member = (LibraryMember)tableView.getSelectionModel().getSelectedItem();
		editMember= FrmEditMember.getEditMemberInstance(member);
		editMember.show();
		}
		else {
			// Nothing selected.
	        Alert alert = new Alert(AlertType.INFORMATION);
	        Stage stage = (Stage)editMemberbtn.getScene().getWindow();
	        alert.initOwner(stage);
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Member Selected");
	        alert.setContentText("Please select a member in the table.");
	        alert.showAndWait();
		}
	}

	@FXML
	void deleteMemberListener() {
		ExecuteQuery runQuery = new ExecuteQuery();
		 int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		 member = (LibraryMember)tableView.getSelectionModel().getSelectedItem();
		    if (selectedIndex >= 0) {
		    	tableView.getItems().remove(selectedIndex);
		    	String delQuery = "DELETE from Member WHERE MemberId='"+member.getMemberId()+"'";
		    	boolean isSuccess = runQuery.executeQuery(delQuery);

				if(isSuccess){
					memData.remove(member);
					tableView.setItems(memData);
					System.out.println("Successfully deleted.");
				}

		    } else {
		        // Nothing selected.
		        Alert alert = new Alert(AlertType.INFORMATION);
		        Stage stage = (Stage)deleteMemberbtn.getScene().getWindow();
		        alert.initOwner(stage);
		        alert.setTitle("No Selection");
		        alert.setHeaderText("No Member Selected");
		        alert.setContentText("Please select a member in the table.");
		        alert.showAndWait();
		    }
		System.out.println("delete button clicked");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		ResultSet rs = null;
		memData.clear();
		ExecuteQuery runQuery = new ExecuteQuery();
		String memQuery ="Select MemberId,FirstName,LastName,Street,City,State,Zip,Phone FROM Member INNER JOIN Address on Member.AddressId=Address.AddressId";
		try {
			rs = runQuery.retrieveData(memQuery);
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
				member = new LibraryMember(memberId,fName,lName,address,phoneNum);
				memData.add(member);
			}
			//colSn.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("SN"));
			colId.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("memberId"));

			colFirstName.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("firstName"));
			colLastName.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("lastName"));

			colAddress.setCellValueFactory(new PropertyValueFactory<LibraryMember, Address>("address"));
			colPhoneNumber.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("phoneNumber"));

			tableView.setItems(memData);
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public  void loadDataTable() {

		tableView.setItems(memData);

	}
}

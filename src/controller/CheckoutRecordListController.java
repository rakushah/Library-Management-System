package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.FrmCheckOutRecordListNewEditClass;
import database.ExecuteQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mappedmodel.CheckoutRecordEntryMapped;
import model.CheckOutRecord;
import model.CheckoutRecordEntry;

public class CheckoutRecordListController implements Initializable{
	FrmCheckOutRecordListNewEditClass newForm;
	FrmCheckOutRecordListNewEditClass editForm;
	//@FXML private int entryNO;
	@FXML private TextField txtmemberId;
	@FXML private TextField txtbook;

	@FXML private  TableView<CheckoutRecordEntryMapped> tblCheckOutRecord;
	@FXML private TableColumn<CheckoutRecordEntryMapped, Integer> sno;
    @FXML private TableColumn<CheckoutRecordEntryMapped, String> member;
    @FXML private TableColumn<CheckoutRecordEntryMapped, String> book;
    @FXML private TableColumn<CheckoutRecordEntryMapped, String> issueDate;
    @FXML private TableColumn<CheckoutRecordEntryMapped, String> dueDate;
    @FXML private TableColumn<CheckoutRecordEntryMapped, String> status;
    @FXML private TableColumn<CheckoutRecordEntryMapped, String> copyNum;
    @FXML private TableColumn<CheckoutRecordEntryMapped, Integer> id;
    //@FXML private TableColumn<CheckoutRecordEntryMapped, String> isbn;
     ObservableList<CheckoutRecordEntryMapped> recordListData;

    @FXML protected void handleNewButtonClick(ActionEvent event) {
        newForm= FrmCheckOutRecordListNewEditClass.getNEwCheckOutInstance();
        newForm.setObjCheckoutRecordListController(this);//so we can manipulate its table from anaother controller
        newForm.show();
        //System.out.println("Ma bhitra chhu......");
    }
    @FXML protected void handleEditButtonClick(ActionEvent event) throws SQLException {
    	editForm= FrmCheckOutRecordListNewEditClass.getEditCheckOutInstance();
    	CheckoutRecordEntryMapped entryselected = tblCheckOutRecord.getSelectionModel().getSelectedItem();
    	if(entryselected!=null){
        editForm.setObjCheckoutRecordListController(this);
    	editForm.setSelectedCheckOutEntry(entryselected);//to know which to edit
    	System.out.println("here..."+entryselected.getIsbn());
    	editForm.setDataForEdit();
    	editForm.show();

    	}
    }
    @FXML protected void handleReturnButtonClick(ActionEvent event) throws SQLException {
    	CheckoutRecordEntryMapped entryselected = tblCheckOutRecord.getSelectionModel().getSelectedItem();
    	if(entryselected!=null){
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Confirmation");
    		alert.setHeaderText("Checkin Record");
    		alert.setContentText("Are you sure to checkin the selected record..?");

    		Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == ButtonType.OK){
    			System.out.println("Click...");
        		ExecuteQuery runQuery= new ExecuteQuery();
        		int selectedCheckOutId= entryselected.getCheckOutEntryId();
        		String query="Update CheckOutRecord Set Status= 'Returned' where ChckOutId="+selectedCheckOutId;
        		boolean isSuccess=runQuery.executeQuery(query);
        		if(isSuccess){
        			System.out.println("Sucess");
        			refreshList();
        		}
    		} else {
    		    // ... user chose CANCEL or closed the dialog
    		}

    	}

    }
    @FXML protected void handleSearchByMemberButtonClick(ActionEvent event) throws SQLException {

    	String memberIdToSearch= txtmemberId.getText();
    	if(!isInt(memberIdToSearch)){
    		Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("LMS");
			alert.setHeaderText("Invalid Input");
			String s = "Please input only number.....";
			alert.setContentText(s);
			alert.show();
    	}
    	else{
    		recordListData=searchByMemberName(memberIdToSearch);
        	tblCheckOutRecord.getItems().removeAll(tblCheckOutRecord.getItems());
        	tblCheckOutRecord.setItems(recordListData);
    	}

    }
    @FXML protected void handleSearchByBookButtonClick(ActionEvent event) throws SQLException {
    	String isbnToSearch = txtbook.getText();
    	recordListData=searchByBookIsbn(isbnToSearch);
    	tblCheckOutRecord.getItems().removeAll(tblCheckOutRecord.getItems());
    	tblCheckOutRecord.setItems(recordListData);
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {

			sno.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, Integer>("sn"));
			member.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, String>("memberName"));
			book.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, String>("bookName"));
			issueDate.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, String>("issueDate"));
			dueDate.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, String>("dueDate"));
			status.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, String>("status"));
			copyNum.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, String>("copyNum"));
			id.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, Integer>("checkOutEntryId"));
			//isbn.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, String>("isbn"));
			loadList();
		} catch (Exception e) {
			System.out.println("Error...");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private  void loadList() throws SQLException{

		List<CheckoutRecordEntryMapped> CheckOUtRecordEntryList= new ArrayList<CheckoutRecordEntryMapped>();
		ExecuteQuery runQuery= new ExecuteQuery();
		String query= "select (select count(*) from CheckOutRecord b  where c.ChckOutId>= b.ChckOutId) as 'SNo',m.FirstName||' '||m.LastName as 'MemberName',"
				+"b.Title as 'BookName',c.CheckOutDate as 'CheckoutDate',c.DueDate,c.Status,bc.CopyNum,c.ChckOutId,b.ISBN,m.MemberId "
				+"from CheckOutRecord c "
				+"inner join Member m on m.MemberId=c.MemberId "
				+"inner Join BookCopy bc on bc.CopyId=c.BookCopyId "
				+"inner join Book b on bc.BookIsbn= b.ISBN";
		ResultSet data;
		data = runQuery.retrieveData(query);
		CheckoutRecordEntryMapped newEntryRecord;

		while(data.next()){
			String sNo=data.getString("SNo");
			String bookName=data.getString("ISBN");
			String memberName=data.getString("MemberId");
			String issueDate=data.getString("CheckoutDate");
			String dueDate=data.getString("DueDate");
			String status=data.getString("Status");
			int copyNum1=data.getInt("CopyNum");
			String copyNum= String.valueOf(copyNum1);
			int chckOutId= data.getInt("ChckOutId");

			newEntryRecord= new CheckoutRecordEntryMapped(Integer.parseInt(sNo),bookName,memberName,issueDate,dueDate,status,copyNum);
			newEntryRecord.setCheckOutEntryId(chckOutId);
			newEntryRecord.setIsbn(data.getString("ISBN"));
			newEntryRecord.setMemberId(data.getInt("MemberId"));
			CheckOUtRecordEntryList.add(newEntryRecord);
			System.out.println("copyNum="+newEntryRecord.getCopyNum());
		}
		recordListData =FXCollections.observableArrayList(CheckOUtRecordEntryList);
		tblCheckOutRecord.setItems(recordListData);
		data.close();
	}

	public  void refreshList() throws SQLException{
		tblCheckOutRecord.getItems().removeAll(tblCheckOutRecord.getItems());
		loadList();
	}

	public ObservableList<CheckoutRecordEntryMapped> searchByMemberName(String searchMemId) throws SQLException{
		List<CheckoutRecordEntryMapped> CheckOUtRecordEntryList= new ArrayList<CheckoutRecordEntryMapped>();
		ExecuteQuery runQuery= new ExecuteQuery();
		String query= "select (select count(*) from CheckOutRecord b  where c.ChckOutId>= b.ChckOutId) as 'SNo',m.FirstName||' '||m.LastName as 'MemberName',"
				+"b.Title as 'BookName',c.CheckOutDate as 'CheckoutDate',c.DueDate,c.Status,bc.CopyNum,c.ChckOutId,b.ISBN,m.MemberId "
				+"from CheckOutRecord c "
				+"inner join Member m on m.MemberId=c.MemberId "
				+"inner Join BookCopy bc on bc.CopyId=c.BookCopyId "
				+"inner join Book b on bc.BookIsbn= b.ISBN "
				+"where m.MemberId= "+searchMemId;
		ResultSet data;
		data = runQuery.retrieveData(query);
		CheckoutRecordEntryMapped newEntryRecord;

		while(data.next()){
			String sNo=data.getString("SNo");
			String bookName=data.getString("BookName");
			String memberName=data.getString("MemberName");
			String issueDate=data.getString("CheckoutDate");
			String dueDate=data.getString("DueDate");
			String status=data.getString("Status");
			int copyNum1=data.getInt("CopyNum");
			String copyNum= String.valueOf(copyNum1);
			int chckOutId= data.getInt("ChckOutId");

			newEntryRecord= new CheckoutRecordEntryMapped(Integer.parseInt(sNo),bookName,memberName,issueDate,dueDate,status,copyNum);
			newEntryRecord.setCheckOutEntryId(chckOutId);
			newEntryRecord.setIsbn(data.getString("ISBN"));
			newEntryRecord.setMemberId(data.getInt("MemberId"));
			CheckOUtRecordEntryList.add(newEntryRecord);
			System.out.println("copyNum="+newEntryRecord.getCopyNum());
		}
		data.close();
		return FXCollections.observableArrayList(CheckOUtRecordEntryList);

	}

	public ObservableList<CheckoutRecordEntryMapped> searchByBookIsbn(String searchIsbn) throws SQLException{
		List<CheckoutRecordEntryMapped> CheckOUtRecordEntryList= new ArrayList<CheckoutRecordEntryMapped>();
		ExecuteQuery runQuery= new ExecuteQuery();
		String query= "select (select count(*) from CheckOutRecord b  where c.ChckOutId>= b.ChckOutId) as 'SNo',m.FirstName||' '||m.LastName as 'MemberName',"
				+"b.Title as 'BookName',c.CheckOutDate as 'CheckoutDate',c.DueDate,c.Status,bc.CopyNum,c.ChckOutId,b.ISBN,m.MemberId "
				+"from CheckOutRecord c "
				+"inner join Member m on m.MemberId=c.MemberId "
				+"inner Join BookCopy bc on bc.CopyId=c.BookCopyId "
				+"inner join Book b on bc.BookIsbn= b.ISBN "
				+"where b.ISBN= '"+searchIsbn+"'";
		ResultSet data;
		data = runQuery.retrieveData(query);
		CheckoutRecordEntryMapped newEntryRecord;

		while(data.next()){
			String sNo=data.getString("SNo");
			String bookName=data.getString("BookName");
			String memberName=data.getString("MemberName");
			String issueDate=data.getString("CheckoutDate");
			String dueDate=data.getString("DueDate");
			String status=data.getString("Status");
			int copyNum1=data.getInt("CopyNum");
			String copyNum= String.valueOf(copyNum1);
			int chckOutId= data.getInt("ChckOutId");

			newEntryRecord= new CheckoutRecordEntryMapped(Integer.parseInt(sNo),bookName,memberName,issueDate,dueDate,status,copyNum);
			newEntryRecord.setCheckOutEntryId(chckOutId);
			newEntryRecord.setIsbn(data.getString("ISBN"));
			newEntryRecord.setMemberId(data.getInt("MemberId"));
			CheckOUtRecordEntryList.add(newEntryRecord);
			System.out.println("copyNum="+newEntryRecord.getCopyNum());
		}
		data.close();
		return FXCollections.observableArrayList(CheckOUtRecordEntryList);

	}

	public boolean isInt(String s)
	{
	 try
	  {
		 int i = Integer.parseInt(s); return true; }

	 catch(NumberFormatException er)
	  {
		 return false; }
	}

}

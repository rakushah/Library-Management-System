package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.FrmCheckOutRecordListNewEditClass;
import application.Enums.FormMode;
import database.ExecuteQuery;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import mappedmodel.CheckoutRecordEntryMapped;
import model.Book;
import model.BookCopy;
import model.CheckOutRecord;
import model.CheckoutRecordEntry;
import model.LibraryMember;


public class CheckOUtRecordListAddNewController implements Initializable{
	static int  count=0;//while adding each checkout to show S.no
	private int prevCopyId=0;
	private FrmCheckOutRecordListNewEditClass callingForm;
	CheckOutRecord objCheckOutRecord= new CheckOutRecord();

	@FXML private TextField txtMember;
	@FXML private TextField txtBookIsbn;
	@FXML private ComboBox<BookCopy> cmbSelectBookCopy;
	@FXML private DatePicker dtpIssuedDate;

	@FXML private TableView<CheckoutRecordEntryMapped> tblAddedCheckOut;
	@FXML private TableColumn<CheckoutRecordEntryMapped, Integer> sno;
    @FXML private TableColumn<CheckoutRecordEntryMapped, String> isbn;
    @FXML private TableColumn<CheckoutRecordEntryMapped, String> member;
    @FXML private TableColumn<CheckoutRecordEntryMapped, String> copyNum;
    @FXML private TableColumn<CheckoutRecordEntryMapped, String> book;
    @FXML private TableColumn<CheckoutRecordEntryMapped, String> issueDate;
    @FXML private TableColumn<CheckoutRecordEntryMapped, String> dueDate;
    ObservableList<CheckoutRecordEntryMapped> data;

    @FXML protected void handleAddButtonClick(ActionEvent event) throws SQLException {
        System.out.println(callingForm.getFormMode());//can get Form mode now
        if(cmbSelectBookCopy.getValue()==null){//no book copy selected
        	return;
        }
        if(callingForm.getFormMode()==FormMode.Add){
        	//this will dold each entry untill saved

        	//first display in tabelview what is to be added
        	String member= txtMember.getText();
        	//String book= txtBookIsbn.getText();
        	String issuedDate= dtpIssuedDate.getValue().toString();
        	//String dueDate= "2015/12/2";
        	String selectedIsbn= txtBookIsbn.getText();
        /*	String selectedIsbn= cmbSelectBook.getValue().getIsbn();
        	String selectedAuthor= cmbSelectBook.getValue().getAutherAsList().toString();*/

        	int selectedCopyNum= cmbSelectBookCopy.getValue().getCopyNum();

        	//int selectedCopyNum= cmbSelectBookCopy.getValue().getCopyNum();
        	if(prevCopyId==selectedCopyNum){//already added so dont add again
        		return;
        	}
        	ExecuteQuery runQuery= new ExecuteQuery();
        	ResultSet dataMaxCheckOutDays=runQuery.retrieveData("Select MaxCheckoutNumber from Book where ISBN= '"+selectedIsbn+"'");
        	int maxLength=0;
        	while(dataMaxCheckOutDays.next()){
        		maxLength= dataMaxCheckOutDays.getInt("MaxCheckoutNumber");
        	}
        	dataMaxCheckOutDays.close();
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    		LocalDate date = LocalDate.parse(issuedDate, formatter);
    		date= date.plusDays(maxLength);
        	String dueDate= date.toString();

        	data.add(new CheckoutRecordEntryMapped(++count,selectedIsbn,member,issuedDate,dueDate,"Not Returned",String.valueOf(selectedCopyNum)));

        	//now add entry to record
        	BookCopy objBook= cmbSelectBookCopy.getValue();

        	LibraryMember objMember;
    		ResultSet dataValues;
    		runQuery= new ExecuteQuery();
    		String query= "Select * from Member where MemberId= "+member;

    		dataValues = runQuery.retrieveData(query);
    		dataValues.next();
			int memberId= dataValues.getInt("MemberId");
			String firstname= dataValues.getString("FirstName");
			String lastName= dataValues.getString("LastName");
			objMember= new LibraryMember(memberId,firstname,lastName);
			dataValues.close();

        	CheckoutRecordEntry objEntry=  new CheckoutRecordEntry(objBook,objMember,issuedDate,dueDate,"Not Returned");
        	objCheckOutRecord.addCheckOutEntry(objEntry);
        	prevCopyId=objBook.getCopyNum();

        }
        else if(callingForm.getFormMode()==FormMode.Edit){
        	ExecuteQuery runQuery= new ExecuteQuery();
        	String selectedIsbn= txtBookIsbn.getText();
        	int copyNum= cmbSelectBookCopy.getValue().getCopyNum();
        	ResultSet dataCopyId;
        	dataCopyId=runQuery.retrieveData("Select CopyId from BookCopy where BookIsbn= '"+selectedIsbn+"' and CopyNum= '"+copyNum+"'");
        	int bookCopyId=0;
        	while(dataCopyId.next()){
        		 bookCopyId= dataCopyId.getInt("CopyId");
        	}
        	dataCopyId.close();
        	String checkOutdate= dtpIssuedDate.getValue().toString();
        	int memberId=Integer.valueOf(txtMember.getText());
        	//String dueDate= entry.getDueDate();
        	//String status= entry.getStatus();
        	String query="Update CheckOutRecord Set MemberId= '"+memberId+"', BookCopyId='"
        	           +bookCopyId+"', CheckOutDate= '"+checkOutdate
        	           +"' where ChckOutId='"+callingForm.getSelectedCheckOutEntry().getCheckOutEntryId()+"'";
        	boolean isSuccess=runQuery.executeQuery(query);
    		if(isSuccess){
    			System.out.println("Sucess");
    			callingForm.getObjCheckoutRecordListController().refreshList();
    			callingForm.closeForm();
    		}

        }
    }
    @FXML protected void handlecheckOutButtonClick(ActionEvent event) throws SQLException {
	  try{
		  for(CheckoutRecordEntry entry:objCheckOutRecord.getCheckOUtRecordList()){
	    		int memberId=entry.getLibraryMember().getMemberId();

	    		String selectedIsbn= entry.getBookCopy().getBookISBN();
	    		int copyNum= entry.getBookCopy().getCopyNum();
	    		ResultSet dataCopyId,dataMaxCheckOutDays;
	        	ExecuteQuery runQuery= new ExecuteQuery();
	        	System.out.println("RecordEntry couny.....");
	        	dataCopyId=runQuery.retrieveData("Select CopyId from BookCopy where BookIsbn= '"+selectedIsbn+"' and CopyNum= '"+copyNum+"'");
	        	int bookCopyId=0;
	        	while(dataCopyId.next()){
	        		 bookCopyId= dataCopyId.getInt("CopyId");
	        	}
	        	dataCopyId.close();
	        	dataMaxCheckOutDays=runQuery.retrieveData("Select MaxCheckoutNumber from Book where ISBN= '"+selectedIsbn+"'");
	        	int maxLength=0;
	        	while(dataMaxCheckOutDays.next()){
	        		maxLength= dataMaxCheckOutDays.getInt("MaxCheckoutNumber");
	        	}
	        	dataMaxCheckOutDays.close();
	        	String checkOutdate= entry.getIssueDate();
	        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    		LocalDate date = LocalDate.parse(checkOutdate, formatter);
	    		date= date.plusDays(maxLength);
	        	String dueDate= date.toString();
	        	String status= entry.getStatus();

	    		String query="INSERT INTO CheckOutRecord(MemberId, BookCopyId, CheckOutDate,DueDate,Status) "
	    				+ "VALUES ("+memberId+","+bookCopyId+",'"+checkOutdate+"','"+dueDate+"','"+status+"')";

	    		boolean isSuccess=runQuery.executeQuery(query);
	    		if(isSuccess){
	    			System.out.println("Sucess.........");

	    		}
	    	}
	    	callingForm.getObjCheckoutRecordListController().refreshList();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("LMS");
			alert.setHeaderText("Success");
			String s = "Checkout Sucessfly saved";
			alert.setContentText(s);
			alert.show();
			callingForm.closeForm();
	    	System.out.println("CheckOut click");
	  }
	  catch(Exception e){
		  callingForm.getObjCheckoutRecordListController().refreshList();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("LMS");
			alert.setHeaderText("Error");
			String s = e.getMessage();
			alert.setContentText(s);
			alert.show();
	  }

    }

    @FXML protected void handleCheckBookAvailabilityButtonClick(ActionEvent event) throws SQLException {

    	cmbSelectBookCopy.getItems().removeAll(cmbSelectBookCopy.getItems());
    	loadComboBookCopy(cmbSelectBookCopy,1);
    	if(cmbSelectBookCopy.getItems().size()==0){
    		String book= txtBookIsbn.getText();
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("LMS");
			alert.setHeaderText("Unavailable Book");
			String s = "No Copy of "+book+" are Availabel right now!";
			alert.setContentText(s);
			alert.show();
    	}
    }
    @FXML protected void handlecmbSelectBookValuechanged(ActionEvent event) throws SQLException {
    	//System.out.println("CheckOut click");
    	//cmbSelectBookCopy.valueProperty().set(null);
    	//cmbSelectBookCopy.setValue(null);
    	cmbSelectBookCopy.getItems().removeAll(cmbSelectBookCopy.getItems());
    	if(callingForm.getFormMode()==FormMode.Edit){
    		loadComboBookCopy(cmbSelectBookCopy,2);
    	}
    	else{
    		loadComboBookCopy(cmbSelectBookCopy,1);
    	}
    	if(cmbSelectBookCopy.getItems().size()==0){
    		String book= txtBookIsbn.getText();
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("LMS");
			alert.setHeaderText("Unavailable Book");
			String s = "No Copy of "+book+" are Availabel right now!";
			alert.setContentText(s);
			alert.show();
    	}

    }
    public void SetCallingForm(FrmCheckOutRecordListNewEditClass callingObj){
    	callingForm=callingObj;
    }

    @FXML protected void handleFocusLeaveFromMember(ActionEvent event) throws SQLException {
    	System.out.println("left...");

    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {

			sno.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, Integer>("sn"));
			member.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, String>("memberName"));
			book.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, String>("bookName"));
			issueDate.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, String>("issueDate"));
			dueDate.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, String>("dueDate"));
			copyNum.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntryMapped, String>("copyNum"));
			//isbn.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntry, String>("status"));

			data =FXCollections.observableArrayList();
			/*data = FXCollections.observableArrayList(new CheckoutRecordEntry("1","001A","Book1","Nabin1","2015/01/01","2015/01/10","mr.xyz"),
					 new CheckoutRecordEntry("2","001A","Book1","Nabin1","2015/01/01","2015/01/10","mr.xyz"),
					 new CheckoutRecordEntry("3","001A","Book1","Nabin1","2015/01/01","2015/01/10","mr.xyz"));*/
			//loadComboBook(cmbSelectBook);
			//loadComboMember(cmbSelectMember);
			//loadComboBookCopy(cmbSelectBookCopy,1);
			tblAddedCheckOut.setItems(data);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	private void loadComboMember(ComboBox<LibraryMember> cmbSelectMember) throws SQLException{
		List<LibraryMember> memberList= new ArrayList<LibraryMember>();
		LibraryMember member;
		ResultSet dataValues;
    	ExecuteQuery runQuery= new ExecuteQuery();

		String query= "Select * from Member";

		dataValues = runQuery.retrieveData(query);
		while(dataValues.next()){
			int memberId= dataValues.getInt("MemberId");
			String firstname= dataValues.getString("FirstName");
			String lastName= dataValues.getString("LastName");
			member= new LibraryMember(memberId,firstname,lastName);
			memberList.add(member);
		}
		cmbSelectMember.getItems().addAll(memberList);
		cmbSelectMember.getSelectionModel().selectFirst();
	}

	private void loadComboBook(ComboBox<Book> cmbSelectBook) throws SQLException{
		List<Book> bookList= new ArrayList<Book>();
		Book newBook;
		ResultSet dataValues;
    	ExecuteQuery runQuery= new ExecuteQuery();

		String query= "Select * from Book";

		dataValues = runQuery.retrieveData(query);
		while(dataValues.next()){
			String isbn= dataValues.getString("ISBN");
			String Title= dataValues.getString("Title");
			int MaxCheckoutNumber= dataValues.getInt("MaxCheckoutNumber");
			newBook= new Book(1,isbn, Title, 1, MaxCheckoutNumber);
			bookList.add(newBook);
			cmbSelectBook.setValue(newBook);
		}
		cmbSelectBook.getItems().addAll(bookList);
		cmbSelectBook.getSelectionModel().selectFirst();

	}
	private void loadComboBookCopy(ComboBox<BookCopy> cmbSelectBookCopy,int mode) throws SQLException{
		List<BookCopy> bookCopyList= new ArrayList<BookCopy>();
		BookCopy newBookCopy;
		ResultSet dataValues;
    	ExecuteQuery runQuery= new ExecuteQuery();
    	String selectedIsbn= txtBookIsbn.getText();
    	String query;
    	if(mode==2&&callingForm.getFormMode()==FormMode.Edit){
    		query="Select * from BookCopy where BookIsbn='"+selectedIsbn+"'";
    	}
    	else{
    		query= "Select * from BookCopy bc where bc.CopyId not in (Select BookCopyId from CheckOutRecord"
				+ " where Status='Not Returned')"
				+ "and bc.BookIsbn='"+selectedIsbn+"'";
    	}

		dataValues = runQuery.retrieveData(query);
		while(dataValues.next()){
			System.out.println("Book copy ma chhu..");
			String isbn= dataValues.getString("BookIsbn");
			int CopyNum= dataValues.getInt("CopyNum");
			newBookCopy= new BookCopy(isbn,CopyNum);
			bookCopyList.add(newBookCopy);
		}
		dataValues.close();
		cmbSelectBookCopy.getItems().addAll(bookCopyList);
		cmbSelectBookCopy.getSelectionModel().selectFirst();
	}

	public void setDataForEdit() throws SQLException{
		txtBookIsbn.setText(callingForm.getSelectedCheckOutEntry().getIsbn());
		txtMember.setText(String.valueOf(callingForm.getSelectedCheckOutEntry().getMemberId()));

		loadComboBookCopy(cmbSelectBookCopy,2);
		for(BookCopy b: cmbSelectBookCopy.getItems()){//set book copy

			if(b.getBookISBN().equals(callingForm.getSelectedCheckOutEntry().getIsbn())&&b.getCopyNum()==Integer.parseInt(callingForm.getSelectedCheckOutEntry().getCopyNum())){
				cmbSelectBookCopy.setValue(b);
				break;
			}
		}


		//now set checkout data
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate selectedCheckoutDate = LocalDate.parse(callingForm.getSelectedCheckOutEntry().getIssueDate(), formatter);
		dtpIssuedDate.setValue(selectedCheckoutDate);
	}
}

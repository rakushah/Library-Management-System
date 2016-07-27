package application;

import java.beans.EventHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


import application.Enums.FormMode;
import controller.CheckOUtRecordListAddNewController;
import controller.CheckoutRecordListController;
import database.ExecuteQuery;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mappedmodel.CheckoutRecordEntryMapped;
public class FrmCheckOutRecordListNewEditClass extends Stage{
	private FormMode formMode;
	private CheckoutRecordEntryMapped selectedCheckOutEntry;//to get id for edit
	static FrmCheckOutRecordListNewEditClass newCheckoutInstance;//only two instance of this class can be created
	static FrmCheckOutRecordListNewEditClass editCheckoutInstance;
	CheckoutRecordListController objCheckoutRecordListController;//to refresh list after closing this form keep track of calling controller


	CheckOUtRecordListAddNewController thiscontroller;
	Pane root= new Pane();
	private  FrmCheckOutRecordListNewEditClass(FormMode mode){
		try {
			 formMode= mode;
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("FrmCheckOutRecordListNewEdit.fxml"));
			 root = (Pane) loader.load();
			 System.out.println("2 in class call");
			 thiscontroller = loader.<CheckOUtRecordListAddNewController>getController();
			 thiscontroller.SetCallingForm(this);//so that we need to check which form called controller new or edit

			//root = FXMLLoader.load(getClass().getResource("FrmCheckOutRecordListNewEdit.fxml"));
			Scene scene = new Scene(root,800,500);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			setScene(scene);

			setTitle("Add new");


			//ComboBox cmbSelectMember = (ComboBox)root.lookup("#cmbSelectMember");
			//loadComboMember(cmbSelectMember);

			//ComboBox cmbSelectBook = (ComboBox)root.lookup("#cmbSelectBook");
			//loadComboBook(cmbSelectBook);

			DatePicker dateIssuedDate = (DatePicker)root.lookup("#dtpIssuedDate");
			dateIssuedDate.setValue(LocalDate.now());
			System.out.println("FrmCheckoutNewEdit///aboe table view//....");
			TableView tblAddedCheckOutList = (TableView)root.lookup("#tblAddedCheckOut");
			VBox vBoxContent = (VBox)root.lookup("#vboxContent");
			Label lblHeading = (Label)root.lookup("#lblHeading");

			Button btnAdd = (Button)root.lookup("#btnAdd");
			Button btnCheckOut = (Button)root.lookup("#btnCheckOut");

			if(mode==FormMode.Edit){
				setTitle("Edit");
				tblAddedCheckOutList.setVisible(false);
				btnCheckOut.setVisible(false);
				this.setWidth(530);
				this.setHeight(250);
				btnAdd.setText("Save");
				vBoxContent.setPrefHeight(160);
				vBoxContent.setPrefWidth(470);
				lblHeading.setText("Edit CheckOut Record");
				System.out.println("now i call");
				//System.out.println("here....");

			}
			this.setResizable(false);

			TextField member= (TextField)root.lookup("#txtMember");
			TextField book= (TextField)root.lookup("#txtBookIsbn");
			book.focusedProperty().addListener(new ChangeListener<Boolean>()//on focus cahgne event
			{
			    @Override
			    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
			    {
			        if (newPropertyValue)
			        {
			            System.out.println("Textfield on focus");
			            ResultSet dataMember;
			        	ExecuteQuery runQuery= new ExecuteQuery();
			        	try {

			        	   int memberId=0;
			        	   if(isInt(member.getText())){
			        		   memberId=Integer.valueOf(member.getText());
			        	   }
			        	   System.out.println("memberId="+memberId);
							dataMember=runQuery.retrieveData("Select * from Member where MemberId= "+memberId);
				        	boolean isEmpty = ! dataMember.next();
				        	dataMember.close();
				        	if(isEmpty){
				        		Alert alert = new Alert(AlertType.INFORMATION);
				    			alert.setTitle("LMS");
				    			alert.setHeaderText("Member Not Found");
				    			String s = "Please enter corrct member id first!";
				    			alert.setContentText(s);
				    			alert.show();
				    			member.requestFocus();
				        	}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			        else
			        {

			        }
			    }
			});
			this.setOnCloseRequest(event -> {

			    if(formMode==FormMode.Edit){
			    	System.out.println("Stage is closing edit");
			    	editCheckoutInstance=null;
				}
			    else{
			    	System.out.println("Stage is closing new");
			    	newCheckoutInstance=null;
			    }
			    try {
			    	 objCheckoutRecordListController.refreshList();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			    // Save file
			});
			//show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	public static FrmCheckOutRecordListNewEditClass getNEwCheckOutInstance(){
		if(newCheckoutInstance==null){
			newCheckoutInstance= new FrmCheckOutRecordListNewEditClass(FormMode.Add);
		}
		return newCheckoutInstance;
	}

	public static FrmCheckOutRecordListNewEditClass getEditCheckOutInstance(){
		if(editCheckoutInstance==null){
			editCheckoutInstance= new FrmCheckOutRecordListNewEditClass(FormMode.Edit);
		}
		return editCheckoutInstance;
	}


	public FormMode getFormMode() {
		return formMode;
	}

	public void setSelectedCheckOutEntry(CheckoutRecordEntryMapped entry){
		selectedCheckOutEntry= entry;
	}
	public CheckoutRecordEntryMapped getSelectedCheckOutEntry(){
		return selectedCheckOutEntry;
	}

	public void setDataForEdit() throws SQLException{
		thiscontroller.setDataForEdit();
	}

	public CheckoutRecordListController getObjCheckoutRecordListController() {
		return objCheckoutRecordListController;
	}
	public void setObjCheckoutRecordListController(CheckoutRecordListController objCheckoutRecordListController) {
		this.objCheckoutRecordListController = objCheckoutRecordListController;
	}
	public void closeForm(){
		if(formMode==FormMode.Add){
			newCheckoutInstance=null;
		}
		else{
			editCheckoutInstance=null;
		}
		close();
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

	//@SuppressWarnings("deprecation")



}





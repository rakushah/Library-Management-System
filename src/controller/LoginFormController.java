package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.FrmBookListNewEditClass;
import application.FrmCheckOutRecordListNewEditClass;
import application.FrmDashboard;
import application.Main;
import application.Enums.UserRole;
import database.ExecuteQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginFormController {
	FrmDashboard dashboard;
	private Stage callingForm;//to close if login success we need to hide this form from here
	@FXML private TextField txtUserName;
	@FXML private TextField txtPassWord;

	@FXML protected void handleLoginButtonClick(ActionEvent event) throws SQLException {
		Login();
	}

	public void Login() throws SQLException{
		if((!txtUserName.getText().isEmpty())&&(!txtPassWord.getText().isEmpty())){
			String userName= txtUserName.getText();
			String password=txtPassWord.getText();
			ExecuteQuery runQuery= new ExecuteQuery();
	    	String query="select r.RoleName as Role from User u "
	    			+"inner join UserRole r on r.RoleId=u.RoleId "
	    			+"where UserName= '"+userName+"' and Password='"+password+"'";
	    	ResultSet userDetail=runQuery.retrieveData(query);
	    	String role="None";
	    	while(userDetail.next()){
	    		role=userDetail.getString("Role");
	    	}
	    	if(role.equals("Admin")){
	    		System.out.println(role);
	    		dashboard = FrmDashboard.getDashboardInstance(callingForm);//so we can open login again when logout clicked in dashboard
	    		dashboard.setLoginRole(UserRole.Admin);
	    		dashboard.logedInUser=userName;
	    		dashboard.show();
	    		dashboard.setPermission();
	    		dashboard.setLogedInUser();
	    		callingForm.close();
	    	}
	    	else if(role.equals("Librarian")){
	    		System.out.println(role);
	    		dashboard = FrmDashboard.getDashboardInstance(callingForm);
	    		dashboard.setLoginRole(UserRole.Librarian);
	    		dashboard.logedInUser=userName;
	    		dashboard.show();
	    		dashboard.setPermission();
	    		dashboard.setLogedInUser();
	    		callingForm.close();
	    	}
	    	else if(role.equals("Both")){
	    		System.out.println(role);
	    		dashboard = FrmDashboard.getDashboardInstance(callingForm);
	    		dashboard.setLoginRole(UserRole.Both);
	    		dashboard.logedInUser=userName;
	    		dashboard.show();
	    		dashboard.setPermission();
	    		dashboard.setLogedInUser();
	    		callingForm.close();
	    	}
	    	else{
	    		System.out.println(role);
	    		Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("LMS");
    			alert.setHeaderText("Invalid Login");
    			String s = "Please enter Correct username and password!";
    			alert.setContentText(s);
    			alert.show();
    			txtUserName.requestFocus();
	    	}
		}
	}
	public void SetCallingForm(Stage callingForm){
		this.callingForm=callingForm;
	}

	public Stage getCallingForm(){
		return callingForm;
	}


}

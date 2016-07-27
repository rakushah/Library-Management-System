package application;

import application.Enums.FormMode;
import application.Enums.UserRole;
import controller.BookFrmController;
import controller.CheckOUtRecordListAddNewController;
import controller.DashBoardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;

public class FrmDashboard extends Stage{
	static FrmDashboard newCheckoutInstance;
	public UserRole loginRole;
	public String logedInUser;

	Pane root = new Pane();
	public static FrmDashboard getDashboardInstance(Stage loginFormStage) {
		//if (newCheckoutInstance == null) {
			newCheckoutInstance = new FrmDashboard(loginFormStage);
		//}
		return newCheckoutInstance;
	}
	private FrmDashboard(Stage loginFormStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("FrmDashboard.fxml"));
			root = (Pane) loader.load();

			// root =
			// FXMLLoader.load(getClass().getResource("FrmCheckOutRecordListNewEdit.fxml"));
			Scene scene = new Scene(root, 1300, 700);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			setScene(scene);
			this.setResizable(false);
			setTitle("Dashboard");
			DashBoardController thiscontroller = loader.<DashBoardController>getController();
			 thiscontroller.setObjFrmDashboard(this);
			 thiscontroller.loginForm=loginFormStage;
			 Pane contentPane = (Pane)root.lookup("#contentPane");

			// show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public UserRole getLoginRole() {
		return loginRole;
	}
	public void setLoginRole(UserRole loginRole) {
		this.loginRole = loginRole;
	}

	public void setPermission() {

		 Hyperlink memberLink= (Hyperlink)root.lookup("#memberListHyperlink");
		 Hyperlink bookLink= (Hyperlink)root.lookup("#bookListHyperlink");
		 Hyperlink checkOutLink= (Hyperlink)root.lookup("#checkOutRecordHyperlink");

		 System.out.println(loginRole);
		 if(loginRole==UserRole.Admin){
			 System.out.println("i am admin");
			 memberLink.setVisible(true);
			 bookLink.setVisible(true);
			 checkOutLink.setVisible(false);
		 }
		 if(loginRole==UserRole.Librarian){
			 System.out.println("i am Librarian");
			 checkOutLink.setVisible(true);
			 bookLink.setVisible(false);
			 memberLink.setVisible(false);
		 }
		 if(loginRole==UserRole.Both){
			 System.out.println("i am Both");
			 checkOutLink.setVisible(true);
			 bookLink.setVisible(true);
			 memberLink.setVisible(true);
		 }
	}
	public void setLogedInUser(){
		Label logeinUser = (Label)root.lookup("#logedInUser");
		 System.out.println("Loged in user="+logedInUser);
		 logeinUser.setText(logedInUser);
	}
}

package members;

import application.Enums.FormMode;
import controller.AddNewMemberController;
import controller.BookFrmController;
import controller.MemberListController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FrmAddNewMember extends Stage{
	private FormMode formMode;
	static FrmAddNewMember newMemberInstance;

	Pane root= new Pane();

	private  FrmAddNewMember(FormMode mode){
		try {
			 formMode= mode;
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmlAddNewMember.fxml"));
			 root = (Pane) loader.load();
			Scene scene = new Scene(root,725,425);
			setScene(scene);
			this.setResizable(false);
			setTitle("Add new Book");
			//show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	public static FrmAddNewMember getNewMemberInstance(){

		newMemberInstance= new FrmAddNewMember(FormMode.Add);

		return newMemberInstance;
	}

	public FormMode getFormMode() {
		return formMode;
	}


}

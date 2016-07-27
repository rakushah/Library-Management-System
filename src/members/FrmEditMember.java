package members;

import application.Enums.FormMode;
import controller.EditMemberController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.LibraryMember;

public class FrmEditMember extends Stage{

	private FormMode formMode;
	static FrmEditMember editMemberInstance;
	Pane root= new Pane();
	private  FrmEditMember(LibraryMember member){
		try {
			 //formMode= mode;
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmlEditMember.fxml"));
			 root = (Pane) loader.load();

			 EditMemberController controller = loader.<EditMemberController>getController();
			 controller.SetCallingForm(member);

			//root = FXMLLoader.load(getClass().getResource("FrmCheckOutRecordListNewEdit.fxml"));
			Scene scene = new Scene(root,725,425);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			setScene(scene);
			this.setResizable(false);
			setTitle("Edit Member");


			//show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	public static FrmEditMember getEditMemberInstance(LibraryMember member){
		editMemberInstance= new FrmEditMember(member);

		return editMemberInstance;
	}

}

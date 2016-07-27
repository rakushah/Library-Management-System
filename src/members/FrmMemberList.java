package members;

import java.io.IOException;

import application.BookList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FrmMemberList extends Stage {

	static FrmMemberList newMemberListInstance;
	Pane root = new Pane();

	private FrmMemberList() {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmlMemberList.fxml"));
			try {
				root = (Pane) loader.load();
				Scene scene = new Scene(root, 725, 425);
				setScene(scene);
				this.setResizable(false);
				setTitle("Add new Book");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static FrmMemberList getNewMemberListInstance(){

		newMemberListInstance= new FrmMemberList();

		return newMemberListInstance;
	}


}

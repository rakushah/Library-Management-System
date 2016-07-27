package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import members.FrmMemberList;

public class FrmCheckOutRecordListClass extends Stage {
	static FrmCheckOutRecordListClass newCheckoutInstance;
	Pane root = new Pane();

	private FrmCheckOutRecordListClass() {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("FrmCheckOUtRecordList.fxml"));
		try {
			root = (Pane) loader.load();
			Scene scene = new Scene(root, 980, 500);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			setScene(scene);
			this.setResizable(false);
			setTitle("CheckOut Record List");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static FrmCheckOutRecordListClass getNEwCheckOutInstance() {
		if (newCheckoutInstance == null) {
			newCheckoutInstance = new FrmCheckOutRecordListClass();
		}
		return newCheckoutInstance;
	}
}

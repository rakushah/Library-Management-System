package application;

import java.io.IOException;

import controller.CheckOUtRecordListAddNewController;
import controller.LoginFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			//Parent root = FXMLLoader.load(getClass().getResource("FrmLogin.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("FrmLogin.fxml"));
			 Pane root = (Pane) loader.load();
			 LoginFormController thiscontroller = loader.<LoginFormController>getController();
			 thiscontroller.SetCallingForm(primaryStage);
			Scene scene = new Scene(root,450,300);

			BorderPane btnAdd = (BorderPane)root.lookup("#bp");
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			btnAdd.setOnKeyReleased(event -> {
				  if (event.getCode() == KeyCode.ENTER){
					  try {
						thiscontroller.Login();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }
				});
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login");
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	public static void main(String[] args) {
		launch(args);
	}
}

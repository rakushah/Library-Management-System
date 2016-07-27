package application;

import java.io.IOException;

import application.Enums.FormMode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BookList extends Stage {
	static BookList newCheckoutInstance;
	Pane root = new Pane();

	private BookList() {
		
			FXMLLoader loader = new FXMLLoader(getClass().getResource("FrmBooklist.fxml"));
			try {
				root = (Pane) loader.load();
				Scene scene = new Scene(root, 725, 425);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				setScene(scene);
				this.setResizable(false);
				setTitle("Add new Book");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}
	public static BookList getNEwCheckOutInstance(){
			newCheckoutInstance= new BookList();
		return newCheckoutInstance;
	}
}

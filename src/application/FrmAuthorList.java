package application;

import controller.AuthurDataSelectionControlller;
import controller.BookFrmController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FrmAuthorList extends Stage {
	static FrmAuthorList newAuthorInstance;
	Pane root = new Pane();
	static BookFrmController bookCtrl;

	private FrmAuthorList() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("FrmAuthorList.fxml"));
			root = (Pane) loader.load();
			AuthurDataSelectionControlller controller = loader.<AuthurDataSelectionControlller> getController();
			controller.SetBookFormCtrl(bookCtrl);// so that we need to check
			controller.setStage(this);										// which form called
													// controller new or edit

			Scene scene = new Scene(root, 725, 425);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			setScene(scene);
			this.setResizable(false);
			setTitle("Add Authors of Book");

			// show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static FrmAuthorList getAuthorInstance(BookFrmController bookCtrl) {
		FrmAuthorList.bookCtrl = bookCtrl;
		newAuthorInstance = new FrmAuthorList();
		System.out.println("i returned aurhtrelsit form");
		return newAuthorInstance;
	}
}
package application;

import controller.AddNewAuthorController;
import controller.AuthurDataSelectionControlller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FrmNewAuthor extends Stage {
	static FrmNewAuthor newAuthorInstance;
	Pane root = new Pane();

	private FrmNewAuthor(AuthurDataSelectionControlller authurDataSelectionControlller) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("FrmNewAuthor.fxml"));
			root = (Pane) loader.load();
			AddNewAuthorController controller = loader.<AddNewAuthorController>getController();
			 controller.SetCallingForm(authurDataSelectionControlller);//so that we need to check which form called controller new or edit
			 controller.setStage(this);
			// root =
			// FXMLLoader.load(getClass().getResource("FrmCheckOutRecordListNewEdit.fxml"));
			Scene scene = new Scene(root, 500, 300);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			setScene(scene);
			this.setResizable(false);
			setTitle("Add new Book");

			// show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static FrmNewAuthor getAuthorInstance(AuthurDataSelectionControlller authurDataSelectionControlller) {
		if (newAuthorInstance == null) {
			newAuthorInstance = new FrmNewAuthor(authurDataSelectionControlller);
		}
		return newAuthorInstance;
	}
}

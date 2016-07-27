package application;

import controller.AddNewBookCopyController;
import controller.BookListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Book;

public class FrmAddNewCopy extends Stage {
	static FrmAddNewCopy newAuthorInstance;
	Pane root = new Pane();
	static Book book;

	private FrmAddNewCopy(BookListController bookListCtrl) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("FrmAddCopy.fxml"));
			root = (Pane) loader.load();
			AddNewBookCopyController controller = loader.<AddNewBookCopyController> getController();
			controller.initBook(book,bookListCtrl);// so that we need to check which form
										// called controller new or edit
			controller.setStage(this);
			Scene scene = new Scene(root, 725, 425);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			setScene(scene);
			this.setResizable(false);
			setTitle("Add Book Copy");

			// show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static FrmAddNewCopy getAuthorInstance(Book book,BookListController bookListCtrl) {
		FrmAddNewCopy.book = book;
		newAuthorInstance = new FrmAddNewCopy(bookListCtrl);
		return newAuthorInstance;
	}
}

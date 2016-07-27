package application;

import java.time.LocalDate;

import application.Enums.FormMode;
import controller.BookFrmController;
import controller.BookListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Book;

public class FrmBookListNewEditClass extends Stage {
	private FormMode formMode;
	FrmBookListNewEditClass newCheckoutInstance;// only two instance of
									// this class can be
														// created
	FrmBookListNewEditClass editCheckoutInstance;
	Pane root = new Pane();
	public FrmBookListNewEditClass(){}
	private FrmBookListNewEditClass(FormMode mode,Book book,BookListController bookListCtrl) {
		try {
			formMode = mode;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("FrmBook.fxml"));
			root = (Pane) loader.load();

			BookFrmController controller = loader.<BookFrmController> getController();
			if (mode.equals(FormMode.Edit)) {
				controller.initData(book);
				
			}
			controller.setStage(this);
			controller.SetCallingForm(this,bookListCtrl);

			// root =
			// FXMLLoader.load(getClass().getResource("FrmCheckOutRecordListNewEdit.fxml"));
			Scene scene = new Scene(root, 725, 425);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			setScene(scene);
			this.setResizable(false);
			setTitle("Add new Book");

			// show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public FrmBookListNewEditClass getNEwCheckOutInstance(BookListController bookListCtr) {
		if (newCheckoutInstance == null) {
			newCheckoutInstance = new FrmBookListNewEditClass(FormMode.Add,null,bookListCtr);
		}
		return newCheckoutInstance;
	}

	public FrmBookListNewEditClass getEditCheckOutInstance(Book book1,BookListController bookListCtrl) {
		if (editCheckoutInstance == null) {
			
			editCheckoutInstance = new FrmBookListNewEditClass(FormMode.Edit,book1,bookListCtrl);
		}
		return editCheckoutInstance;
	}

	public FormMode getFormMode() {
		return formMode;
	}
}

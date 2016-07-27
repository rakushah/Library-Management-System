package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import application.FrmAuthorList;
import application.FrmBookListNewEditClass;
import application.Enums.FormMode;
import database.ExecuteQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Address;
import model.Author;
import model.Book;
import utils.Utils;

public class BookFrmController implements Initializable {
	private FrmBookListNewEditClass callingForm;
	@FXML
	TextField tfIsbn;
	@FXML
	TextField tfTitle;
	@FXML
	ListView<Author> lvAuthors;
	@FXML
	TextField tfCheckOutLength;
	Stage authorStage;
	ObservableList<Author> selectedIndexes = FXCollections.observableArrayList();
	FormMode formMode;
	ExecuteQuery runQuery = new ExecuteQuery();
	String isbn, title, checkOutLength;
	List<Author> author;
	String priviourISBN;
	BookListController bookListCtrl;
	Stage stage;

	@FXML
	void saveBookEntry(ActionEvent event) throws SQLException {
		// System.out.println("clicked");
		isbn = tfIsbn.getText();
		title = tfTitle.getText();
		author = lvAuthors.getItems();
		checkOutLength = tfCheckOutLength.getText();
		switch (callingForm.getFormMode()) {
		case Add:
			addNewBook();
			break;
		case Edit:
			editBook();
			break;
		}
	}

	void editBook() {
		String queryEdit = "Update Book set ISBN='" + isbn + "',Title='" + title + "',MaxCheckoutNumber="
				+ checkOutLength + " where ISBN='" + priviourISBN + "'";
		boolean isSuccess = runQuery.executeQuery(queryEdit);
		if (isSuccess) {
			System.out.println("Edit success");
			String queryDeleteBookAuthor = "delete from BookAuthors where BookISBN='" + priviourISBN + "'";
			boolean isDeleted = runQuery.executeQuery(queryDeleteBookAuthor);
			for (Author a : author) {
				String queryAuthorBook = "INSERT INTO BookAuthors(BookISBN, AuthorId) " + "VALUES ('" + isbn + "',"
						+ a.getSn() + ")";
				boolean isSuccessInsert = runQuery.executeQuery(queryAuthorBook);
				if (isSuccessInsert) {
					// System.out.println("Sucess Book Author");
					Utils.dialogShow("Book successfully edited");
					bookListCtrl.refreshList();
					stage.close();
				}
			}
		}
	}

	void addNewBook() {
		String query = "INSERT INTO Book(ISBN, Title, MaxCheckoutNumber) " + "VALUES ('" + isbn + "','" + title + "',"
				+ checkOutLength + ")";
		boolean isSuccess = runQuery.executeQuery(query);
		if (isSuccess) {
			Book book = new Book(bookListCtrl.getListSize() + 1, isbn, title, 1, Integer.parseInt(checkOutLength));
			book.setAutherAsList(author);
			book.setAuthorList(author);
			bookListCtrl.addData(book);
			// System.out.println("Sucess");

			for (Author a : author) {
				String queryAuthorBook = "INSERT INTO BookAuthors(BookISBN, AuthorId) " + "VALUES ('" + isbn + "',"
						+ a.getSn() + ")";
				boolean isSuccessInsert = runQuery.executeQuery(queryAuthorBook);
				if (!isSuccessInsert) {
					// System.out.println("Sucess Book Author");
					Utils.dialogShow("Problem with database");
				}
			}
			Utils.dialogShow("New Book Successfully added");
			bookListCtrl.refreshList();
			stage.close();
		}
	}

	@FXML
	void addAuthor() {
		authorStage = FrmAuthorList.getAuthorInstance(this);
		authorStage.show();
	}

	public void setListView(ObservableList<Author> selectedAuthors) {

		lvAuthors.setItems(selectedAuthors);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		tfIsbn.clear();
		tfIsbn.clear();
		tfTitle.clear();
		tfCheckOutLength.clear();
	}

	public void SetCallingForm(FrmBookListNewEditClass callingObj, BookListController bookListCtrl) {
		callingForm = callingObj;
		this.bookListCtrl = bookListCtrl;
	}

	public void initData(Book book) {
		if (book != null) {
			formMode = FormMode.Edit;
			priviourISBN = book.getIsbn();
			tfIsbn.setText(book.getIsbn());
			tfTitle.setText(book.getName());
			tfCheckOutLength.setText(Integer.toString(book.getCheckOutLenght()));
			lvAuthors.setItems(book.getAutherAsList().sorted());
		} else {
			formMode = FormMode.Add;
		}

	}
}

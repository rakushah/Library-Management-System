package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.ExecuteQuery;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Author;
import model.Book;
import utils.Utils;

public class AddNewBookCopyController implements Initializable {
	static Book book;
	@FXML
	Text txtIsbn;
	@FXML
	Text txtTitle;
	@FXML
	TextField txtFieldCopyNum;
	@FXML
	ListView<Author> listAuthor;
	BookListController bookListCtrl;
	Stage stage;

	@FXML
	void saveBookCopy() {
		String checkCopyIDQuery="Select * from BookCopy where CopyNum="+txtFieldCopyNum.getText()+" and BookIsbn='"+txtIsbn.getText()+"'";
		String insertCopyQuery = "INSERT INTO BookCopy(BookIsbn,CopyNum)  VALUES ('" + txtIsbn.getText() + "',"
				+ Integer.valueOf(txtFieldCopyNum.getText()) + ")";
		ExecuteQuery executeQuery = new ExecuteQuery();
		
		try {
			ResultSet answer=executeQuery.retrieveData(checkCopyIDQuery);
			if (answer.next()) {
				Utils.dialogShow("Duplicate copy Number.. Try Again");
				answer.close();
				return;
			}
			answer.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean isSuccess = executeQuery.executeQuery(insertCopyQuery);
		if (isSuccess) {
			// System.out.println("Copy successfully added");
			Utils.dialogShow("Book copy Successfully added");
			bookListCtrl.refreshList();
			stage.close();
		}
	}

	public void setStage(Stage stage) {
		this.stage=stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void initBook(Book book, BookListController bookListCtrl) {
		this.book = book;
		this.bookListCtrl = bookListCtrl;
		txtIsbn.setText(book.getIsbn());
		txtTitle.setText(book.getName());
		listAuthor.setItems(book.getAutherAsList().sorted());
	}
}

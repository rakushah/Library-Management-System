package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.BookList;
import application.FrmAddNewCopy;
import application.FrmBookListNewEditClass;
import application.FrmCheckOutRecordListNewEditClass;
import database.ExecuteQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Address;
import model.Author;
import model.Book;
import utils.Utils;

public class BookListController implements Initializable {
	Stage newForm;
	Stage editForm;
	@FXML
	TextField tfSearch;
	@FXML
	TableView tblBooklist = new TableView<Book>();
	@FXML
	TableColumn colMaxCheckoutLength;
	@FXML
	TableColumn colRemainingCopies;
	@FXML
	TableColumn colTotalCopies;
	@FXML
	TableColumn colAuthor;
	@FXML
	TableColumn colTitle;
	@FXML
	TableColumn colIsbn;
	@FXML
	TableColumn colSno;

	ObservableList<Book> data;
	List<Book> bookList;
	ExecuteQuery executeQuery = new ExecuteQuery();

	@FXML
	void searchBookListener() {
		String tfSearchKeyWord=tfSearch.getText();
		String searchQueay="select b.*, (select Count(BookIsbn) From BookCopy where CopyId in (Select BookCopyId from CheckOutRecord  where Status='Not Returned') and  BookIsbn=b.ISBN) as notReturnedCount, (Select Count(*) from BookCopy where BookIsbn=b.ISBN) as TotalCopy from Book b where ISBN='"+tfSearchKeyWord+"'";
		prepareBookList(searchQueay);
		//tblBooklist.getItems().removeAll(tblBooklist.getItems());
		tblBooklist.setItems(data);
		//System.out.println(data);

	}

	@FXML
	void newBookListener(ActionEvent event) {
		// String

		//System.out.println("new button clicked");
		newForm = new FrmBookListNewEditClass().getNEwCheckOutInstance(this);
		newForm.show();

	}
	void refreshList(){
		getData();
	}
	@FXML
	void editBookListener() {
		Book book = (Book) tblBooklist.getSelectionModel().getSelectedItem();
		if (book == null) {
			Utils.dialogShow("Please select a row that is to be edited");
		} else {
			FrmBookListNewEditClass instance = new FrmBookListNewEditClass();
			editForm = instance.getEditCheckOutInstance(book,this);
			editForm.show();
		}
	}

	@FXML
	void deleteBookListener() {
		System.out.println("Delete button clicked");
		Book book = (Book) tblBooklist.getSelectionModel().getSelectedItem();
		if (book == null) {
			Utils.dialogShow("Please select a row to delete");
		} else {
			String deleteQUery = "delete from Book where ISBN='" + book.getIsbn() + "'";
			boolean isDeleted = new ExecuteQuery().executeQuery(deleteQUery);
			if (isDeleted) {
				//System.out.println("deleted successfully");
				// removes all selected elements for the table
				Utils.dialogShow("Book Successfully deleted");
				getData();
			}
		}
	}

	@FXML
	void addCopyListener() {

		Book book = (Book) tblBooklist.getSelectionModel().getSelectedItem();
		if (book == null) {
			Utils.dialogShow("Please select a book to add its copy");
		} else {
			FrmAddNewCopy instance = FrmAddNewCopy.getAuthorInstance(book,this);
			instance.show();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		colSno.setCellValueFactory(new PropertyValueFactory<Book, String>("sn"));
		colIsbn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));

		colTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));

		colAuthor.setCellValueFactory(new PropertyValueFactory<Book, List<Author>>("autherAsList"));

		colTotalCopies.setCellValueFactory(new PropertyValueFactory<Book, String>("totalCopies"));
		colRemainingCopies.setCellValueFactory(new PropertyValueFactory<Book, String>("remainingCopies"));
		colMaxCheckoutLength.setCellValueFactory(new PropertyValueFactory<Book, String>("checkOutLenght"));
		getData();

	}

	public int getListSize() {
		return bookList.size();
	}
	
	void prepareBookList(String bookListQuery){
		tblBooklist.getItems().removeAll(tblBooklist.getItems());
		bookList = new ArrayList<Book>();
		ResultSet booksResultSet = null;
		try {
			booksResultSet = executeQuery.retrieveData(bookListQuery);
			while (booksResultSet.next()) {
				String isbn = booksResultSet.getString("ISBN");
				String title = booksResultSet.getString("Title");
				int maxCheckoutNumber = booksResultSet.getInt("MaxCheckoutNumber");
				int noOfCopies = booksResultSet.getInt("TotalCopy");
				int noReturnedCount = booksResultSet.getInt("notReturnedCount");
				Book book = new Book(bookList.size() + 1, isbn, title, noOfCopies - noReturnedCount, maxCheckoutNumber);
				book.setTotalCopies(String.valueOf(noOfCopies));
				bookList.add(book);
			//	System.out.println("book added to list");
			}
			booksResultSet.close();
			data = FXCollections.observableArrayList();
			for (Book book : bookList) {
				String authorListQuery = "Select a.AuthorId,a.FirstName, a.LastName From BookAuthors ba inner "
						+ "join Author a on a.AuthorId=ba.AuthorId where ba.BookISBN='" + book.getIsbn() + "'";
				ResultSet autherReseltSet = executeQuery.retrieveData(authorListQuery);
				// StringBuilder sb = new StringBuilder();
				List<Author> authorList = new ArrayList<>();
				while (autherReseltSet.next()) {
					String firstName = autherReseltSet.getString("FirstName");
					String lastName = autherReseltSet.getString("LastName");
					int authorId = autherReseltSet.getInt("AuthorId");
					Author author = new Author(authorId, firstName, lastName, "", "", null);
					/*
					 * sb.append(firstName); sb.append(lastName);
					 * sb.append(",");
					 */
					authorList.add(author);

				}
				autherReseltSet.close();
				book.setAutherAsList(authorList);
				book.setAuthorList(authorList);
				data.add(book);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getData() {
		String bookListQuery = "select b.*, (select Count(BookIsbn) From BookCopy where CopyId in (Select BookCopyId from CheckOutRecord  where Status='Not Returned') and  BookIsbn=b.ISBN) as notReturnedCount, (Select Count(*) from BookCopy where BookIsbn=b.ISBN) as TotalCopy from Book b";
		prepareBookList(bookListQuery);
		tblBooklist.setItems(data);
	}

	public void addData(Book book) {
		data.add(book);
	}

}

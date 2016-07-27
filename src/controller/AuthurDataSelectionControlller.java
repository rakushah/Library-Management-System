package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.FrmNewAuthor;
import database.ExecuteQuery;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Address;
import model.Author;

public class AuthurDataSelectionControlller implements Initializable {
	public static DataFormat dataFormat = new DataFormat("mydata");
	BookFrmController bookCtrl;
	Stage authorNewStage;
	Stage stage;
	@FXML
	private ListView<Author> listView;

	@FXML
	private TableColumn<Author, String> colCredentials;

	@FXML
	private TableColumn<Author, Address> colAddress;

	@FXML
	private TableColumn<Author, String> colFirstName;
	@FXML
	private TableColumn<Author, String> colLastName;
	@FXML
	private TableColumn<Author, String> colPhone;

	@FXML
	private TableColumn<Author, String> colSn;

	@FXML
	private TableView<Author> tableView;

	@FXML
	private void addAllSelectedAuthors() {
		bookCtrl.setListView(selectedIndexes);
		stage.close();
	}

	@FXML
	private void addNewAuthor() {
		authorNewStage = FrmNewAuthor.getAuthorInstance(this);
		authorNewStage.show();
	}

	ObservableList<Author> selectedIndexes = FXCollections.observableArrayList();
	ObservableList<Author> authors;

	public void SetBookFormCtrl(BookFrmController bookCtrl) {
		this.bookCtrl = bookCtrl;
	}

	public void addAuthor(Author author) {
		System.out.println("new author added");
		authors.add(author);
	}
	public void setStage(Stage stage){
		this.stage=stage;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TODO Auto-generated method stub
		// changed to multiple selection mode
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// set cell value factories
		setCellValueFactories();

		// set Dummy Data for the TableView
		tableView.setItems(getData());

		// ListView items bound with selection index property of tableview
		listView.setItems(selectedIndexes);

		// change listview observable list
		tableView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Author>() {
			@Override
			public void onChanged(Change<? extends Author> change) {
				selectedIndexes.setAll(change.getList());
			}
		});

		// set the Row Factory of the table
		setRowFactory();

		// Set row selection as default
		setRowSelection();

	}

	private void setCellValueFactories() {
		colSn.setCellValueFactory(new PropertyValueFactory("sn"));
		colFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
		colLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
		colCredentials.setCellValueFactory(new PropertyValueFactory("credentials"));
		colPhone.setCellValueFactory(new PropertyValueFactory("phone"));
		colAddress.setCellValueFactory(new PropertyValueFactory("address"));
	}

	/**
	 * Change the cell selection boolean value of TableView
	 */
	public void setRowSelection() {
		tableView.getSelectionModel().clearSelection();
		tableView.getSelectionModel().setCellSelectionEnabled(false);
	}

	/**
	 * Change the cell selection boolean value of TableView
	 */
	public void setCellSelection() {
		tableView.getSelectionModel().clearSelection();
		tableView.getSelectionModel().setCellSelectionEnabled(true);

	}

	/**
	 * Set Row Factory for the TableView
	 */
	public void setRowFactory() {
		tableView.setRowFactory(new Callback<TableView<Author>, TableRow<Author>>() {
			@Override
			public TableRow<Author> call(TableView<Author> p) {
				final TableRow<Author> row = new TableRow<Author>();
				row.setOnDragEntered(new EventHandler<DragEvent>() {
					@Override
					public void handle(DragEvent t) {
						setSelection(row);
					}
				});

				row.setOnDragDetected(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent t) {
						Dragboard db = row.getTableView().startDragAndDrop(TransferMode.COPY);
						ClipboardContent content = new ClipboardContent();
						content.put(dataFormat, "XData");
						db.setContent(content);
						setSelection(row);
						t.consume();

					}
				});
				return row;
			}
		});
	}

	/**
	 * For the changes on table cell selection used only on the TableCell
	 * selection mode
	 *
	 * @param cell
	 */
	private void setSelection(IndexedCell cell) {
		if (cell.isSelected()) {
			System.out.println("False");
			tableView.getSelectionModel().clearSelection(cell.getIndex());
		} else {
			System.out.println("true");
			tableView.getSelectionModel().select(cell.getIndex());
		}
	}

	/**
	 * For the changes on the table row selection used only on TableRow
	 * selection mode
	 *
	 * @param cell
	 * @param col
	 */
	private void setSelection(IndexedCell cell, TableColumn col) {
		System.out.println("Select");
		tableView.getSelectionModel().select(cell.getIndex(), col);
	}

	/**
	 * Provides the Dummy Data for this application in string format
	 *
	 * @param length
	 * @return String
	 */
	public String getDummyText(int length) {
		String most = "abdflntiso";
		String alpha = "abcdefghijkmopqrstuvwxyz";
		StringBuffer b = new StringBuffer();
		int chars = 0;
		for (int i = 0; i < length; i++) {
			if (chars > 2 && chars > Math.random() * 10) {
				b.append(" ");
				chars = 0;
				continue;
			}
			if (chars == 0 || i % 2 == 0) {
				b.append(most.charAt((int) (Math.random() * most.length())));
			} else {
				b.append(alpha.charAt((int) (Math.random() * alpha.length())));
			}
			chars++;
		}
		return b.toString();
	}

	/**
	 * Provides the dummy String
	 *
	 * @param length
	 * @return
	 */
	public String getDummyDigits(int length) {

		StringBuilder b = new StringBuilder();
		for (int i = 0; i < length; i++) {
			b.append((int) (Math.random() * 9));
		}
		return b.toString();
	}

	/**
	 * Provides the dummy Person data.
	 *
	 * @return
	 */
	public ObservableList<Author> getData() {
		ExecuteQuery executeQuery = new ExecuteQuery();
		String selectAllAuthorQuery = "Select a.*,ad.* From Author a inner join Address ad on ad.AddressId=a.AddressId";
		authors = FXCollections.observableArrayList();

		// String authorAddressQuery="Select * From Address where
		// AddressId="+addressid;
		ResultSet authorsResultSet = null;
		try {
			authorsResultSet = executeQuery.retrieveData(selectAllAuthorQuery);
			while (authorsResultSet.next()) {

				int addressid = authorsResultSet.getInt("AddressId");
				int authorId = authorsResultSet.getInt("AuthorId");
				String firstName = authorsResultSet.getString("FirstName");
				String lastName = authorsResultSet.getString("LastName");
				String credentials = authorsResultSet.getString("Credential");
				String phone = authorsResultSet.getString("Phone");
				String street = authorsResultSet.getString("Street");
				String city = authorsResultSet.getString("City");
				String state = authorsResultSet.getString("State");
				//int zip = Integer.valueOf(authorsResultSet.getString("Zip"));
				Address add = new Address(street, city, state, 122);
				Author p = new Author(authorId, firstName, lastName, credentials, phone, add);
				authors.add(p);
				System.out.println("i ma in observable list data of aurthur first");

			}

			authorsResultSet.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return authors;
	}

}

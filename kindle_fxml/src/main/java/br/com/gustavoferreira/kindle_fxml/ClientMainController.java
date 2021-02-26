package br.com.gustavoferreira.kindle_fxml;

import java.net.URL;
import java.util.ResourceBundle;

import db.BookDAO;
import db.UserDAO;
import entities.Book;
import entities.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.util.Callback;

public class ClientMainController implements Initializable {

	private User user;
	@FXML private Button btnExit;
	// Home tab
	@FXML private TableView<Book> homeTable;
	@FXML private TableColumn<Book, String> homeBookTitleColumn;
	@FXML private TableColumn<Book, Integer> homeBookPagesColumn;
	@FXML private TableColumn<Book, String> homeBookWritersColumn;
	@FXML private TableColumn<Book, String> homeBookGenresColumn;
	@FXML private TableColumn<Book, String> homeBookPublisherColumn;
	@FXML private Button btnAddBookToLibrary;
	// Library tab
	@FXML private TableView<Book> libraryTable;
	@FXML private TableColumn<Book, String> bookTitleColumn;
	@FXML private TableColumn<Book, Integer> bookPagesColumn;
	@FXML private TableColumn<Book, String> bookWritersColumn;
	@FXML private TableColumn<Book, String> bookGenresColumn;
	@FXML private TableColumn<Book, String> bookPublisherColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		user = (User) App.getUserData();
		initLibraryTable();
		initHomeTable();
	}

	// Library methods
	private void initLibraryTable() {
		libraryTable.setItems(getLivraryTableData());

		bookTitleColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						return new SimpleStringProperty(param.getValue().getTitle());
					}
				});

		bookPagesColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
						return new SimpleIntegerProperty(param.getValue().getPages()).asObject();
					}
				});

		bookWritersColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						String writers = "";
						for (int i = 0; i < param.getValue().getWriters().size(); i++) {
							writers = writers.concat(param.getValue().getWriters().get(i).getName() + " "
									+ param.getValue().getWriters().get(i).getSurname() + "\n");
						}

						return new SimpleStringProperty(writers);
					}
				});

		bookGenresColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						String genres = "";
						for (int i = 0; i < param.getValue().getGenres().size(); i++) {
							genres = genres.concat(param.getValue().getGenres().get(i).getGenre() + "\n");
						}

						return new SimpleStringProperty(genres);
					}
				});

		bookPublisherColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						return new SimpleStringProperty(param.getValue().getPublisher().getName());
					}
				});
	}

	private ObservableList<Book> getLivraryTableData() {
		return FXCollections.observableArrayList(UserDAO.getUserBooks(user.getId()));
	}

	// Home methods
	private void initHomeTable() {
		homeTable.setItems(getHomeTableData());

		homeBookTitleColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						// TODO Auto-generated method stub
						return new SimpleStringProperty(param.getValue().getTitle());
					}
				});

		homeBookPagesColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
						// TODO Auto-generated method stub
						return new SimpleIntegerProperty(param.getValue().getPages()).asObject();
					}
				});

		homeBookWritersColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						String writers = "";
						for (int i = 0; i < param.getValue().getWriters().size(); i++) {
							writers = writers.concat(param.getValue().getWriters().get(i).getName() + " "
									+ param.getValue().getWriters().get(i).getSurname() + "\n");
						}

						return new SimpleStringProperty(writers);
					}
				});

		homeBookGenresColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						String genres = "";
						for (int i = 0; i < param.getValue().getGenres().size(); i++) {
							genres = genres.concat(param.getValue().getGenres().get(i).getGenre() + "\n");
						}

						return new SimpleStringProperty(genres);
					}
				});

		homeBookPublisherColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						return new SimpleStringProperty(param.getValue().getPublisher().getName());
					}
				});
	}

	private ObservableList<Book> getHomeTableData() {
		return FXCollections.observableArrayList(new BookDAO().list());
	}

	@FXML
	private void addBookToLibrary() {
		TableViewSelectionModel<Book> selectionModel = homeTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);

		try {
			if (selectionModel.isEmpty()) {
				AlertUtil.alert("Selecine um livro", AlertType.INFORMATION);
				return;
			}

			for(int i = 0; i < libraryTable.getItems().size(); i++) {
				if (libraryTable.getItems().get(i).getId() == selectionModel.getSelectedItem().getId()) {
					AlertUtil.alert("Livro já adicionado a biblioteca", AlertType.INFORMATION);
					return;
				}
			}
			
			UserDAO.addBookToLibrary(selectionModel.getSelectedItem(), user);
			libraryTable.setItems(getLivraryTableData());
			AlertUtil.alert("Livro adicionado com sucesso!", AlertType.CONFIRMATION);

		} catch (Exception e) {
			AlertUtil.error("Erro", "Adição de livro à biblioteca", "Erro ao adicionar livro à biblioteca", e);
		}
	}

	@SuppressWarnings("exports")
	public void setUser(User user) {
		this.user = user;
	}
	
	@FXML
	private void exit() {
		App.setRoot("login", null);
		App.changeResizable();
	}
}

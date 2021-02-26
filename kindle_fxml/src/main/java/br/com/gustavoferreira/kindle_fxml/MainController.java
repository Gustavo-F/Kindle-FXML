package br.com.gustavoferreira.kindle_fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import db.BookDAO;
import db.GenreDAO;
import db.PublisherDAO;
import db.WriterDAO;
import entities.Book;
import entities.Genre;
import entities.Publisher;
import entities.Writer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainController implements Initializable {

	private static Stage stage;
	@FXML private Button btnExit;
	// Book Tab
	@FXML private TableView<Book> bookTable;
	@FXML private TableColumn<Book, Integer> bookIDColumn;
	@FXML private TableColumn<Book, String> bookTitleColumn;
	@FXML private TableColumn<Book, Integer> bookPagesColumn;
	@FXML private TableColumn<Book, String> bookWritersColumn;
	@FXML private TableColumn<Book, String> bookGenresColumn;
	@FXML private TableColumn<Book, String> bookPublisherColumn;
	@FXML private Button btnAddBook;
	@FXML private Button btnEditBook;
	@FXML private Button btnRemoveBook;
	@FXML private Button btnRefreshBookTable;
	// Writer Tab
	@FXML private TableView<Writer> writerTable;
	@FXML private TableColumn<Writer, Integer> writerIDColumn;
	@FXML private TableColumn<Writer, String> writerNameColumn;
	@FXML private TableColumn<Writer, String> writerSurnameColumn;
	@FXML private TableColumn<Writer, String> writerEmailColumn;
	@FXML private TextField txtWriterName;
	@FXML private TextField txtWriterSurname;
	@FXML private TextField txtWriterEmail;
	@FXML private Button btnSaveWriter;
	@FXML private Button btnRemoveWriter;
	// Genre Tab
	@FXML private ListView<String> genreList;
	@FXML private TextField txtGenre;
	@FXML private Button btnAddGenre;
	@FXML private Button btnRemoveGenre;
	// Publisher Tab
	@FXML private TableView<Publisher> publisherTable;
	@FXML private TableColumn<Publisher, Integer> publisherIDColumn;
	@FXML private TableColumn<Publisher, String> publisherNameColumn;
	@FXML private TableColumn<Publisher, String> cnpjColumn;
	@FXML private TableColumn<Publisher, String> publisherEmailColumn;
	@FXML private TableColumn<Publisher, String> publisherPhoneColumn;
	@FXML private TextField txtPublisherName;
	@FXML private TextField txtPublisherCNPJ;
	@FXML private TextField txtPublisherEmail;
	@FXML private TextField txtPublisherPhone;
	@FXML private Button btnSavePublisher;
	@FXML private Button btnRemovePublisher;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initGenreList();
		initPublisherTable();
		initWriterTable();
		initBookTable();
	}
	
	// Book methods
	private void initBookTable() {
		bookTable.setItems(getBookTableData());
		
		bookIDColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,Integer>, ObservableValue<Integer>>() {

			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
				// TODO Auto-generated method stub
				return new SimpleIntegerProperty(param.getValue().getId()).asObject();
			}
		});
		
		bookTitleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				// TODO Auto-generated method stub
				return new SimpleStringProperty(param.getValue().getTitle());
			}
		});
		
		bookPagesColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,Integer>, ObservableValue<Integer>>() {

			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
				// TODO Auto-generated method stub
				return new SimpleIntegerProperty(param.getValue().getPages()).asObject();
			}
		});
		
		bookWritersColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				// TODO Auto-generated method stub
				String writers = "";
				for(int i = 0; i < param.getValue().getWriters().size(); i++) {
					writers = writers.concat(param.getValue().getWriters().get(i).getName()
							+ " " + param.getValue().getWriters().get(i).getSurname() + "\n");
				}
				
				return new SimpleStringProperty(writers);
			}
		});
		
		bookGenresColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				// TODO Auto-generated method stub
				String genres = "";
				for(int i = 0; i < param.getValue().getGenres().size(); i++) {
					genres = genres.concat(param.getValue().getGenres().get(i).getGenre() + "\n");
				}
				
				return new SimpleStringProperty(genres);
			}
		});
		
		bookPublisherColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				// TODO Auto-generated method stub
				return new SimpleStringProperty(param.getValue().getPublisher().getName());
			}
		});
	}
	
	private static ObservableList<Book> getBookTableData() {
		return FXCollections.observableArrayList(new BookDAO().list());
	}
	
	@FXML
	private void removeBook() {
		
	}
	
	@FXML
	private void addBook() {
		try {
			FXMLLoader load = new FXMLLoader(App.class.getResource("add_edit_book.fxml"));
			Parent root = (Parent) load.load();
			
			AddEditBookController controller = load.getController();
			controller.setStage(stage);
			
			stage = new Stage();
			stage.setTitle("Cadastrar Livro");
			stage.setResizable(false);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			AlertUtil.error("", "", "", e);
		}
	}
	
	@FXML
	private void editBook() {
		TableViewSelectionModel<Book> selectionModel = bookTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		
		try {
			if(selectionModel.isEmpty()) {
				AlertUtil.alert("Nenhum livro selecionado.", AlertType.INFORMATION);
				return;
			}
			
			FXMLLoader load = new FXMLLoader(App.class.getResource("add_edit_book.fxml"));
			Parent root = (Parent) load.load();
			
			AddEditBookController controller = load.getController();
			controller.setBook(selectionModel.getSelectedItem());
			controller.setStage(stage);
			
			stage = new Stage();
			stage.setTitle("Editar Livro");
			stage.setResizable(false);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			AlertUtil.error("Erro", "Erro ao carregar página", "Erro ao carrecar página de edição de livro", e);
		}
	}
	
	@FXML
	private void refreshBookTable() {
		bookTable.setItems(getBookTableData());
	}
	
	// Writer methods
	private void initWriterTable() {
		writerTable.setItems(getWriterTableData());
		
		writerIDColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Writer,Integer>, ObservableValue<Integer>>() {

			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Writer, Integer> param) {
				return new SimpleIntegerProperty(param.getValue().getId()).asObject();
			}
		});
		
		writerNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Writer,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Writer, String> param) {
				return new SimpleStringProperty(param.getValue().getName());
			}
		});
		
		writerSurnameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Writer,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Writer, String> param) {
				return new SimpleStringProperty(param.getValue().getSurname());
			}
		});
		
		writerEmailColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Writer,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Writer, String> param) {
				return new SimpleStringProperty(param.getValue().getEmail());
			}
		});
		
		editableWriterColumns();
	}

	private void editableWriterColumns() {
		writerNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		writerNameColumn.setOnEditCommit(e->{
			String newName = e.getNewValue();
			int id = e.getTableView().getItems().get(e.getTablePosition().getRow()).getId();
			WriterDAO.editName(newName, id);
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(newName);
		});
		
		writerSurnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		writerSurnameColumn.setOnEditCommit(e->{
			String newSurname = e.getNewValue();
			int id = e.getTableView().getItems().get(e.getTablePosition().getRow()).getId();
			WriterDAO.editSurname(newSurname, id);
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setSurname(newSurname);
		});
		
		writerEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		writerEmailColumn.setOnEditCommit(e->{
			String newEmail = e.getNewValue();
			int id = e.getTableView().getItems().get(e.getTablePosition().getRow()).getId();
			WriterDAO.editEmail(newEmail, id);
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setEmail(newEmail);
		});
	}
	
	private ObservableList<Writer> getWriterTableData(){
		return FXCollections.observableArrayList(new WriterDAO().list());
	}

	@FXML
	private void addWriter() {
		try {
			if(txtWriterName.getText().isBlank()) {
				AlertUtil.alert("Nome em branco!", AlertType.INFORMATION);
				return;
			}
			
			if(txtWriterSurname.getText().isBlank()) {
				AlertUtil.alert("Sobrenome em branco!", AlertType.INFORMATION);
				return;
			}
			
			if(txtWriterEmail.getText().isBlank()) {
				AlertUtil.alert("Email em branco!", AlertType.INFORMATION);
				return;
			}
			
			Writer writer = new Writer();
			writer.setName(txtWriterName.getText());
			writer.setSurname(txtWriterSurname.getText());
			writer.setEmail(txtWriterEmail.getText());
			new WriterDAO().add(writer);
			
			txtWriterName.setText("");
			txtWriterSurname.setText("");
			txtWriterEmail.setText("");
			
			writerTable.setItems(getWriterTableData());
			
		}catch(Exception e) {
			AlertUtil.error("Erro", "Cadastro de escritor", "Erro ao cadastrar escritor", e);
		}
	}
	
	@FXML
	private void removeWriter() {
		TableViewSelectionModel<Writer> selectionModel = writerTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		
		try {
			if(selectionModel.isEmpty()) {
				AlertUtil.alert("Nenhum escritor selecionado", AlertType.INFORMATION);
				return;
			}
			
			Writer writer = selectionModel.getSelectedItem();
			new WriterDAO().remove(writer);
			writerTable.setItems(getWriterTableData());
		}catch(Exception e) {
			
		}
	}
	
	// Genre methods
	private void initGenreList() {
		genreList.setCellFactory(TextFieldListCell.forListView());
		genreList.getItems().setAll(getGenreListData());
		genreList.setOnEditCommit(editGenre());
	}

	private ObservableList<String> getGenreListData() {
		ArrayList<Genre> genres = new GenreDAO().list();
		ArrayList<String> returnList = new ArrayList<>();

		for (int i = 0; i < genres.size(); i++)
			returnList.add(genres.get(i).getGenre());

		return FXCollections.observableArrayList(returnList);
	}
	
	@FXML
	private void addGenre() {
		try {
			if (txtGenre.getText().isBlank()) {
				AlertUtil.alert("Gênero em branco!", AlertType.INFORMATION);
				return;
			}

			Genre genre = new Genre(txtGenre.getText());
			new GenreDAO().add(genre);
			txtGenre.setText("");
			genreList.setItems(getGenreListData());

		} catch (Exception e) {
			Alert alert = AlertUtil.error("Erro", "Cadastro de gênero.", "Erro ao cadastrar gênero.", e);
			alert.showAndWait();
		}
	}

	@FXML
	private void removeGenre() {
		if (genreList.getSelectionModel().isEmpty()) {
			AlertUtil.alert("Nenhum gênero selecionado.", AlertType.INFORMATION);
			return;
		}

		Genre genre = new Genre(genreList.getSelectionModel().getSelectedItem());
		new GenreDAO().remove(genre);

		genreList.setItems(getGenreListData());
	}

	private EventHandler<EditEvent<String>> editGenre() {
		return new EventHandler<ListView.EditEvent<String>>() {

			@Override
			public void handle(EditEvent<String> event) {
				Genre oldGenre = new Genre(genreList.getItems().get(event.getIndex()));
				Genre newGenre = new Genre(event.getNewValue());
				GenreDAO.edit(oldGenre, newGenre);
				genreList.getItems().setAll(getGenreListData());
			}
		};
	}

	// Publisher methods
	private void initPublisherTable() {
		publisherTable.setItems(getPublisherTableData());

		publisherIDColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Publisher, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Publisher, Integer> param) {
						return new SimpleIntegerProperty(param.getValue().getId()).asObject();
					}
				});

		publisherNameColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Publisher, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Publisher, String> param) {
						return new SimpleStringProperty(param.getValue().getName());
					}
				});

		cnpjColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Publisher, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Publisher, String> param) {
						return new SimpleStringProperty(param.getValue().getCnpj());
					}
				});

		publisherEmailColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Publisher, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Publisher, String> param) {
						return new SimpleStringProperty(param.getValue().getEmail());
					}
				});

		publisherPhoneColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Publisher, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Publisher, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone());
					}
				});
		
		editablePublisherColumns();
	}

	private ObservableList<Publisher> getPublisherTableData() {
		return FXCollections.observableArrayList(new PublisherDAO().list());
	}

	private void editablePublisherColumns() {
		publisherNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		publisherNameColumn.setOnEditCommit(e->{
			String newName = e.getNewValue();
			int id = e.getTableView().getItems().get(e.getTablePosition().getRow()).getId();
			PublisherDAO.editName(newName, id);
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(newName);
		});
		
		cnpjColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		cnpjColumn.setOnEditCommit(e->{
			String newCNPJ = e.getNewValue();
			int id = e.getTableView().getItems().get(e.getTablePosition().getRow()).getId();
			PublisherDAO.editCNPJ(newCNPJ, id);
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setCnpj(newCNPJ);
		});
		
		publisherEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		publisherEmailColumn.setOnEditCommit(e->{
			String newEmail = e.getNewValue();
			int id = e.getTableView().getItems().get(e.getTablePosition().getRow()).getId();
			PublisherDAO.editEmail(newEmail, id);
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setEmail(newEmail);
		});
		
		publisherPhoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		publisherPhoneColumn.setOnEditCommit(e->{
			String newPhone = e.getNewValue();
			int id = e.getTableView().getItems().get(e.getTablePosition().getRow()).getId();
			PublisherDAO.editPhone(newPhone, id);
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setPhone(newPhone);
		});
	}
	
	@FXML
	private void addPublisher() {
		try {
			if(txtPublisherName.getText().isEmpty()) {
				AlertUtil.alert("Nome em branco", AlertType.INFORMATION);
				return;
			}
			
			if(txtPublisherCNPJ.getText().isEmpty()) {
				AlertUtil.alert("CNPJ em branco", AlertType.INFORMATION);
				return;
			}
			
			if(txtPublisherEmail.getText().isEmpty()) {
				AlertUtil.alert("Email em branco", AlertType.INFORMATION);
				return;
			}
			
			if(txtPublisherPhone.getText().isEmpty()) {
				AlertUtil.alert("Telefone em branco", AlertType.INFORMATION);
				return;
			}
			
			Publisher publisher = new Publisher();
			publisher.setName(txtPublisherName.getText());
			publisher.setCnpj(txtPublisherCNPJ.getText());
			publisher.setEmail(txtPublisherEmail.getText());
			publisher.setPhone(txtPublisherPhone.getText());
			
			txtPublisherName.setText("");
			txtPublisherCNPJ.setText("");
			txtPublisherEmail.setText("");
			txtPublisherPhone.setText("");
			
			new PublisherDAO().add(publisher);
			publisherTable.setItems(getPublisherTableData());
			
		}catch(Exception e) {
			AlertUtil.error("Erro", "Cadastro de editora", "Erro ao cadastrar editora", e);
		}
	}
	
	@FXML
	private void removePublisher() {
		TableViewSelectionModel<Publisher> selectionModel = publisherTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		
		try {
			if(selectionModel.isEmpty()) {
				AlertUtil.alert("Nenhuma editora selecionada.", AlertType.INFORMATION);
				return;
			}
			
			Publisher publisher = selectionModel.getSelectedItem();
			new PublisherDAO().remove(publisher);
			publisherTable.setItems(getPublisherTableData());
			
		}catch(Exception e) {
			AlertUtil.error("Erro", "Remoção de editora", "Erro ao remover editora.", e);
		}
	}
	
	@SuppressWarnings("exports")
	public static Stage getStage() {
		return stage;
	}
	
	@FXML
	private void exit() {
		App.setRoot("login", null);
		App.changeResizable();
	}
}










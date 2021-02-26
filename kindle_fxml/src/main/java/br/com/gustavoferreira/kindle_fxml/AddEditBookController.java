package br.com.gustavoferreira.kindle_fxml;

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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

@SuppressWarnings({"exports", "unused"})
public class AddEditBookController implements Initializable{
	
	private Book book;
	private Stage stage;
	@FXML private TableView<Writer> allWriters;
	@FXML private TableColumn<Writer, Integer> writerIDColumn;
	@FXML private TableColumn<Writer, String> writerNameColumn;
	@FXML private TableColumn<Writer, String> writerSurnameColumn;
	@FXML private ListView<String> allGenres;
	@FXML private TextField txtTitle;
	@FXML private TextField txtPages;
	@FXML private ComboBox<String> publishers;
	@FXML private TableView<Writer> bookWritersTable;
	@FXML private TableColumn<Writer, Integer> bookWriterIDColumn;
	@FXML private TableColumn<Writer, String> bookwriterNameColumn;
	@FXML private TableColumn<Writer, String> bookWriterSurnameColumn;
	@FXML private ListView<String> bookGenresList;
	@FXML private Button btnAddBookWriter;
	@FXML private Button btnAddBookGenre;
	@FXML private Button btnRemoveBookWriter;
	@FXML private Button btnRemoveBookGenre;
	@FXML private Button btnAdd;
	@FXML private Button btnCancel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initAllWritersTable();
		initBookWritersTable();
		publishers.setItems(getPublishers());
		allGenres.setItems(getAllGenresListData());
	}
	
	private void initAllWritersTable() {
		allWriters.setItems(getAllWritersTableData());
		
		writerIDColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Writer,Integer>, ObservableValue<Integer>>() {
			
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Writer, Integer> param) {
				// TODO Auto-generated method stub
				return new SimpleIntegerProperty(param.getValue().getId()).asObject();
			}
		});
		
		writerNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Writer,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Writer, String> param) {
				// TODO Auto-generated method stub
				return new SimpleStringProperty(param.getValue().getName());
			}
		});
		
		writerSurnameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Writer,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Writer, String> param) {
				// TODO Auto-generated method stub
				return new SimpleStringProperty(param.getValue().getSurname());
			}
		});
	}
	
	private void initBookWritersTable() {
		bookWriterIDColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Writer,Integer>, ObservableValue<Integer>>() {

			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Writer, Integer> param) {
				// TODO Auto-generated method stub
				return new SimpleIntegerProperty(param.getValue().getId()).asObject();
			}
		});
		
		bookwriterNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Writer,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Writer, String> param) {
				// TODO Auto-generated method stub
				return new SimpleStringProperty(param.getValue().getName());
			}
		});
		
		bookWriterSurnameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Writer,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Writer, String> param) {
				// TODO Auto-generated method stub
				return new SimpleStringProperty(param.getValue().getSurname());
			}
		});
	}
	
	private ObservableList<Writer> getAllWritersTableData(){
		return FXCollections.observableArrayList(new WriterDAO().list());
	}
	
	private ObservableList<String> getPublishers() {
		ArrayList<Publisher> publishers = new PublisherDAO().list();
		ArrayList<String> returnArray = new ArrayList<String>(); 
		
		for(int i = 0; i < publishers.size(); i++) {
			returnArray.add(publishers.get(i).getName());
		}
		
		return FXCollections.observableArrayList(returnArray);
	}
	
	private ObservableList<String> getAllGenresListData() {
		ArrayList<Genre> genres = new GenreDAO().list();
		ArrayList<String> returnList = new ArrayList<>();

		for (int i = 0; i < genres.size(); i++)
			returnList.add(genres.get(i).getGenre());

		return FXCollections.observableArrayList(returnList);
	}
	
	@FXML
	private void addBookWriter() {
		TableViewSelectionModel<Writer> selectionModel = allWriters.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
	
		if(selectionModel.isEmpty()) {
			AlertUtil.alert("Nenhum escritor selecionado.", AlertType.INFORMATION);
			return;
		}else if(bookWritersTable.getItems().contains(selectionModel.getSelectedItem())) {
			AlertUtil.alert("Escritor já adicionado", AlertType.INFORMATION);
			return;
		}
		
		bookWritersTable.getItems().add(selectionModel.getSelectedItem());
	}

	@FXML
	private void removeBookWriter() {
		TableViewSelectionModel<Writer> selectionModel = bookWritersTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
	
		if(bookWritersTable.getItems().isEmpty())
			return;
		
		if(selectionModel.isEmpty()) {
			AlertUtil.alert("Selecione um escritor,", AlertType.INFORMATION);
			return;
		}
		
		bookWritersTable.getItems().remove(selectionModel.getSelectedIndex());
	}
	
	@FXML
	private void removeBookGenre() {
		if(bookGenresList.getItems().isEmpty()) {
			return;
		}
		
		if(bookGenresList.getSelectionModel().isEmpty()) {
			AlertUtil.alert("Selecione um gênero.", AlertType.INFORMATION);
			return;
		}
		
		bookGenresList.getItems().remove(bookGenresList.getSelectionModel().getSelectedIndex());
	}
	
	@FXML
	private void addBookGenre() {
		if(allGenres.getSelectionModel().isEmpty()) {
			AlertUtil.alert("Selecione um gêneros.", AlertType.INFORMATION);
			return;
		}else if(bookGenresList.getItems().contains(allGenres.getSelectionModel().getSelectedItem())) {
			AlertUtil.alert("Gênero já adicionado.", AlertType.INFORMATION);
			return;
		}
		
		bookGenresList.getItems().add(allGenres.getSelectionModel().getSelectedItem());
	}
	
	@FXML 
	private void addBook() throws Throwable {
		try {
			if(txtTitle.getText().isBlank()) {
				AlertUtil.alert("Título em branco.", AlertType.INFORMATION);
				return;
			}
			
			if(txtPages.getText().isBlank()) {
				AlertUtil.alert("Páginas em branco.", AlertType.INFORMATION);
				return;
			}
			
			if(publishers.getSelectionModel().getSelectedItem().isBlank()) {
				AlertUtil.alert("Selecione uma editora.", AlertType.INFORMATION);
				return;
			}
			
			Book book = new Book();
			book.setTitle(txtTitle.getText());
			book.setPages(Integer.parseInt(txtPages.getText()));
			new BookDAO().add(book, publishers.getValue());
			
			book.setId(BookDAO.getBookID(book));
			
			for(int i = 0; i < bookWritersTable.getItems().size(); i++) {
				BookDAO.addWriter(book, bookWritersTable.getItems().get(i));
			}
			
			for(int i = 0; i < bookGenresList.getItems().size(); i++) {
					BookDAO.addGenre(book, bookGenresList.getItems().get(i));
			}
			
			AlertUtil.alert("Livro cadastrado com sucesso!", AlertType.CONFIRMATION);
			MainController.getStage().close();
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	@FXML 
	private void cancel() {
		MainController.getStage().close();
	}
	
	public void setBook(Book book) {
		this.book = book;
		
		txtTitle.setText(book.getTitle());
		txtPages.setText(Integer.toString(book.getPages()));
		for(int i = 0; i < book.getWriters().size(); i++) {
			bookWritersTable.getItems().add(book.getWriters().get(i));
		}
		
		for(int i = 0; i < book.getGenres().size(); i++) {
			bookGenresList.getItems().add(book.getGenres().get(i).getGenre());
		}
		
		publishers.setValue(book.getPublisher().getName());
		
		btnAdd.setText("Salvar");
		btnAdd.setOnAction(saveBook());
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private EventHandler<ActionEvent> saveBook(){
		
		return new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Boolean aux;
				ArrayList<String> removedGenres = new ArrayList<String>();
				ArrayList<Writer> removedWriters = new ArrayList<Writer>();
 				
				if (txtTitle.getText().isBlank()) {
					AlertUtil.alert("Título em branco.", AlertType.INFORMATION);
					return;
				}else if(!txtTitle.getText().equals(book.getTitle())){
					BookDAO.editTitle(book.getId(), txtTitle.getText());
				}
				
				if(Integer.parseInt(txtPages.getText()) != book.getPages()) {
					BookDAO.editPages(book.getId(), Integer.parseInt(txtPages.getText()));
				}
				
				if(!publishers.getSelectionModel().getSelectedItem().equals(book.getPublisher().getName())) {
					BookDAO.editPublisher(book.getId(), publishers.getSelectionModel().getSelectedItem());
				}
				
				// remove writers
				for(int i = 0; i < book.getWriters().size(); i++) {
					aux = false;
					
					for(int y = 0; y < bookWritersTable.getItems().size(); y++){
						if(bookWritersTable.getItems().get(y).getId() == book.getWriters().get(i).getId()) {
							aux = true;
							break;
						}
					}
					
					if(!aux) {
						BookDAO.removeWriter(book, book.getWriters().get(i));
						removedWriters.add(book.getWriters().get(i));
					}
				}
				
				// add writers
				for(int i = 0; i < bookWritersTable.getItems().size(); i++) {
					aux = false;
					
					for(int y = 0; y < book.getWriters().size(); y++) {
						if(bookWritersTable.getItems().get(i).getId() == book.getWriters().get(y).getId()) {
							aux = true;
							break;
						}
					}
					
					if(!aux) {
						if(!removedWriters.contains(bookWritersTable.getItems().get(i))) {
							BookDAO.addWriter(book, bookWritersTable.getItems().get(i));
						}
					}
				}
				
				// remove genre
				for(int i = 0; i < book.getGenres().size(); i++) {
					aux = false;
					System.out.println("Removing genre");
					
					for(int y = 0; y < bookGenresList.getItems().size(); y++) {
						if(bookGenresList.getItems().get(y).equals(book.getGenres().get(i).getGenre())) {
							aux = true;
							break;
						}
					}
					
					if(!aux) {
						BookDAO.removeGenre(book, book.getGenres().get(i).getGenre());
						removedGenres.add(book.getGenres().get(i).getGenre());
					}
				}
				
				// add genre
				for(int i = 0; i < bookGenresList.getItems().size(); i++) {
					aux = false;
					
					for(int y = 0; y < book.getGenres().size(); y++) {
						if(bookGenresList.getItems().get(i).equals(book.getGenres().get(y).getGenre())) {
							aux = true;
							break;
						}
					}
					
					if(!aux && !removedGenres.contains(bookGenresList.getItems().get(i))) {
						BookDAO.addGenre(book, bookGenresList.getItems().get(i));
					}
				}
				
				AlertUtil.alert("Livro atualizado com sucesso!", AlertType.CONFIRMATION);
				MainController.getStage().close();
			}
		};
	}
}



















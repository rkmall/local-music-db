package controller.artist;

import database.DaoManager;
import database.DataSource;
import database.command.DaoCommand;
import database.factory.DaoFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import model.Artist;
import view.MusicDB;

import java.util.List;

public class ArtistController {

    private MusicDB mainApp;

    private DaoFactory daoFactory;

    @FXML
    private TableView<Artist> artistTable;

    @FXML
    private Button listArtistBtn;

    @FXML
    private Button listAlbumBtn;

    @FXML
    private Button updateArtistBtn;

    @FXML
    private ProgressBar progressBar;


    @FXML
    public void initialize() {
        daoFactory = new DaoFactory();
        listArtistBtn.setOnAction(e -> listArtists());
        listAlbumBtn.setOnAction(e -> listAlbumForArtist());
        updateArtistBtn.setOnAction(e -> updateArtist());
    }



    public void listArtists() {
        Task<ObservableList<Artist>> task = new Task<ObservableList<Artist>>() {
            @Override
            protected ObservableList<Artist> call() {

                DaoManager manager = daoFactory.createDaoManager();

                Object artistList = manager.executeAndClose(daoManager -> daoManager.getArtistDao().queryAllArtists(2));

                return FXCollections.observableArrayList((List<Artist>)artistList);

                //(DataSource.getInstance().queryAllArtists(DataSource.ORDER_BY_ASC));
            }
        };

        // Handle progress bar bind the progress bar with the task
        artistTable.itemsProperty().bind(task.valueProperty());
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setVisible(true);
        task.setOnSucceeded(e -> progressBar.setVisible(false));
        task.setOnFailed(e -> progressBar.setVisible(false));
        new Thread(task).start();

        // OR this
        //task.setOnSucceeded(e -> artistTable.getItems().setAll(task.getValue()));
        //new Thread(task).start();
    }

    public void listAlbumForArtist() {
        Artist selectedArtist = artistTable.getSelectionModel().getSelectedItem();
        if(selectedArtist == null) {
            System.out.println("No artist selected");
        } else{
            mainApp.showAlbumListLayout(selectedArtist.getId());
        }
    }

    public void updateArtist() {
        final Artist selectedArtist = artistTable.getSelectionModel().getSelectedItem();
        if(selectedArtist == null){
            System.out.println("No artist selected");
        }else {
            boolean okClicked = mainApp.openUpdateArtistDialog(selectedArtist);
            if(okClicked){
                listArtists();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Update Failed!");
                alert.setHeaderText("Server busy");
                alert.setContentText("Try again later");
                alert.showAndWait();
            }
        }
    }

    public void setMainApp(MusicDB app){
        this.mainApp = app;
    }
}

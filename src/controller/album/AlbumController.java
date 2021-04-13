package controller.album;

import database.DaoManager;
import database.command.DaoCommand;
import database.factory.DaoFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import model.Album;
import view.MusicDB;

import java.util.List;

public class AlbumController {

    private MusicDB mainApp;

    private DaoFactory factory;

    @FXML
    private Button backToArtistBtn;

    @FXML
    public void initialize(){

        factory = new DaoFactory();
        backToArtistBtn.setOnAction(e -> backToArtistList());
    }

    @FXML
    private TableView<Album> albumTable;

    public void getArtistAlbums(int artistId) {
        Task<ObservableList<Album>> task = new Task<ObservableList<Album>>() {
            @Override
            protected ObservableList<Album> call() throws Exception {

                DaoManager manager = factory.createDaoManager();

                Object albumList = manager.executeAndClose(new DaoCommand() {
                    @Override
                    public Object execute(DaoManager daoManager) {
                        return daoManager.getAlbumDao().queryAlbumsForArtistId(artistId);
                    }
                });

                return FXCollections.observableArrayList((List<Album>)albumList);

                        //DataSource.getInstance().queryAlbumsForArtistId(artistId));
            }
        };

        albumTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();

        // OR this
        //task.setOnSucceeded(e -> artistTable.getItems().setAll(task.getValue()));
        //new Thread(task).start();
    }

    public void backToArtistList() {
        mainApp.initArtistListLayout();
    }

    public void setMainApp(MusicDB app) {
        this.mainApp = app;
    }

}

package view;

import controller.album.AlbumController;
import controller.artist.ArtistController;
import controller.artist.UpdateArtistController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Artist;

import java.io.IOException;

public class MusicDB extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    // Launch the application
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Entry point of the application
     * Implementation of Application class abstract method start()
     * Set the App's primary stage and initialize
     *      1. Apps root layout
     *      2. ArtistListLayout
     * @param primaryStage: top level JavaFx container constructed by the JavaFx platform
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MusicDB");

        initRootLayout();
        initArtistListLayout();
    }

    /**
     * Initialize main root layout which is the empty container and the
     * wrapper for all other app layout
     */
    public void initRootLayout() {
        try{
            FXMLLoader loader = new FXMLLoader(MusicDB.class.getResource("RootLayout.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout, 800,600);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Initialize ArtistListLayout
     */
    public void initArtistListLayout() {
        try{
            FXMLLoader loader = new FXMLLoader(MusicDB.class.getResource("ArtistListLayout.fxml"));
            BorderPane artistListLayout = loader.load();
            rootLayout.setCenter(artistListLayout);
            ArtistController controller = loader.getController();
            controller.setMainApp(this);
            controller.listArtists();
        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Initialize AlbumListLayout
     * @param artistId: artist id that user selects to get the albums of
     */
    public void showAlbumListLayout(int artistId) {
        try{
            FXMLLoader loader = new FXMLLoader(MusicDB.class.getResource("AlbumListLayout.fxml"));
            BorderPane albumListLayout = loader.load();
            rootLayout.setCenter(albumListLayout);
            AlbumController controller = loader.getController();
            controller.setMainApp(this);
            controller.getArtistAlbums(artistId);
        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Open dialog box to update artist info
     * @param artist: artist that user selects to update
     * @return boolean: true if the update process is successful
     *                  false if the update process fails or cancelled
     */
    public boolean openUpdateArtistDialog(Artist artist) {
        try{
            FXMLLoader loader = new FXMLLoader(MusicDB.class.getResource("UpdateArtistDialog.fxml"));
            AnchorPane updateArtistLayout = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Update Artist");
            dialogStage.initOwner(primaryStage);
            Scene dialogScene = new Scene(updateArtistLayout);
            dialogStage.setScene(dialogScene);

            UpdateArtistController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setToUpdateArtist(artist);

            dialogStage.showAndWait();
            return controller.okComplete();
        }catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Provide primary stage when needed to the app's controllers
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /*// Open database connection upon App start
    @Override
    public void init() throws Exception {
        super.init();
        DataSource.getInstance().getConnection();
        // Exit if cannot connect to db
        if(DataSource.getInstance().getConnection() == null) {
            System.out.println("FATAL ERROR: Could not connect to database.");
            Platform.exit();
        }
    }

    // Close database connection
    @Override
    public void stop() throws Exception {
        super.stop();
        DataSource.getInstance().close();
    }*/
}

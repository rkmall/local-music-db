package controller.artist;

import database.DaoManager;
import database.DataSource;
import database.command.DaoCommand;
import database.factory.DaoFactory;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Artist;

public class UpdateArtistController {

    private Stage dialogStage;

    private Artist artist;

    private DaoFactory factory;

    @FXML
    private TextField artistNameTextField;

    @FXML
    private Button okBtn;

    @FXML
    private Button cancelBtn;

    private boolean isOK;


    public void initialize() {

        factory = new DaoFactory();
        okBtn.setOnAction(e -> handleOk());
        cancelBtn.setOnAction(e -> handleCancel());
    }

    // Set/ Display current artist to be updated in the dialog box
    public void setToUpdateArtist(Artist artist){
        this.artist = artist;
        if(artist == null){
            artistNameTextField.setText("");
        }else{
            artistNameTextField.setText(artist.getName());
        }
    }

    public boolean okComplete() {
        return isOK;
    }

    // Handle update artist operation
    private void handleOk() {
        if(isValidName()){
            String newValue = artistNameTextField.getText().trim();
            int id = artist.getId();

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() {

                    DaoManager manager = factory.createDaoManager();
                    Object update = manager.executeAndClose(daoManager -> daoManager.getArtistDao().updateArtistName(newValue, id));

                    return (Boolean) update;
                }
            };

            // When the task is successful update UI with the task result
            // task returns boolean
            task.setOnSucceeded(e -> {
                isOK = task.valueProperty().get();
                dialogStage.close();
            });

            new Thread(task).start();
        }
    }

    private void handleCancel() {
        dialogStage.close();
    }

    // Validate user input when updating Artist name
    private boolean isValidName() {
        String errorMsg = "";
        if(artistNameTextField.getText() == null || artistNameTextField.getText().length() == 0){
            errorMsg = "Not valid artist name";
        }
        if(errorMsg.length() == 0){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Not Updated!");
            alert.setHeaderText("Type in artist name ");
            alert.setContentText(errorMsg);
            alert.showAndWait();
            return false;
        }
    }

    //
    public void setDialogStage(Stage stage){
        this.dialogStage = stage;
    }
}

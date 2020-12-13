package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.utils.Configuration;

import java.io.IOException;


public class MainController {


    @FXML
    private TextField memoryVolumeText;

    @FXML
    private TextField memoryOSText;

    @FXML
    private TextField maxProcessWorktimeText;

    @FXML
    private TextField prioritiesQuantityText;

    @FXML
    private TextField coresQuantityText;

    @FXML
    private TextField cpuSpeedText;

    @FXML
    private TextField startingProcessesText;


    @FXML
    private void click(ActionEvent event) throws IOException {
        setConfiguration();

        Stage stage = secondWindow();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();


    }

    private void setConfiguration() {
        Configuration.MEMORY_VOLUME = Integer.parseInt(memoryVolumeText.getText());
        Configuration.OS_MEMORY_VOLUME = Integer.parseInt(memoryOSText.getText());
        Configuration.MAX_PROCESS_WORK_TIME = Integer.parseInt(maxProcessWorktimeText.getText());
        Configuration.PRIORITIES_QUANTITY = Integer.parseInt(prioritiesQuantityText.getText());
        Configuration.CORES_QUANTITY = Integer.parseInt(coresQuantityText.getText());
        Configuration.TIMER_DELAY = Integer.parseInt(cpuSpeedText.getText());
        Configuration.STARTING_PROCESSES = Integer.parseInt(startingProcessesText.getText());
    }

    private Stage secondWindow() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxmainwindow.fxml"));
        stage.setTitle("");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);

        return stage;
    }
}
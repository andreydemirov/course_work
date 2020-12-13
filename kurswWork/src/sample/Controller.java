package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.memory.MemoryScheduler;
import sample.utils.Configuration;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class Controller {
    private static Scheduler scheduler;
    private static Timer timer;

    @FXML
    private Button runButton;

    @FXML
    private TextField increaseSpeedText;

    @FXML
    private Button stopEmulationButton;

    @FXML
    private TextArea coreStatusTextArea;

    @FXML
    private TextArea memoryTextArea;

    @FXML
    private TextArea allowedQueueTextArea;

    @FXML
    private TextArea rejectedQueueTextArea;


    public void startEmulation(ActionEvent actionEvent) throws InterruptedException {
        runButton.setDisable(true);
        stopEmulationButton.setDisable(false);

        timer.schedule(Task(), 0, Configuration.TIMER_DELAY);
    }

    public void addProcess(ActionEvent actionEvent) {
        scheduler.queue.add();

        if (!runButton.isDisable()) {
            print();
        }
    }

    public void stopEmulation(ActionEvent actionEvent) throws IOException {
        stopTimer();

        Stage stage = (Stage) stopEmulationButton.getScene().getWindow();
        stage.close();

        stage = openResultWindow();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void print() {
        coreStatusTextArea.setText(String.valueOf(scheduler.cpu));
        memoryTextArea.setText(scheduler.memoryScheduler.toString());
        allowedQueueTextArea.setText(scheduler.queue.toStringAllowedQueue());
        rejectedQueueTextArea.setText(scheduler.queue.toStringRejectedQueue());
    }



    private TimerTask Task() {
        return new TimerTask() {
            @Override
            public void run() {
                print();
                scheduler.run();
            }
        };
    }

    private void stopTimer() {
        timer.cancel();
        timer.purge();
    }

    private Stage openResultWindow() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxresultwindow.fxml"));
        stage.setTitle("");
        stage.setScene(new Scene(root));

        return stage;
    }

    public static Scheduler getScheduler() {
        return scheduler;
    }

    @FXML
    public void initialize() throws InterruptedException {
        sample.utils.Timer.zeroing();
        Data.dataZeroing();
        MemoryScheduler.releaseMemory();

        scheduler = new Scheduler();
        timer = new Timer();

        scheduler.init();

        print();
    }
}

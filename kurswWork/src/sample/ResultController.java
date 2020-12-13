package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.cpu.Core;

public class ResultController {

    @FXML
    private TextField totalProcessesText;

    @FXML
    private TextField rejectedProcessesText;

    @FXML
    private TextField completeProcessesText;

    @FXML
    private TextArea coresCompleteProcessesText;

    @FXML
    private TextField averageWaitingTactsText;

    @FXML
    private TextField idleTimeText;

    @FXML
    private TextField averageRunningTimeText;

    @FXML
    public void initialize() {
        printAverageRunningTimeText();
        printAverageWaitingTime();
        printCompleteProcesses();
        printCoresCompleteProcesses();
        printIdleTime();
        printRejectedProcesses();
        printTotalProcesses();
    }

    private void printTotalProcesses() {
        totalProcessesText.setText(String.valueOf(Controller.getScheduler().queue.getLastID()));
    }

    private void printRejectedProcesses() {
        rejectedProcessesText.setText(String.valueOf(Controller.getScheduler().queue.getRejectedQueue().size()));
    }

    private void printCompleteProcesses() {
        completeProcessesText.setText(String.valueOf(Data.executedProcesses));
    }

    private void printCoresCompleteProcesses() {
        String result = String.format("%-10s%-20s%n", "Cores", "Number of Processes");
        Core[] cores = Controller.getScheduler().cpu.getCores();

        for (int i = 0; i < cores.length; i++) {
            result += String.format("%-10s%-20s%n", "Core " + (i + 1), cores[i].getProcessed());
        }

        coresCompleteProcessesText.setText(result);
    }

    private void printAverageWaitingTime() {
        averageWaitingTactsText.setText(String.valueOf(Data.waitingTime / Controller.getScheduler().queue.getLastID()));
    }

    private void printIdleTime() {
        idleTimeText.setText(String.valueOf(Data.idleTime));
    }

    private void printAverageRunningTimeText() {
        averageRunningTimeText.setText(String.valueOf(Data.runningTime / Data.processesEnteredToCores));
    }

}

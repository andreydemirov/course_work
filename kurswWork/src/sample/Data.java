package sample;



public class Data {
    public static int executedProcesses = 0;
    public static int waitingTime = 0;
    public static int deletedProcesses = 0;
    public static int idleTime = 0;
    public static int runningTime = 0;
    public static int processesEnteredToCores = 0;

    public static void incWaitingTime(Process process) {
        waitingTime += process.getWaitingTime();
    }

    public static void incDeletedProcesses() {
        deletedProcesses++;
    }

    public static void incIdleTime() {
        idleTime++;
    }

    public static void incRunningTime() {
        Data.runningTime++;
    }

    public static void incProcessesEnteredToCores() {
        Data.processesEnteredToCores++;
    }

    public static void incWaitingTime() {
        Data.waitingTime++;
    }

    public static void dataZeroing() {
        executedProcesses = 0;
        waitingTime = 0;
        deletedProcesses = 0;
        idleTime = 0;
        runningTime = 0;
        processesEnteredToCores = 0;
    }

}

package sample;

import sample.memory.MemoryScheduler;
import sample.utils.Configuration;
import sample.utils.State;

import java.util.ArrayList;

public class Queue {
    private ArrayList<ArrayList<Process>> allowedQueue;
    private ArrayList<Process> rejectedQueue;
    private int lastID;

    public Queue() {
        this.allowedQueue = initializeQueue(Configuration.PRIORITIES_QUANTITY);
        this.rejectedQueue = new ArrayList<>();
        this.lastID = 0;
    }

    private ArrayList<ArrayList<Process>> initializeQueue(int size) {
        ArrayList<ArrayList<Process>> queue = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            queue.add(new ArrayList<Process>());
        }

        return queue;
    }

    public void add() {
        this.lastID++;
        Process process = new Process(this.lastID);

        if (MemoryScheduler.fillMemoryBlock(process)) {
            allowedQueue.get(process.getPriority() - 1).add(process);
            process.setState(State.Waiting);
        } else {
            process.setState(State.Deleted);
            rejectedQueue.add(process);
            Data.incDeletedProcesses();
        }
    }

    public void add(int processesQuantity) {
        for (int i = 0; i < processesQuantity; i++) {
            this.add();
        }
    }

    public void queueCleaning() {
        ArrayList<Process> toDelete;
        for (ArrayList<Process> processes : allowedQueue) {
            toDelete = new ArrayList<>();
            for (Process process : processes) {
                if (process.getState() == State.Finished) {
                    toDelete.add(process);
                }
            }
            for (Process process : toDelete) {
                Data.executedProcesses++;
                Data.incWaitingTime(process);
                Data.incDeletedProcesses();
                processes.remove(process);
                try {
                    if (!MemoryScheduler.releaseMemoryBlock(process.getMemoryBlock())) {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    System.out.println("Memory not released");
                }

            }
        }
    }

    public ArrayList<ArrayList<Process>> getAllowedQueue() {
        return allowedQueue;
    }

    public ArrayList<Process> getRejectedQueue() {
        return rejectedQueue;
    }

    public int getLastID() {
        return lastID;
    }

    public String toStringAllowedQueue() {
        String result = String.format("%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s%n",
                "ID", "Name", "Priority", "Memory", "Time", "Time in", "State");

        for (ArrayList<Process> processes : allowedQueue) {
            for (Process process : processes) {
                result += String.format("%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s%n",
                        process.getId(), process.getName(), process.getPriority(),
                        process.getMemory(), process.getTime(), process.getTimeIn(), process.getState());
            }
        }
        return result;
    }

    public String toStringRejectedQueue() {
        String result = String.format("%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s%n",
                "ID", "Name", "Priority", "Memory", "Time", "Time in", "State");

        for (Process process : rejectedQueue) {
                result += String.format("%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s%n",
                        process.getId(), process.getName(), process.getPriority(),
                        process.getMemory(), process.getTime(), process.getTimeIn(), process.getState());
        }
        return result;
    }
}


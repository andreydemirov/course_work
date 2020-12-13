package sample;

import sample.cpu.CPU;
import sample.cpu.Core;
import sample.memory.MemoryScheduler;
import sample.utils.Configuration;
import sample.utils.State;
import sample.utils.Timer;

import java.util.ArrayList;

public class Scheduler {
    CPU cpu;
    MemoryScheduler memoryScheduler;
    Queue queue;

    public Scheduler() {
        this.cpu = new CPU();
        this.memoryScheduler = new MemoryScheduler();
        this.queue = new Queue();
    }

    private void addProcessToCore(int number) throws InterruptedException {
        ArrayList<Process> queue = this.queue.getAllowedQueue().get(number);
        queue.sort(Process.byLessTime);

        for (Process process : queue) {
            for (Core core : cpu.getCores()) {
                if (core.isFree() && (process.getState() != State.Running)) {
                    core.setState(false);
                    core.setProcess(process);
                    process.setState(State.Running);
                    Data.incProcessesEnteredToCores();
                }
            }
            if (process.getState() == State.Waiting)
                Data.incWaitingTime();
        }

        Thread.sleep(Configuration.TIMER_DELAY);
    }

    private void processServiceInCPU() {
        for (Core core : cpu.getCores()) {
            Process process = core.getProcess();
            if (!core.isFree()) {
                if (process.getBurstTime() != process.getTime()) {
                    core.getProcess().setBurstTime(process.getBurstTime() + 1);
                }
                else {
                    process.setState(State.Finished);
                    core.setProcess(null);
                    core.setState(true);

                    core.incProcessed();
                }
            }
        }
    }

    public void init() throws InterruptedException {
        MemoryScheduler.loadingOSIntoMemory();
        queue.add(Configuration.STARTING_PROCESSES);
    }

    @Override
    public String toString() {
        return  cpu + "\n" + memoryScheduler + "\nQueue: " + queue;
    }


    public void run() {
        Timer.incTime();
        Thread[] threads = new Thread[Configuration.PRIORITIES_QUANTITY];
        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        addProcessToCore(finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads[i].start();
        }

        if (Data.deletedProcesses != queue.getLastID()) {
            processServiceInCPU();
            calculateRunningTime();
            queue.queueCleaning();
        }
        else {
            Data.incIdleTime();
        }

    }

    private void calculateRunningTime() {
        for (Core core : cpu.getCores()) {
            if (!core.isFree())
                Data.incRunningTime();
        }
    }
}

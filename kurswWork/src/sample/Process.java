package sample;

import sample.memory.MemoryBlock;
import sample.utils.Configuration;
import sample.utils.State;
import sample.utils.Timer;
import sample.utils.Utils;

import java.util.Comparator;

public class Process {
    private String name;
    private int id;        //param
    private int priority;  //random
    private int memory;    //random
    private int time;      //random
    private int timeIn;
    private int burstTime;
    private State state;
    private MemoryBlock memoryBlock;
    private int waitingTime;

    public Process(int id) {
        this.id = id;
        this.name = Configuration.PROCESS_NAME + id;
        this.priority = Utils.getRandomNumber(Configuration.PRIORITIES_QUANTITY);
        this.memory = Utils.getRandomNumber(Configuration.MIN_PROCESS_MEMORY, Configuration.MAX_PROCESS_MEMORY);
        this.time = Utils.getRandomNumber(Configuration.MAX_PROCESS_WORK_TIME);
        this.timeIn = Timer.getTime();
        this.burstTime = 0;
        this.state = State.Ready;
        this.waitingTime = 0;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getMemory() {
        return memory;
    }

    public int getTime() {
        return time;
    }

    public int getTimeIn() {
        return timeIn;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public State getState() {
        return state;
    }

    public void setMemoryBlock(MemoryBlock memoryBlock) {
        this.memoryBlock = memoryBlock;
    }

    public MemoryBlock getMemoryBlock() {
        return memoryBlock;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public static Comparator<Process> byLessTime = new Comparator<Process>() {
        @Override
        public int compare(Process o1, Process o2) {
            return o1.getTime()-o2.getTime();
        }
    };

    @Override
    public String toString() {
        return  id +
                "{ name='" + name + '\'' +
                ", priority=" + priority +
                ", memory=" + memory +
                ", time=" + time +
                ", timeIn=" + timeIn +
                ", burstTime=" + burstTime +
                ", state=" + state +
                '}';
    }
}

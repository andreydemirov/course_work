package sample.cpu;

import sample.Process;

public class Core {
    private boolean isFree;
    private Process process;
    private int processed = 0;

    public Core() {
        this.isFree = true;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setState(boolean free) {
        isFree = free;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public int getProcessed() {
        return processed;
    }

    public void incProcessed() {
        processed++;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        if (isFree)
            return String.valueOf(isFree);
        return isFree + "\t" + process.getId() + "\t\t\t" + process.getTime() + "\t\t\t\t\t\t" + process.getBurstTime();
    }
}

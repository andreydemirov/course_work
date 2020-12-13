package sample.cpu;

import sample.utils.Configuration;

public class CPU {
    Core [] cores;

    public CPU() {
        this.cores = new Core[Configuration.CORES_QUANTITY];
        fillCores();
    }

    private void fillCores() {
        for (int i = 0; i < cores.length; i++) {
            cores[i] = new Core();
        }
    }

    public Core[] getCores() {
        return cores;
    }

    @Override
    public String toString() {
        String result = String.format("%-10s\t%-10s\t%-20s\t%-20s\t%-20s%n",
                "Core", "State", "Process ID", "Process require time", "Process burst time");

        for (int i = 0; i < cores.length; i++) {
            if (!cores[i].isFree()) {
                result += String.format("%-10s\t%-10s\t%-20s\t%-20s\t%-20s%n",
                        "Core " + (i + 1), cores[i].isFree(), cores[i].getProcess().getId(),
                        cores[i].getProcess().getTime(), cores[i].getProcess().getBurstTime());
            }
            else {
                result += String.format("%-10s\t%-10s%n","Core " + (i + 1), cores[i].isFree());
            }
        }
        return result;
    }
}

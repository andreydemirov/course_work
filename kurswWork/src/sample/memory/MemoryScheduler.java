package sample.memory;

import sample.Process;
import sample.utils.Configuration;

import java.util.ArrayList;

public class MemoryScheduler {
    private static ArrayList<MemoryBlock> memoryBlocks = new ArrayList<>();

    private static MemoryBlock findFreeBlock(int size) {
        memoryBlocks.sort(MemoryBlock.byEnd);
        ArrayList<MemoryBlock> freeMemoryBlocks = new ArrayList<>();
        MemoryBlock freeMemoryBlock;
        int start;
        int end;

        for (int i = 0; i < memoryBlocks.size() - 1; i++) {
            start = memoryBlocks.get(i).getEnd();
            end = memoryBlocks.get(i+1).getStart();
            if (end - start >= size) {
                freeMemoryBlock = new MemoryBlock(start, end);
                freeMemoryBlocks.add(freeMemoryBlock);
            }
        }
        start = memoryBlocks.get(memoryBlocks.size()-1).getEnd();
        end = Configuration.MEMORY_VOLUME;
        if (end - start >= size) {
            freeMemoryBlock = new MemoryBlock(start, end);
            freeMemoryBlocks.add(freeMemoryBlock);
        }

        freeMemoryBlocks.sort(MemoryBlock.byTheMostSuitable);

        if (freeMemoryBlocks.isEmpty()) {
            return  null;
        }
        return freeMemoryBlocks.get(0);
    }

    public static boolean fillMemoryBlock(Process process) {
        MemoryBlock theMostSuitableMemoryBlock = findFreeBlock(process.getMemory());

        if (theMostSuitableMemoryBlock != null) {
            MemoryBlock memoryBlock = new MemoryBlock(theMostSuitableMemoryBlock.getStart(), process.getMemory() + theMostSuitableMemoryBlock.getStart());
            memoryBlocks.add(memoryBlock);
            process.setMemoryBlock(memoryBlock);
            return true;
        }
        return false;
    }

    public static boolean releaseMemoryBlock(MemoryBlock memoryBlock) {
        return memoryBlocks.remove(memoryBlock);
    }

    public static void loadingOSIntoMemory() {
        memoryBlocks.add(new MemoryBlock(0, Configuration.OS_MEMORY_VOLUME));
    }

    public static void releaseMemory() {
        memoryBlocks = new ArrayList<>();
    }

    @Override
    public String toString() {
        String result = String.format("%-10s\t%-10s%n", "Memory block", "Size");

        for (int i = 0; i < memoryBlocks.size(); i++) {
            result += String.format("%-10s\t%-10s%n", "Block " + (i + 1),
                    memoryBlocks.get(i).getEnd()-memoryBlocks.get(i).getStart());
        }

        return result;
    }
}

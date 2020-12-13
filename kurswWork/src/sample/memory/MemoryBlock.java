package sample.memory;

import java.util.Comparator;

public class MemoryBlock {
    private int start;
    private int end;

    public MemoryBlock(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public static Comparator<MemoryBlock> byEnd = new Comparator<MemoryBlock>() {
        @Override
        public int compare(MemoryBlock o1, MemoryBlock o2) {
            return o1.end-o2.end;
        }
    };

    public static Comparator<MemoryBlock> byTheMostSuitable = new Comparator<MemoryBlock>() {
        @Override
        public int compare(MemoryBlock o1, MemoryBlock o2) {
            int sizeO1 = o1.end - o1.start;
            int sizeO2 = o2.end - o2.start;
            return  sizeO1-sizeO2;
        }
    };

    @Override
    public String toString() {
        return '{' + "start=" + start +
                ", end=" + end +
                '}';
    }
}

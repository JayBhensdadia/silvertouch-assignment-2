import java.util.LinkedList;

class MemoryBlock {
    private final int size;
    private boolean allocated;

    public MemoryBlock(int size) {
        this.size = size;
        this.allocated = false;
    }

    public int getSize() {
        return size;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public void allocate() {
        this.allocated = true;
    }

    public void deallocate() {
        this.allocated = false;
    }
}

class MemoryPoolAllocator {
    private final LinkedList<MemoryBlock> memoryPool;

    public MemoryPoolAllocator(int blockSize, int poolSize) {
        this.memoryPool = new LinkedList<>();
        for (int i = 0; i < poolSize; i++) {
            memoryPool.add(new MemoryBlock(blockSize));
        }
    }

    public MemoryBlock allocate() {
        for (MemoryBlock block : memoryPool) {
            if (!block.isAllocated()) {
                block.allocate();
                return block;
            }
        }
        return null; 
    }

    public void deallocate(MemoryBlock block) {
        if (block != null && block.isAllocated()) {
            block.deallocate();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        
        MemoryPoolAllocator allocator = new MemoryPoolAllocator(4, 10);

        
        MemoryBlock block1 = allocator.allocate();
        MemoryBlock block2 = allocator.allocate();

        System.out.println("Block 1 allocated: " + block1);
        System.out.println("Block 2 allocated: " + block2);

        
        allocator.deallocate(block1);
        allocator.deallocate(block2);

        
        MemoryBlock block3 = allocator.allocate();
        MemoryBlock block4 = allocator.allocate();

        System.out.println("Block 3 allocated: " + block3);
        System.out.println("Block 4 allocated: " + block4);
    }
}


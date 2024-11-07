
import java.util.ArrayList;
import java.util.List;

// MemoryBlock class represents a block of memory with its size and allocation status
class MemoryBlock {
    int size; // Size of the memory block
    boolean isAllocated; // Whether the block is allocated or not

    // Constructor to initialize the memory block with a size
    public MemoryBlock(int size) {
        this.size = size;
        this.isAllocated = false; // Initially, the block is not allocated
    }
}

// MemoryManager class manages the memory blocks and allocation strategies
class MemoryManager {
    private List<MemoryBlock> memoryBlocks; // List of memory blocks

    // Constructor to initialize the memory blocks with the given sizes
    public MemoryManager(int[] blockSizes) {
        memoryBlocks = new ArrayList<>();
        for (int size : blockSizes) {
            memoryBlocks.add(new MemoryBlock(size)); // Create a memory block for each size
        }
    }

    // First-Fit Allocation: Allocates memory from the first block that fits
    public boolean firstFit(int requestSize) {
        // Traverse the memory blocks and find the first available block that can fit the request
        for (MemoryBlock block : memoryBlocks) {
            if (!block.isAllocated && block.size >= requestSize) {
                block.isAllocated = true; // Mark the block as allocated
                System.out.println("First-Fit: Allocated " + requestSize + " to block of size " + block.size);
                return true; // Allocation successful
            }
        }
        System.out.println("First-Fit: No suitable block found for " + requestSize);
        return false; // No suitable block found
    }

    // Best-Fit Allocation: Allocates the smallest block that fits the request
    public boolean bestFit(int requestSize) {
        int bestIndex = -1; // Index of the best fitting block
        int minWaste = Integer.MAX_VALUE; // To keep track of the smallest waste

        // Traverse through all blocks and find the one with the least wasted space
        for (int i = 0; i < memoryBlocks.size(); i++) {
            MemoryBlock block = memoryBlocks.get(i);
            // Block should not be allocated and should fit the request
            if (!block.isAllocated && block.size >= requestSize) {
                int waste = block.size - requestSize; // Calculate waste (unused space)
                if (waste < minWaste) {
                    minWaste = waste; // Update the smallest waste
                    bestIndex = i; // Update the best block index
                }
            }
        }

        // If a suitable block is found, allocate it
        if (bestIndex != -1) {
            memoryBlocks.get(bestIndex).isAllocated = true; // Allocate the block
            System.out.println("Best-Fit: Allocated " + requestSize + " to block of size " +
                               memoryBlocks.get(bestIndex).size);
            return true; // Allocation successful
        }

        System.out.println("Best-Fit: No suitable block found for " + requestSize);
        return false; // No suitable block found
    }

    // Worst-Fit Allocation: Allocates the largest available block
    public boolean worstFit(int requestSize) {
        int worstIndex = -1; // Index of the worst fitting block
        int maxWaste = Integer.MIN_VALUE; // To keep track of the largest waste

        // Traverse through all blocks and find the one with the largest wasted space
        for (int i = 0; i < memoryBlocks.size(); i++) {
            MemoryBlock block = memoryBlocks.get(i);
            // Block should not be allocated and should fit the request
            if (!block.isAllocated && block.size >= requestSize) {
                int waste = block.size - requestSize; // Calculate waste (unused space)
                if (waste > maxWaste) {
                    maxWaste = waste; // Update the largest waste
                    worstIndex = i; // Update the worst block index
                }
            }
        }

        // If a suitable block is found, allocate it
        if (worstIndex != -1) {
            memoryBlocks.get(worstIndex).isAllocated = true; // Allocate the block
            System.out.println("Worst-Fit: Allocated " + requestSize + " to block of size " +
                               memoryBlocks.get(worstIndex).size);
            return true; // Allocation successful
        }

        System.out.println("Worst-Fit: No suitable block found for " + requestSize);
        return false; // No suitable block found
    }

    // Display the current memory blocks with their allocation status
    public void displayMemory() {
        for (MemoryBlock block : memoryBlocks) {
            String status = block.isAllocated ? "Allocated" : "Free"; // Check if allocated or free
            System.out.println("Block of size " + block.size + ": " + status); // Display the block status
        }
    }
}

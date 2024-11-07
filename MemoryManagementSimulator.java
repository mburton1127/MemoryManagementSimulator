
// Main class to test the memory management simulator
public class MemoryManagementSimulator {

    public static void main(String[] args) {
        // Initialize memory blocks with different sizes (representing physical memory)
        int[] blockSizes = {100, 500, 200, 300, 600, 250};
        MemoryManager manager = new MemoryManager(blockSizes);

        // Display the initial state of memory
        System.out.println("Initial Memory State:");
        manager.displayMemory();
        System.out.println();

        // Test First-Fit strategy with different memory requests
        System.out.println("Testing First-Fit Strategy:");
        manager.firstFit(150); // Request 150 units of memory
        manager.firstFit(450); // Request 450 units of memory
        manager.firstFit(700); // Request 700 units of memory (this should fail)
        manager.displayMemory(); // Display memory state after allocation
        System.out.println();

        // Reset memory state (create a new MemoryManager instance)
        manager = new MemoryManager(blockSizes);

        // Test Best-Fit strategy with different memory requests
        System.out.println("Testing Best-Fit Strategy:");
        manager.bestFit(150); // Request 150 units of memory
        manager.bestFit(450); // Request 450 units of memory
        manager.bestFit(700); // Request 700 units of memory (this should fail)
        manager.displayMemory(); // Display memory state after allocation
        System.out.println();

        // Reset memory state again
        manager = new MemoryManager(blockSizes);

        // Test Worst-Fit strategy with different memory requests
        System.out.println("Testing Worst-Fit Strategy:");
        manager.worstFit(150); // Request 150 units of memory
        manager.worstFit(450); // Request 450 units of memory
        manager.worstFit(700); // Request 700 units of memory (this should fail)
        manager.displayMemory(); // Display memory state after allocation
    }
}
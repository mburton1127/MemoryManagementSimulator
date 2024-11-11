/*
 * Main class of the Memory Management Simulator
 * @Author - Matthew Burton
 */
public class MemoryManagementSimulator {

    public static void main(String[] args) {

        int[] blockSizes = {100, 500, 200, 300, 600, 250};  // Initialize physical memory blocks with different sizes (sizes are arbitrarily chosen for this project)
        int numberOfPages = 6;  // Number of virtual pages to allocate
        MemoryManager manager = new MemoryManager(blockSizes, numberOfPages);

        // Display the initial state of memory and the page table
        System.out.println(">>>>> Initial Memory State <<<<<");
        manager.displayMemory();  // Show the status of each memory block (all free initially)  
        manager.displayPageTable();  // Initially, no pages are allocated (all entries are -1)
        System.out.println();

        // Test First-Fit strategy with different memory requests
        System.out.println(">>>>> Example for First-Fit Strategy <<<<<");
        System.out.println();
        manager.firstFit(150, 0); // Request 150 units of memory (virtual page 0)
        manager.firstFit(450, 1); // Request 450 units of memory (virtual page 1)
        manager.firstFit(700, 2); // Request 700 units of memory (virtual page 2, should fail)
        manager.displayMemory(); // Show memory state after First-Fit allocation
        manager.displayPageTable(); // Show the updated page table
        System.out.println();

        // Reset memory state and page table for the next test
        manager = new MemoryManager(blockSizes, numberOfPages);

        // Test Best-Fit strategy with different memory requests
        System.out.println(">>>>> Example for Best-Fit Strategy <<<<<");
        System.out.println();
        manager.bestFit(150, 0); // Request 150 units of memory (virtual page 0)
        manager.bestFit(450, 1); // Request 450 units of memory (virtual page 1)
        manager.bestFit(700, 2); // Request 700 units of memory (virtual page 2, should fail)
        manager.displayMemory(); // Show memory state after Best-Fit allocation
        manager.displayPageTable(); // Show the updated page table
        System.out.println();

        // Reset memory state and page table for the next test
        manager = new MemoryManager(blockSizes, numberOfPages);

        // Test Worst-Fit strategy with different memory requests
        System.out.println(">>>>> Example for Worst-Fit Strategy <<<<<");
        System.out.println();
        manager.worstFit(150, 0); // Request 150 units of memory (virtual page 0)
        manager.worstFit(450, 1); // Request 450 units of memory (virtual page 1)
        manager.worstFit(700, 2); // Request 700 units of memory (virtual page 2, should fail)
        manager.displayMemory(); // Show memory state after Worst-Fit allocation
        manager.displayPageTable(); // Show the updated page table
        System.out.println();

        // Reset memory state and page table for the next test
        manager = new MemoryManager(blockSizes, numberOfPages);

        // Test Next-Fit strategy with different memory requests
        System.out.println(">>>>> Example for Next-Fit Strategy <<<<<");
        System.out.println();
        manager.nextFit(150, 0); // Request 150 units of memory (virtual page 0)
        manager.nextFit(450, 1); // Request 450 units of memory (virtual page 1)
        manager.nextFit(700, 2); // Request 700 units of memory (virtual page 2, should fail)
        manager.displayMemory(); // Show memory state after Next-Fit allocation
        manager.displayPageTable(); // Show the updated page table
    }
}

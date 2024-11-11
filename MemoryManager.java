import java.util.ArrayList;
import java.util.List;

/*
 * MemoryBlock class represents a physical memory block (or frame)
 */
class MemoryBlock {

    int size;  // Size of the memory block (physical frame size)
    boolean isAllocated;  // Allocation status (whether the block is allocated or not)

    /*
     * Constructor to initialize the memory block with a given size
     * @Param size - integer representing the size of the memory block
     */
    public MemoryBlock(int size) {
        this.size = size;
        this.isAllocated = false;  // Initially, memory block is not allocated
    }
}

/*
 * PageTable class represents a virtual page to physical frame mapping (Page Table)
 */
class PageTable {

    private List<Integer> pageTableEntries;  // Each entry will map a virtual page to a physical frame
 
    /* 
     * Constructor to initialize the page table with a number of virtual pages
     * @Param numberOfPages - integer representing the number of virtual pages to be initialized with the PageTable Constructor
     */
    public PageTable(int numberOfPages) {
        pageTableEntries = new ArrayList<>();
        for (int i = 0; i < numberOfPages; i++) {
            pageTableEntries.add(-1);  // Initially, no virtual pages are mapped to any physical frames (-1 means unallocated)
        }
    }

    /*
     * Method to map a virtual page to a physical frame (frameIndex) when a block is allocated
     * @Param virtualPage - integer representing the specific virtual page
     * @Param physicalFrameIndex - integer representing a specific location in physical memory
     * @Return - none
     */
    public void mapPageToFrame(int virtualPage, int physicalFrameIndex) {
        if (virtualPage >= 0 && virtualPage < pageTableEntries.size()) {
            pageTableEntries.set(virtualPage, physicalFrameIndex);  // Set the page table entry for the given virtual page
        }
    }

    /*
     * Displays the current mapping of virtual pages to physical frames
     * @Return - none
     */
    public void displayPageTable() {
        System.out.println("Page Table:");
        for (int i = 0; i < pageTableEntries.size(); i++) {
            // Print each entry mapping a virtual page to a physical frame
            System.out.println("Virtual Page " + i + " -> Physical Frame: " + pageTableEntries.get(i));
        }
    }
}

/*
 * MemoryManager class manages memory block allocations using different allocation strategies
 */
class MemoryManager {

    private List<MemoryBlock> memoryBlocks;  // List of physical memory blocks (frames)
    private PageTable pageTable;  // Page table to map virtual pages to physical frames
    private int lastAllocatedIndex = 0;  // Keeps track of the last allocation position for Next-Fit

 
    /*
     * Constructor to initialize memory manager with memory blocks and a page table
     * @Param blockSizes - array of integers simulating block sizes for physical memory
     * @Param numberOfPages - integer representing the number of virtual pages managed by the memory management simulator
     */
    public MemoryManager(int[] blockSizes, int numberOfPages) {
        memoryBlocks = new ArrayList<>();
        for (int size : blockSizes) {
            memoryBlocks.add(new MemoryBlock(size));  // Create and add memory blocks based on given sizes
        }
        pageTable = new PageTable(numberOfPages);  // Initialize the page table with a number of pages
    }

    /*
     * First-Fit Allocation: Allocate the first block that fits
     * @Param requestSize - integer representing the amount of memory that a program (or virtual page) wants to allocate from the available physical memory blocks
     * @Param virtualPage - integer representing the specific virtual page that we are working on
     * @Return - returns a boolean representing a match for this allocation type
     */
    public boolean firstFit(int requestSize, int virtualPage) {
        for (int i = 0; i < memoryBlocks.size(); i++) {
            MemoryBlock block = memoryBlocks.get(i);
            if (!block.isAllocated && block.size >= requestSize) {
                block.isAllocated = true;  // Mark the block as allocated
                pageTable.mapPageToFrame(virtualPage, i);  // Map the virtual page to this physical frame
                System.out.println("First-Fit: Allocated " + requestSize + " to block of size " + block.size);
                return true;
            }
        }
        // If no suitable block is found
        System.out.println("First-Fit: No suitable block found for " + requestSize);
        return false;
    }

    /*
     * Best-Fit Allocation: Allocate the smallest block that fits the request size
     * @Param requestSize - integer representing the amount of memory that a program (or virtual page) wants to allocate from the available physical memory blocks
     * @Param virtualPage - integer representing the specific virtual page that we are working on
     * @Return - returns a boolean representing a match for this allocation type
     */
    public boolean bestFit(int requestSize, int virtualPage) {
        int bestIndex = -1;
        int minWaste = Integer.MAX_VALUE;

        // Search for the best-fitting block
        for (int i = 0; i < memoryBlocks.size(); i++) {
            MemoryBlock block = memoryBlocks.get(i);
            if (!block.isAllocated && block.size >= requestSize) {
                int waste = block.size - requestSize;  // Calculate wasted space
                if (waste < minWaste) {
                    minWaste = waste;
                    bestIndex = i;  // Track the block with the least waste
                }
            }
        }

        if (bestIndex != -1) {
            memoryBlocks.get(bestIndex).isAllocated = true;  // Mark the best block as allocated
            pageTable.mapPageToFrame(virtualPage, bestIndex);  // Map the virtual page to the best-fitting block
            System.out.println("Best-Fit: Allocated " + requestSize + " to block of size " + memoryBlocks.get(bestIndex).size);
            return true;
        }
        // If no suitable block is found
        System.out.println("Best-Fit: No suitable block found for " + requestSize);
        return false;
    }
 
    /*
     * Worst-Fit Allocation: Allocate the largest block that fits the request size
     * @Param requestSize - integer representing the amount of memory that a program (or virtual page) wants to allocate from the available physical memory blocks
     * @Param virtualPage - integer representing the specific virtual page that we are working on
     * @Return - returns a boolean representing a match for this allocation type
     */
    public boolean worstFit(int requestSize, int virtualPage) {
        int worstIndex = -1;
        int maxWaste = Integer.MIN_VALUE;

        // Search for the worst-fitting block (largest block with enough space)
        for (int i = 0; i < memoryBlocks.size(); i++) {
            MemoryBlock block = memoryBlocks.get(i);
            if (!block.isAllocated && block.size >= requestSize) {
                int waste = block.size - requestSize;  // Calculate wasted space
                if (waste > maxWaste) {
                    maxWaste = waste;
                    worstIndex = i;  // Track the block with the most remaining space
                }
            }
        }

        if (worstIndex != -1) {
            memoryBlocks.get(worstIndex).isAllocated = true;  // Mark the worst block as allocated
            pageTable.mapPageToFrame(virtualPage, worstIndex);  // Map the virtual page to the worst-fitting block
            System.out.println("Worst-Fit: Allocated " + requestSize + " to block of size " + memoryBlocks.get(worstIndex).size);
            return true;
        }
        // If no suitable block is found
        System.out.println("Worst-Fit: No suitable block found for " + requestSize);
        return false;
    }
 
    /*
     * Next-Fit Allocation: Allocate the next available block that fits, starting from where the last allocation was made
     * @Param requestSize - integer representing the amount of memory that a program (or virtual page) wants to allocate from the available physical memory blocks
     * @Param virtualPage - integer representing the specific virtual page that we are working on
     * @Return - returns a boolean representing a match for this allocation type
     */
    public boolean nextFit(int requestSize, int virtualPage) {
        // Start from where the last allocation was made
        for (int i = lastAllocatedIndex; i < memoryBlocks.size(); i++) {
            MemoryBlock block = memoryBlocks.get(i);
            if (!block.isAllocated && block.size >= requestSize) {
                block.isAllocated = true;  // Mark the block as allocated
                lastAllocatedIndex = i;  // Update the last allocation index to current block
                pageTable.mapPageToFrame(virtualPage, i);  // Map the virtual page to this physical frame
                System.out.println("Next-Fit: Allocated " + requestSize + " to block of size " + block.size);
                return true;
            }
        }

        // If no suitable block is found in the remaining blocks, wrap around to the beginning
        for (int i = 0; i < lastAllocatedIndex; i++) {
            MemoryBlock block = memoryBlocks.get(i);
            if (!block.isAllocated && block.size >= requestSize) {
                block.isAllocated = true;  // Mark the block as allocated
                lastAllocatedIndex = i;  // Update the last allocation index to current block
                pageTable.mapPageToFrame(virtualPage, i);  // Map the virtual page to this physical frame
                System.out.println("Next-Fit: Allocated " + requestSize + " to block of size " + block.size);
                return true;
            }
        }

        // If no suitable block is found
        System.out.println("Next-Fit: No suitable block found for " + requestSize);
        return false;
    }


    /*
     * Display the current memory block statuses (allocated or free)
     * @Return - none
     */
    public void displayMemory() {
        for (MemoryBlock block : memoryBlocks) {
            String status = block.isAllocated ? "Allocated" : "Free";
            System.out.println("Block of size " + block.size + ": " + status);
        }
    }


    /*
     * Display the current page table mappings
     * @Return - none
     */
    public void displayPageTable() {
        pageTable.displayPageTable();
    }
}

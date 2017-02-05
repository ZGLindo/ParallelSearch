// Zaki G Lindo
// CS 450 - High Performance Computing
// Assignment: MPI Unit II - Parellel Search
// 2/4/2017

import java.util.*;


public class ParallelSearch {
	private static final int SECTIONS = 5;
	
	public static void main (String [] args) {
		Random rand = new Random();
		int [] array = {3,6,9,12,15,18,21,24,27,30, 33, 36};// Array to be searched
		int target = 21;									// Target Value
		System.out.println(process(array, target));			// Master Process
	}

	// Precondition:	Large integer array w/ a lot of values(relatively) and a target
	// Parameters: 		int [] array - Array to be searched
	//					int target - Value to be searched for
	// Returns: 		Location of target in the array
	public static int process(int [] array, int target) {
		int remainder = array.length % SECTIONS;
		int index = split(array, target, remainder);				// Index of array target is in
		return index;
	}

	// Parameters:		int [] array - Array to be split up
	// 					int target - target to be sent to sub processes
	//					int padding - remainder of array.length/SECTIONS
	// Returns: 		int - index of target value in reference to original array
	public static int split(int [] array, int target, int padding) {
		int index = -1;
		int interval = array.length/SECTIONS;
		int [] subArray = new int [interval];
		if (padding > 0) {
			int [] smallSubArray = new int [padding];
			for (int i = 0; i < padding; i++) {				// run a subProcess on a subArray
				smallSubArray[i] = array[i];				// of length padding
				index = subProcess(smallSubArray, target, 0);
			}
		}
		if (index != -1)									// If index is valid
			return index;
		for (int i = padding; i < array.length; i+=interval) {	// 
			for (int j = 0; j < interval; j++) {			// Create subArray
				subArray[j] = array[i + j];
			}
			index = subProcess(subArray, target, i);		// Run subProcess on subArray
			if (index != -1)								// If index is valid
				return index;
		}
		return index;
	}

	// Parameters:		int [] subArray - Section of original array
	// 					int target - value that subProcess is searching for
	//					int offset - value used to calculate index of original array
	//					that the subArray is examining
	// Returns:			int - index of the original array, calculated by examining index
	//					from subArray + offset
	public static int subProcess(int [] subArray, int target, int offset) {
		int index = -1;
		for (int i = 0; i < subArray.length; i++) 			// Traverse Array  
			if (subArray[i] == target) 						// Check for target value
				index = i + offset;
		return index;
	}
}
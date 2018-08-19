package com.goldman;


import java.util.HashSet;

public class OnlyChoice {
	
	static void onlyChoice() {
		boolean change = true;
		
		while(change) {
			change = false;
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					
					HashSet<Integer> pointer = Solver.cellDomain.get((row*9)+col);
					Integer[] domain =pointer.toArray(new Integer[pointer.size()]);
					
					if(pointer.size() == 1) {continue;}
				    
				    for(Integer element:domain) {
						
						//continue if cell is empty or cell doesn't satisfy the "only choice" rule
						if(!onlyChoiceHelper(row, col, element)) {
							continue;}
						else {
							change = true;
							pointer.clear();
							pointer.add(element);
							System.out.println("only choice solve");
							//Eliminator.eliminate(row, col ,element);
						}	
					}	
				}
			}
		}

		return;
	}

	private final static boolean onlyChoiceHelper(int row, int col, int value) {
		return (aloneInRow(row, col, value) && aloneInCol(row, col, value)
				&& aloneInBox(row, col, value));
	}

	private final static boolean aloneInBox(int row, int col, int value) {
		int r = row - row % 3;
		int c = col - col % 3;
		for (int i = r; i < r + 3; i++) {
			for (int j = c; j < c + 3; j++) {
				
				if(i != row && j!= col) {
					HashSet<Integer> pointer = Solver.cellDomain.get((i*9) + j);
					if(pointer.contains(value)) {
						return false;
					}
				}
		
			}
		}
		return true;
	}

	private final static boolean aloneInCol(int row, int col, int value) {
		for (int i = 0; i < 9; i++) {
			if(i != row) {
				HashSet<Integer> pointer = Solver.cellDomain.get((i*9) + col);
				if(pointer.contains(value)) {
					return false;
				}
			}
		}
		return true;
	}

	private final static boolean aloneInRow(int row, int col, int value) {
		for (int i = 0; i < 9; i++) {
			if(i != col) {
				HashSet<Integer> pointer = Solver.cellDomain.get((row*9) + i);
				if(pointer.contains(value)) {
					return false;
				}
			}
		}
		return true;
	}
}

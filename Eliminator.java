package com.goldman;

import java.util.HashSet;

public final class Eliminator {

	public static void sweep() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				HashSet<Integer> pointer = Solver.cellDomain.get((row*9)+col);
				if( pointer.size() == 1 ) {
					Eliminator.eliminate(row, col, pointer.iterator().next() );
				}
			}
		}
		return;
	}
	
	public static void eliminate(int row, int col, int value) {
		peerRow(row, col, value);
		peerCol(row, col, value);
		peerBox(row, col, value);
		
		return;
	}

	private final static void peerBox(int row, int col, int value) {
		
		int r = row - row % 3;
		int c = col - col % 3;
		for (int i = r; i < r + 3; i++) {
			for (int j = c; j < c + 3; j++) {
				HashSet<Integer> pointer = Solver.cellDomain.get((i*9) + j);
				
				if(pointer.size() > 1) {
					pointer.remove(value);
					if(pointer.size() == 1) {
						eliminate(i, j, pointer.iterator().next());
					}
				}
			}
		}
		return ;
	}

	private final static void peerCol(int row, int col, int value) {
		
		for (int i = 0; i < 9; i++) {
			HashSet<Integer> pointer = Solver.cellDomain.get((i*9) + col);
			
			if(pointer.size() > 1) {
				pointer.remove(value);
				if(pointer.size() == 1) {
					eliminate(i, col, pointer.iterator().next());
				}
			}
		}
		return ;
	}

	private final static void peerRow(int row, int col, int value) {
		
		for (int i = 0; i < 9; i++) {
			HashSet<Integer> pointer = Solver.cellDomain.get((row*9) + i);
			
			if(pointer.size() > 1) {
				pointer.remove(value);
				if(pointer.size() == 1) {
					eliminate(row, i, pointer.iterator().next());
				}
			}
		}
		return;
	}
}

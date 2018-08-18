package com.goldman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public final class Backtrack {
	
	public final static boolean solve(int[][] board) {
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				
				if (board[row][col] == 0) {
					
					// TODO loop through domain
					//for (int number = 1; number <= 9; number++) {
					//System.out.println("row: "+row+", col: "+col+"  : "+board[row][col]);
					
					HashSet<Integer> pointer = Solver.cellDomain.get((row*9) + col);
					Iterator<Integer> itr = pointer.iterator();
					while (itr.hasNext()) {
						
						
						int number = itr.next();
						
						//System.out.println("trying: "+number+" for row: "+row+", col: "+col);
						
						if (isValid(row, col, number, board)) {
							board[row][col] = number;
							if (solve(board)) {
								return true;
							} 
							else {
								board[row][col] = 0;
							}
						}
					}
						
					return false;
				}
			}
		}
		return true;
	}
	
	private final static boolean containsInRow(int row, int number, int[][] board) {
		for (int i = 0; i < 9; i++) {
			if (board[row][i] == number) {
				return true;
			}
		}
		return false;
	}

	private final static boolean containsInCol(int col, int number, int[][] board) {
		for (int i = 0; i < 9; i++) {
			if (board[i][col] == number) {
				return true;
			}
		}
		return false;
	}

	private final static boolean containsInBox(int row, int col, int number, int[][] board) {
		int r = row - row % 3;
		int c = col - col % 3;
		for (int i = r; i < r + 3; i++) {
			for (int j = c; j < c + 3; j++) {
				if (board[i][j] == number) {
					return true;
				}
			}

		}
		return false;
	}

	private final static boolean isValid(int row, int col, int number, int[][] board) {
		return !(containsInRow(row, number, board) || containsInCol(col, number, board)
				|| containsInBox(row, col, number, board));
	}
}

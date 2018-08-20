package com.goldman;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public final class Backtrack {
	
	public final static boolean solve(int[][] board) {
		board = Solver.board;
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				
				if (board[row][col] == 0) {
					HashSet<Integer> pointer = Solver.cellDomain.get((row*9) + col);
					Iterator<Integer> itr = pointer.iterator();
					while (itr.hasNext()) {
						int number = itr.next();
						
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
	
	public final static void validBoard(int[][] board) throws IOException {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if(board[row][col] == 0) {continue;}
				if(!isValid(row, col,board[row][col], board)) {
					throw new IOException("(Invalid puzzle)");
				}
			}
		}
	}
	
	private final static boolean containsInRow(int row, int col, int number, int[][] board) {
		for (int i = 0; i < 9; i++) {
			if(i == col) {continue;}
			if (board[row][i] == number) {
				return true;
			}
		}
		return false;
	}

	private final static boolean containsInCol(int row, int col, int number, int[][] board) {
		for (int i = 0; i < 9; i++) {
			if(i == row) {continue;}
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
				if(i == row  && j == col) {continue;}
				if (board[i][j] == number) {
					return true;
				}
			}

		}
		return false;
	}

	private final static boolean isValid(int row, int col, int number, int[][] board) {
		return !(containsInRow(row, col, number, board) || containsInCol(row, col, number, board)
				|| containsInBox(row, col, number, board));
	}
}

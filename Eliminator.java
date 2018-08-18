package com.goldman;

import java.util.HashSet;

public final class Eliminator {
	
	public static int[][] eliminate(int row, int col, int[][] board) {
		board = peerRow(row, col, board);
		board = peerCol(row, col, board);
		board = peerBox(row, col, board);
		
		return board;
	}

	private final static int[][] peerBox(int row, int col, int[][] board) {
		int value = board[row][col];
		
		int r = row - row % 3;
		int c = col - col % 3;
		for (int i = r; i < r + 3; i++) {
			for (int j = c; j < c + 3; j++) {
				HashSet<Integer> pointer = Solver.cellDomain.get((i*9) + j);
				
				if(pointer.size() > 1) {
					pointer.remove(value);
					if(pointer.size() == 1) {
						Solver.solvedCells.add((i*9) + j);
					}
				}
			}
		}
		return board;
	}

	private final static int[][] peerCol(int row, int col, int[][] board) {
		int value = board[row][col];
		
		for (int i = 0; i < 9; i++) {
			HashSet<Integer> pointer = Solver.cellDomain.get((i*9) + col);
			
			if(pointer.size() > 1) {
				pointer.remove(value);
				if(pointer.size() == 1) {
					Solver.solvedCells.add((i*9) + col);
					//Iterator<Integer> newValue = pointer.iterator();
					//board[i][col] = newValue.next(); 
				}
			}
		}
		return board;
	}

	private final static int[][] peerRow(int row, int col, int[][] board) {
		//System.out.println(row+" "+col);
		int value = board[row][col];
		
		for (int i = 0; i < 9; i++) {
			HashSet<Integer> pointer = Solver.cellDomain.get((row*9) + i);
			
			if(pointer.size() > 1) {
				pointer.remove(value);
				if(pointer.size() == 1) {
					Solver.solvedCells.add((row*9) + i);
				}
			}
		}
		return board;
	}
}

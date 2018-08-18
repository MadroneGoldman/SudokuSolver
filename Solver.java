package com.goldman;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;



public final class Solver {

	public static ArrayList<HashSet<Integer>> cellDomain = new ArrayList<HashSet<Integer>>();
	public static LinkedList<Integer> solvedCells = new LinkedList<Integer>(); 
    
	
	
	public static ArrayList<HashSet<Integer>> getCellDomain() {
		return cellDomain;
	}

	public static void setCellDomain(ArrayList<HashSet<Integer>> cellDomain) {
		Solver.cellDomain = cellDomain;
	}

	public static final void main(String[] args) {

		try {
			int[][] board;

			File boardFile = new File("D:\\Downloads\\Sudoku Challenge\\puzzle3.txt");
			//boardFile = GetFile();

			board = Parser.readBoard(boardFile);
			//printDomain(cellDomain);
			//printBoard(board);

			board = constraintPropagation(board);
			
			final long timeBefore = System.nanoTime();
			
			//printDomain(cellDomain);
			
			Backtrack.solve(board);

			final long timeAfter = System.nanoTime();

			System.out.println("solved in " + ((timeAfter - timeBefore)/1000000) + " miliseconds");

			printBoard(board);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void printDomain(ArrayList<HashSet<Integer>> cellDomain) {
		int count = 0;
		for(HashSet<Integer> set:cellDomain) {
			count+=set.size();
			/*Integer[] arry =set.toArray(new Integer[set.size()]);
			
			for(Integer i:arry) {
				System.out.print(i);
			}
			System.out.println(" ");*/
			
		}
		System.out.println("count: "+count);
	}

	public final static int[][] constraintPropagation(int[][] board){
		
		//elimination rule
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				
				//if(board[row][col] == 0) {continue;}
				if( cellDomain.get((row*9)+col).size() > 1 ) {continue;}
				board = Eliminator.eliminate(row, col, board);
				
				//for any newly solved cells constrain the domains of the cell's peers
				Iterator<Integer> itr = solvedCells.iterator();
				while (itr.hasNext()) {
				    Integer cell = itr.next();
				    Eliminator.eliminate(cell/9, cell%9 ,board);
				    itr.remove();
				}	
			}
		}
		
		printDomain(cellDomain);
		//only choice rule
		OnlyChoice.onlyChoice(board);
		

		
		return board;
	}
	
	private final static void printBoard(final int[][] board) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(board[i][j]);
			}

			System.out.println();
		}
	}

	private static File GetFile() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "text");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			
		}

		return chooser.getSelectedFile();
	}

}
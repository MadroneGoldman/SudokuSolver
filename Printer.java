package com.goldman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Printer {
	
	public Printer() {
		super();
	}

	final void printBoard(final int[][] board) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(board[i][j]);
			}

			System.out.println();
		}
	}
	
	final void outputBoard(final int[][] board, String name) {
	
		String fileName = name.substring(0, name.length()-4) +".sol.txt";
		
		try {
	        File file = new File(System.getProperty("user.dir")+"\\Output\\"+fileName);
	        BufferedWriter output = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					output.write(Integer.toString(board[i][j]));
				}
				output.newLine();
			}
			output.close();
			
		} catch (Exception e) {
			System.out.println("ERROR WRITING SOLUTION FILE "+e.getMessage() );
		}	
	}

}

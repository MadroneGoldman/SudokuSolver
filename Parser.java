package com.goldman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public final class Parser {
	
	public static ArrayList<HashSet<Integer>> cellDomain = null;
	private static int filledCells = 0;
	
	
	public Parser(ArrayList<HashSet<Integer>> cellDomain) {
		super();
		Parser.cellDomain = cellDomain;
	}

	final static int[][] readBoard(final File file) throws IOException {
		int[][] board = new int[9][9];
		BufferedReader input = new BufferedReader(new FileReader(file));

		try {
			String line = null;
			int lineIndex = 0;

			while ((line = input.readLine()) != null) {
				if (line.length() != 9 || lineIndex > 8) {
					throw new IOException();
				}

				for (int letterIndex = 0; letterIndex < 9; letterIndex++) {

					int value;

					if (line.charAt(letterIndex) == 'X') {
						value = 0;

						// before constraint propagation the domain of an empty cell is 1-9
						HashSet<Integer> set = new HashSet<Integer>();
						for (int i = 1; i <= 9; i++) {
							set.add(i);
						}
						Solver.cellDomain.add(set);

					} 
					else {
						value = Character.getNumericValue(line.charAt(letterIndex));
						// the domain of an empty cell is it's value
						HashSet<Integer> set = new HashSet<Integer>();
						set.add(value);
						Solver.cellDomain.add(set);
						filledCells++;
					}

					if (value < 0 || value > 9) {
						throw new IOException();
					}

					board[lineIndex][letterIndex] = value;
				}

				lineIndex++;
			}

			if (lineIndex != 9) {
				throw new IOException();
			}
		} finally {
			input.close();
		}
		
		//Solver.setCellDomain(cellDomain);
		return board;
	}
	
}

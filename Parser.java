package com.goldman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public final class Parser {
	
	private Integer filledCells;
	
	public Parser() {
		super();
		this.filledCells = 0;
	}

	final int[][] readBoard(final File file) throws IOException, NotEnoughCluesException {
		int[][] board = new int[9][9];
		
		BufferedReader input = new BufferedReader(new FileReader(file));

		try {
			String line = null;
			int lineIndex = 0;

			while ((line = input.readLine()) != null) {
				if (line.length() != 9 || lineIndex > 8) {
					throw new IOException("(invalid puzzle format or size)");
				}

				for (int letterIndex = 0; letterIndex < 9; letterIndex++) {

					int value;
					
					char cell = line.charAt(letterIndex);
					if (cell == 'X') {
						value = 0;

						// before constraint propagation the domain of an empty cell is 1-9
						HashSet<Integer> set = new HashSet<Integer>();
						for (int i = 1; i <= 9; i++) {
							set.add(i);
						}
						Solver.cellDomain.add(set);

					} 
					else {
						value = Character.getNumericValue(cell);
						// the domain of an empty cell is it's value
						HashSet<Integer> set = new HashSet<Integer>();
						set.add(value);
						Solver.cellDomain.add(set);
						this.filledCells++;
					}

					if (value < 0 || value > 9) {
						throw new IOException("(invalid cell entry: "+cell+")");
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
		if(filledCells < 17) {
			throw new NotEnoughCluesException("(board has only "+filledCells.toString()+" clues)");
		}
		
		return board;
	}
	
}

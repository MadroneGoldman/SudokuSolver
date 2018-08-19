package com.goldman;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class UnitTests {

		@Test
		public void noFileGiven() {
			try {
				Solver.solve("");
			} catch (IOException e) {
				assertEquals("(invalid file type. Please use .txt)",e.getMessage());
			}
		}
		
		@Test
		public void wrongFileType() {
			try {
				Solver.solve("wsefsf");
			} catch (IOException e) {
				assertEquals("(invalid file type. Please use .txt)",e.getMessage());
			}
		}
		
		@Test
		public void fileDoesNotExist() {
			try {
				Solver.solve("wsefsf.txt");
			} catch (IOException e) {
				assertEquals("wsefsf.txt (The system cannot find the file specified)",e.getMessage());
			}
		}
		
		@Test
		public void wrongBoardSize() {
			try {
				File boardFile = new File(System.getProperty("user.dir")+"\\TestPuzzles\\oneCellBoard.txt");
				Parser parser = new Parser();
				parser.readBoard(boardFile);
			} catch (IOException e) {
				assertEquals("(invalid puzzle format or size)",e.getMessage());
			} catch (NotEnoughCluesException e) {
			}
		}
		
		@Test
		public void wrongBoardFormat() {
			try {
				File boardFile = new File(System.getProperty("user.dir")+"\\TestPuzzles\\boardWithZs.txt");
				Parser parser = new Parser();
				parser.readBoard(boardFile);
			} catch (IOException e) {
				assertEquals("(invalid cell entry: Z)",e.getMessage());
			} catch (NotEnoughCluesException e) {
			}
		}
		
		
		@Test
		public void notEnoughClues() {
			try {
				File boardFile = new File(System.getProperty("user.dir")+"\\TestPuzzles\\16clueBoard.txt");
				Parser parser = new Parser();
				parser.readBoard(boardFile);
			} catch (IOException e) {

			} catch (NotEnoughCluesException e) {
				assertEquals("(board has only 16 clues)",e.getMessage());
			}
		}
		
		
		@Test
		public void veryHardSudoku() {
			try {
				int[][] board;
				File boardFile = new File(System.getProperty("user.dir")+"\\TestPuzzles\\veryHardSudoku.txt");
				Printer printer = new Printer();
				Parser parser = new Parser();
				
				board = parser.readBoard(boardFile);
				Backtrack.validBoard(board);
				
				System.out.println("\nUnsolved puzzle:");
				printer.printBoard(board);
				
				final long timeBefore = System.nanoTime();
				Eliminator.sweep();
				OnlyChoice.onlyChoice();
				Backtrack.solve(board);
				final long timeAfter = System.nanoTime();
				
				System.out.println("\nSolved puzzle:");
				printer.printBoard(board);
				System.out.println("solved in " + ((timeAfter - timeBefore)/1000000) + " miliseconds");
				
				printer.outputBoard(board, boardFile.getName());
			} catch (IOException | NotEnoughCluesException e) {
				fail("exception caught");
			}
		}
		
		@Test
		public void easySudoku() {
			try {
				int[][] board;
				File boardFile = new File(System.getProperty("user.dir")+"\\TestPuzzles\\easySudoku.txt");
				Printer printer = new Printer();
				Parser parser = new Parser();
				
				board = parser.readBoard(boardFile);
				Backtrack.validBoard(board);
				
				System.out.println("\nUnsolved puzzle:");
				printer.printBoard(board);
				
				final long timeBefore = System.nanoTime();
				Eliminator.sweep();
				OnlyChoice.onlyChoice();
				Backtrack.solve(board);
				final long timeAfter = System.nanoTime();
				
				System.out.println("\nSolved puzzle:");
				printer.printBoard(board);
				System.out.println("solved in " + ((timeAfter - timeBefore)/1000000) + " miliseconds");
				
				printer.outputBoard(board, boardFile.getName());
			} catch (IOException | NotEnoughCluesException e) {
				fail("exception caught");
			}
		}

	}
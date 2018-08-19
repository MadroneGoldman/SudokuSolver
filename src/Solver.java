package com.goldman;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;



public final class Solver {

	public static ArrayList<HashSet<Integer>> cellDomain = new ArrayList<HashSet<Integer>>();	

	public static final void main(String[] args) {
		
		System.out.print("\r\n" + 
				"  _____ __ __  ___     ___   __  _  __ __       _____  ___   _      __ __    ___  ____  \r\n" + 
				" / ___/|  |  ||   \\   /   \\ |  |/ ]|  |  |     / ___/ /   \\ | |    |  |  |  /  _]|    \\ \r\n" + 
				"(   \\_ |  |  ||    \\ |     ||  ' / |  |  |    (   \\_ |     || |    |  |  | /  [_ |  D  )\r\n" + 
				" \\__  ||  |  ||  D  ||  O  ||    \\ |  |  |     \\__  ||  O  || |___ |  |  ||    _]|    / \r\n" + 
				" /  \\ ||  :  ||     ||     ||     \\|  :  |     /  \\ ||     ||     ||  :  ||   [_ |    \\ \r\n" + 
				" \\    ||     ||     ||     ||  .  ||     |     \\    ||     ||     | \\   / |     ||  .  \\\r\n" + 
				"  \\___| \\__,_||_____| \\___/ |__|\\_| \\__,_|      \\___| \\___/ |_____|  \\_/  |_____||__|\\_|\r\n" + 
				"                                                                                        \r\n" + 
				"");
			
		boolean run = true;
		while(run) {
			
			cellDomain.clear();
			System.out.println("Type 1 to solve all 5 challenge puzzles");
			System.out.println("Type 2 to solve a specific puzzle");
			System.out.println("Type 3 to exit");
			System.out.println("Enter choice here: ");
			
			String option = null;
			Scanner input = new Scanner(System.in);
			while(true) {
				try{
					option = input.nextLine();
					if(!option.equals("1") && !option.equals("2") && !option.equals("3")) {
						throw new InputMismatchException("not an option");
					}
					break;
				}
			    catch (InputMismatchException e) {
						System.out.println("Incorrect input. Please enter a valid option: ");
						continue;
				}	
			}
			
			int s = Integer.parseInt(option);
			
			switch(s) {
				/* ################  solve all puzzle case  ################*/
				case 1:
					System.out.println("solved all");
					String[] puzzles = new String[5];
					puzzles[0] = System.getProperty("user.dir")+"\\puzzles\\puzzle1.txt";
					puzzles[1] = System.getProperty("user.dir")+"\\puzzles\\puzzle2.txt";
					puzzles[2] = System.getProperty("user.dir")+"\\puzzles\\puzzle3.txt";
					puzzles[3] = System.getProperty("user.dir")+"\\puzzles\\puzzle4.txt";
					puzzles[4] = System.getProperty("user.dir")+"\\puzzles\\puzzle5.txt";
					
					try {
						for(String fileName:puzzles) {
							solve(fileName);
						}	
					}catch (InputMismatchException e) {
						System.out.println("ERROR incorrect input");
					} catch (IOException e) {
						System.out.println("ERROR "+e.getMessage());
					}
					finally {
						System.out.println("\nPress enter to restart");
						input.nextLine();
					}
					continue;
					
					/* ################  solve specific puzzle case  ################*/
				case 2:
					try {
						System.out.println("Enter filepath of the Sudoku puzzle: ");
				        String fileName = input.nextLine();
				        solve(fileName);

					} catch (InputMismatchException e) {
						System.out.println("ERROR incorrect input");
					} catch (IOException e) {
						System.out.println("ERROR "+e.getMessage());
					}
					finally {
						System.out.println("\nPress enter to restart");
						input.nextLine();
					}
					
					continue;
					
				/* ################  exit case  ################*/
				case 3:
					input.close();
					run = false;
					break;
			}
			break;
		}
	
	}
	
	
	public static void solve(String fileName) throws IOException, StringIndexOutOfBoundsException {
		
		
		if(fileName.length()<4 || !fileName.substring(fileName.length()-4).equals(".txt")) {
			throw new IOException("(invalid file type. Please use .txt)");
		}
		
		try {			
			int[][] board;
			File boardFile = new File(fileName);
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
			System.out.println("ERROR "+e.getMessage());
		}
	}

}
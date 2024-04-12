package com.example.mazesolver.controller;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * auther Andro Rizk
 */
public class MazeSolver {
	Maze dogMaze;
	private boolean[][] visited;

	/**
	 * This method sets up the maze using the given input argument
	 * 
	 * @param maze is a maze that is used to construct the dogMaze
	 */
	public void setup(String[][] maze) {
		/*
		 * insert your code here to create the dogMaze
		 * using the input argument.
		 */

		dogMaze = new Maze(maze);
		this.visited = new boolean[maze.length][maze[0].length];
		for (boolean[] row : visited) {
			Arrays.fill(row, false);
		}
	}

	/**
	 * This method returns true if the number of
	 * gates in dogMaze >= 2.
	 * * treating it like a rectangle * *
	 * First --> top 0111
	 * second --> left 1011
	 * third --> bottom 1101
	 * fourth --> right 1110
	 * 
	 * @return it returns true, if enough gate exists (at least 2), otherwise false.
	 *         Note The first, second, third and fourth digits shows the state of
	 *         the top, left, bottom and right
	 * 
	 */
	public boolean enoughGate() {
		// insert your code here. Change the return value to fit your purpose.
		int length = 0; // represent the length of the dog Maze
		int width = 0; // represent the width of the dog Maze
		for (int i = 0; i < this.dogMaze.getReference().length; i++) {
			length = length + 1; // getting the length of the rectangular array by counting
		}
		// getting the width of the rectangular array using only the first array in the
		// 2D array because it is assumed that it is either a rectangular or a square 2D
		// array
		for (int i = 0; i < this.dogMaze.getReference()[0].length; i++) {
			width = width + 1;

		}
		return enoughGateHelper('T', 0, 0, length, width);
	}

	/**
	 * This method is a helper for the enoughGate() method
	 * 
	 * @param c      a char side such that:
	 *               'T' --> Top
	 *               'B'--> Bottom
	 *               'L' --> Left
	 *               'R' --> Right
	 * @param pos    which represent the position of the block
	 * @param gates  which represents the number of gates
	 * @param length represents the length of the dog Maze
	 * @param width  represents the width of the dog Maze
	 * @return a boolean if the dogGate have >= 2.
	 * 
	 */
	public boolean enoughGateHelper(char c, int pos, int gates, int length, int width) {
		if (gates >= 2) // Base case, if the dogGate have more than
			return true;

		// Applying the necessary rotation such that it go though all the sides of the
		// rectangular array
		// a
		// ^----------->
		// | |
		// | |
		// d | | b
		// | |
		// <-----------V
		// c

		if (c == 'T' && pos == width)
			return enoughGateHelper('R', 0, gates, length, width);

		if (c == 'R' && pos == length)
			return enoughGateHelper('B', 0, gates, length, width);

		if (c == 'B' && pos == width)
			return enoughGateHelper('L', 0, gates, length, width);
		if (c == 'L' && pos == length)
			return false;

		// recursive cases

		if (c == 'T') {
			if (dogMaze.getMaze()[0][pos].charAt(0) == '0') {
				gates++;
			}
			return enoughGateHelper('T', pos + 1, gates, length, width); // checking side a
		} else if (c == 'B') {
			if (dogMaze.getMaze()[length - 1][pos].charAt(2) == '0') {
				gates++;

			}
			return enoughGateHelper('B', pos + 1, gates, length, width); // checking side c
		} else if (c == 'L') {
			if (dogMaze.getMaze()[pos][0].charAt(1) == '0') {
				gates++;
			}
			return enoughGateHelper('L', pos + 1, gates, length, width); // checking side d
		} else {
			if (dogMaze.getMaze()[pos][width - 1].charAt(3) == '0') {
				gates++;
			}
			return enoughGateHelper('R', pos + 1, gates, length, width); // checking side b
		}

	}

	/**
	 * This method finds a path from the entrance gate to
	 * the exit gate.
	 * 
	 * @param row    is the index of the row, where the entrance is.
	 * @param column is the index of the column, where the entrance is.
	 * @return it returns a string that contains the path from the start to the end.
	 *         The return value should have a pattern like this (i,j)(k,l),...
	 *         The first pair of the output must show the entrance given as the
	 *         input parameter (i.e. (row,column)
	 *         No whitespace is allowed in the output.
	 */
	public String findPath(int row, int column) {
		if (ifGate(row, column)) { // If starting point is a gate, initiate from here.
			return findPathHelper(row, column, "", true);
		}
		return "No path found or entrance not on gate";
	}

	// Utility to close a specific wall in the maze cell's string representation
	private String closeWall(int row, int column, int wallIndex) {
		char[] cell = dogMaze.getReference()[row][column].toCharArray();
		if (wallIndex >= 0)
			cell[wallIndex] = '1';
		return new String(cell);
	}

	private int getWallIndex(int row, int column, int length, int width) {
		if (row == 0)
			return 0;
		if (row == length - 1)
			return 2;
		if (column == 0)
			return 1;
		if (column == width - 1)
			return 3;
		return -1;
	}

	/**
	 * this method is a helper for the findPath. it checks if the block is on the
	 * side of the maze
	 * and actually a gate
	 * input the location of the block (row and column)
	 * 
	 * @param row    represents which row the block is located
	 * @param column represents which column the block is located
	 * @return boolean if the block on a side of the sides of the 2D rectangular and
	 *         it is a Gate returns true
	 */
	public boolean ifGate(int row, int column) {
		int length = dogMaze.getReference().length;
		int width = dogMaze.getReference()[0].length;

		String cell = dogMaze.getReference()[row][column];
		if (row == 0 && cell.charAt(0) == '0')
			return true;
		if (row == length - 1 && cell.charAt(2) == '0')
			return true;
		if (column == 0 && cell.charAt(1) == '0')
			return true;
		if (column == width - 1 && cell.charAt(3) == '0')
			return true;
		return false;
	}

	/**
	 * this method is a helper for the method findPath. it takes in the full Path of
	 * the dog and returns the shortest path
	 * the logic behind this is:
	 * Case Zero: if the substring is empty just add the entrance of the maze
	 * Case One: if the dog passed though a block only once and it have only one
	 * opening then it has to be in the shortest path
	 * Case Two: If it is a gate (the exit) and not in the shortest Path
	 * already(just avoiding redundancy) then it has to be in the shortest path
	 * Case Three: if the dog passed through a block more than one opening, and it
	 * is not already in the string and ( it is row is more than or less the
	 * previous
	 * blocks row, and column is the same or the other way around) such lets say
	 * (1,2) the first one the then one after it has to be either
	 * (2,2) or (0,2) or (1,1) or (1,3) which either moves up or down or left or
	 * right then it has to be in the shortest path
	 * 
	 * @param Path all the passes the dog passed in the maze
	 * @return the shortest path to the exit
	 */
	public String shortestPath(String Path) {
		String shortestPath = ""; // the string that we will sort into the shortest Path
		String subString = ""; // the string that will store the current substring
		String previousSubString = ""; // the string that will store the previous substring
		for (int i = 0; i < Path.length(); i = i + 5) {
			subString = Path.substring(i, i + 5);

			int count = (Path.split(subString).length) - 1;
			if (shortestPath == "") {
				shortestPath = shortestPath + subString;
				previousSubString = subString;
			} else if (count < 2 && !checkPath(Integer.parseInt(subString.substring(1, 2)),
					Integer.parseInt(subString.substring(3, 4)))) {
				shortestPath = shortestPath + subString;
				previousSubString = subString; // saving the previous added substring
			} else if (ifGate(Integer.parseInt(subString.substring(1, 2)), Integer.parseInt(subString.substring(3, 4)))
					&& !shortestPath.contains(subString)) {
				shortestPath = shortestPath + subString;
				previousSubString = subString; // saving the previous added substring
			} else if (count >= 1 && checkPath(Integer.parseInt(subString.substring(1, 2)),
					Integer.parseInt(subString.substring(3, 4))) && !shortestPath.contains(subString) &&
			// making sure that the added substring in the right place, avoiding adding
			// block that been went twice and not in the fastest path ( either moves up or
			// down or left or right)
					((Integer.parseInt(previousSubString.substring(1, 2)) == Integer.parseInt(subString.substring(1, 2))
							- 1
							&& Integer.parseInt(previousSubString.substring(3, 4)) == Integer
									.parseInt(subString.substring(3, 4)))
							|| ((Integer
									.parseInt(previousSubString.substring(1,
											2)) == Integer.parseInt(subString.substring(1, 2)) + 1
									&& Integer.parseInt(previousSubString.substring(3, 4)) == Integer
											.parseInt(subString.substring(3, 4))))
							|| ((Integer
									.parseInt(previousSubString.substring(3,
											4)) == Integer.parseInt(subString.substring(3, 4)) - 1
									&& Integer.parseInt(previousSubString.substring(1, 2)) == Integer
											.parseInt(subString.substring(1, 2))))
							|| ((Integer
									.parseInt(previousSubString.substring(3,
											4)) == Integer.parseInt(subString.substring(3, 4)) + 1
									&& Integer.parseInt(previousSubString.substring(1, 2)) == Integer
											.parseInt(subString.substring(1, 2)))))) {
				shortestPath = shortestPath + subString;
				previousSubString = subString; // saving the previous added substring
			}

		}

		return shortestPath;
	}

	/**
	 * a helper method for the method finfPath
	 * 
	 * @param row    represents which row the block is located
	 * @param column represents which column the block is located
	 * @return a String in the shape of (__,__)
	 *         Note not Overriding toString
	 */
	public String toString(int row, int column) {

		return "(" + row + "," + column + ")";
	}

	/**
	 * this is a helper method that checks if a specific block have more than one
	 * opening or path
	 * this method checks if the block in the maze have more than one path
	 * 
	 * @param row    represents which row the block is located
	 * @param column represents which column the block is located
	 * @return true if the block have more than 2 '0's
	 */
	public boolean checkPath(int row, int column) {
		int numberOfZeros = 0;
		for (int i = 0; i < 4; i++) {
			if (dogMaze.getMaze()[row][column].charAt(i) == '0') {
				numberOfZeros++;
			}
		}
		if (numberOfZeros >= 3)
			return true;
		else
			return false;
	}

	/**
	 * 
	 * 
	 * @param c              represents the previous move, basically where is the
	 *                       previous move coming from
	 *                       'T' --> Top
	 *                       'B'--> Bottom
	 *                       'L' --> Left
	 *                       'R' --> Right
	 * @param row            represents the row of the current block
	 * @param column         represents the column of the current block
	 * @param entranceRow    represents which row the entrance located
	 * @param entranceColumn represents which Column the entrance located
	 * @param Path           that have all the movement of the dog tell he finds the
	 *                       exit
	 * @return the string of the right path to the exit including all the possible
	 *         tries
	 */
	private String findPathHelper(int row, int column, String path, boolean isStart) {
		if (row < 0 || row >= dogMaze.getRows() || column < 0 || column >= dogMaze.getCols() || visited[row][column]) {
			return null; // Return null if out of bounds or already visited.
		}

		path += toString(row, column); // Assuming toString formats coordinates correctly.
		visited[row][column] = true;

		// Check if it's a gate and not the starting point or if the starting point can
		// also be an exit.
		if (ifGate(row, column) && (!isStart || path.length() > toString(row, column).length())) {
			return path;
		}

		// No longer the start after the first call.
		isStart = false;

		String[] directions = new String[4];
		directions[0] = dogMaze.getReference()[row][column].charAt(0) == '0'
				? findPathHelper(row - 1, column, path, isStart)
				: null;
		directions[1] = dogMaze.getReference()[row][column].charAt(1) == '0'
				? findPathHelper(row, column - 1, path, isStart)
				: null;
		directions[2] = dogMaze.getReference()[row][column].charAt(2) == '0'
				? findPathHelper(row + 1, column, path, isStart)
				: null;
		directions[3] = dogMaze.getReference()[row][column].charAt(3) == '0'
				? findPathHelper(row, column + 1, path, isStart)
				: null;

		visited[row][column] = false; // Unmark as visited before backtracking.

		for (String dir : directions) {
			if (dir != null) {
				return dir; // Return the first found path.
			}
		}

		return null; // Return null if no paths lead to an exit.
	}

	private boolean isValid(int row, int col) {
		return row >= 0 && row < dogMaze.getReference().length &&
				col >= 0 && col < dogMaze.getReference()[0].length;
	}

}

/**
 * This class defines a <code> maze </code> using a 2D array.
 * To complete the code, you should not change the method
 * signatures (header).
 *
 */

class Maze {
	private String[][] maze;
	private int rows;
	private int columns;

	/**
	 * This constructor makes the maze.
	 * 
	 * @param maze is a 2D array that contains information
	 *             on how each cell of the array looks like.
	 */
	public Maze(String[][] maze) {
		/*
		 * complete the constructor so that the maze is
		 * a (deep copy) of the input parameter.
		 * we create a new 2D array and insert all the values into it
		 */
		int length = 0; // represent the length of the rectangular array
		int width = 0; // represent the width of the rectangular array

		for (int i = 0; i < maze.length; i++) {
			length = length + 1; // getting the length of the rectangular array by counting
		}
		// getting the width of the rectangular array using only the first array in the
		// 2D array because it is assumed that it is either a rectangular or a square 2D
		// array
		for (int i = 0; i < maze[0].length; i++) {
			width = width + 1;

		}

		this.maze = new String[length][width];
		this.rows = length;
		this.columns = width;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				this.maze[i][j] = maze[i][j];
			}
		}
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return columns;
	}

	/**
	 * This accessor(getter) method returns a 2D array that
	 * represents the maze
	 * 
	 * @return it returns a (clone)(DeepCopy) to the maze
	 */
	public String[][] getMaze() {
		int length = 0; // represent the length of the rectangular array
		int width = 0; // represent the width of the rectangular array

		for (int i = 0; i < maze.length; i++) {
			length = length + 1; // getting the length of the rectangular array by counting
		}
		// getting the width of the rectangular array using only the first array in the
		// 2D array because it is assumed that it is either a rectangular or a square 2D
		// array
		for (int i = 0; i < maze[0].length; i++) {
			width = width + 1;

		}

		String[][] CloneMaze = new String[length][width];

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				CloneMaze[i][j] = this.maze[i][j];
			}
		}
		return CloneMaze;
	}

	/**
	 * Another getter for the maze but returns a shadow Copy
	 * 
	 * @return a reference for the actual maze
	 */
	public String[][] getReference() {
		return this.maze;
	}

	/**
	 * toString() method will print out the 2D string such that it prints every
	 * String in the 2D array
	 * one after another with a space " " in between, and an array after an array
	 * with a "]/n[" in
	 * between.
	 * Note there is a "[" at the beginning and make an if-statement to delete the
	 * "[" at the end
	 * 
	 * @return the String
	 */

	@Override
	public String toString() {
		String strMaze = "[";
		int length = 0; // represent the length of the rectangular array
		int width = 0; // represent the width of the rectangular array

		for (int i = 0; i < maze.length; i++) {
			length = length + 1; // getting the length of the rectangular array by counting
		}
		// getting the width of the rectangular array using only the first array in the
		// 2D array because it is assumed that it is either a rectangular or a square 2D
		// array
		for (int i = 0; i < maze[0].length; i++) {
			width = width + 1;

		}

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				if (j < width - 1)
					strMaze = strMaze + this.maze[i][j] + " ";
				else {
					strMaze = strMaze + this.maze[i][j] + "]";
					if (i < length - 1)
						strMaze = strMaze + "\n[";
				}
			}

		}
		return strMaze;
	}

}// end of class Maze

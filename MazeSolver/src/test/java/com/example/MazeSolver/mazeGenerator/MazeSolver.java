package com.example.MazeSolver.mazeGenerator;

import java.util.ArrayList;

public class MazeSolver {
	Maze dogMaze;

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
	public String findPath(int row, int column) // returns --> shortest path
	{
		int length = 0; // represent the length of the dog Maze
		int width = 0; // represent the width of the dog Maze
		String Path = ""; // initializing String path that represents the Path to the exit
		for (int i = 0; i < this.dogMaze.getReference().length; i++) {
			length = length + 1; // getting the length of the rectangular array by counting
		}
		// getting the width of the rectangular array using only the first array in the
		// 2D array because it is assumed that it is either a rectangular or a square 2D
		// array
		for (int i = 0; i < this.dogMaze.getReference()[0].length; i++) {
			width = width + 1;

		}

		int entranceRow = row; // represents the row of the entrance
		int entranceColumn = column; // represents the column of the entrance

		// Checking the corners as they can have two walls open and can be treated as an
		// entrance and an open path. in this case we class both walls
		// calling the shortestPath method to get the shortest path to the exit

		if (row == 0 && column == 0) {
			dogMaze.getReference()[row][column] = dogMaze.getReference()[row][column].substring(0, 0) + "1"
					+ dogMaze.getReference()[row][column].substring(0 + 1); // closing the entrance so to mark it as a
																			// non possible solution
			dogMaze.getReference()[row][column] = dogMaze.getReference()[row][column].substring(0, 1) + "1"
					+ dogMaze.getReference()[row][column].substring(1 + 1); // closing the entrance so to mark it as a
																			// non possible solution
			return shortestPath(findPathHelper('L', row, column, entranceRow, entranceColumn, Path));
		} else if (row == 0 && column == width - 1) {
			dogMaze.getReference()[row][column] = dogMaze.getReference()[row][column].substring(0, 0) + "1"
					+ dogMaze.getReference()[row][column].substring(0 + 1); // closing the entrance so to mark it as a
																			// non possible solution
			dogMaze.getReference()[row][column] = dogMaze.getReference()[row][column].substring(0, 3) + "1"
					+ dogMaze.getReference()[row][column].substring(3 + 1); // closing the entrance so to mark it as a
																			// non possible solution
			return shortestPath(findPathHelper('L', row, column, entranceRow, entranceColumn, Path));
		} else if (row == length - 1 && column == 0) {
			dogMaze.getReference()[row][column] = dogMaze.getReference()[row][column].substring(0, 1) + "1"
					+ dogMaze.getReference()[row][column].substring(1 + 1); // closing the entrance so to mark it as a
																			// non possible solution
			dogMaze.getReference()[row][column] = dogMaze.getReference()[row][column].substring(0, 2) + "1"
					+ dogMaze.getReference()[row][column].substring(2 + 1); // closing the entrance so to mark it as a
																			// non possible solution
			return shortestPath(findPathHelper('L', row, column, entranceRow, entranceColumn, Path));
		}

		else if (row == length - 1 && column == width - 1) {
			dogMaze.getReference()[row][column] = dogMaze.getReference()[row][column].substring(0, 2) + "1"
					+ dogMaze.getReference()[row][column].substring(2 + 1); // closing the entrance so to mark it as a
																			// non possible solution
			dogMaze.getReference()[row][column] = dogMaze.getReference()[row][column].substring(0, 3) + "1"
					+ dogMaze.getReference()[row][column].substring(3 + 1); // closing the entrance so to mark it as a
																			// non possible solution
			return shortestPath(findPathHelper('L', row, column, entranceRow, entranceColumn, Path));
		}

		// deciding where is the entrance
		else if (column == 0) // if the entrance in the left --> 'L'
		{
			dogMaze.getReference()[row][column] = dogMaze.getReference()[row][column].substring(0, 1) + "1"
					+ dogMaze.getReference()[row][column].substring(1 + 1); // closing the entrance so to mark it as a
																			// non possible solution
			return shortestPath(findPathHelper('L', row, column, entranceRow, entranceColumn, Path));
		} else if (row == 0) // top --> 'T'
		{
			dogMaze.getReference()[row][column] = dogMaze.getReference()[row][column].substring(0, 0) + "1"
					+ dogMaze.getReference()[row][column].substring(0 + 1); // closing the entrance so to mark it as a
																			// non possible solution
			return shortestPath(findPathHelper('T', row, column, entranceRow, entranceColumn, Path));
		} else if (column == width - 1) // right --> 'R'
		{
			dogMaze.getReference()[row][column] = dogMaze.getReference()[row][column].substring(0, 3) + "1"
					+ dogMaze.getReference()[row][column].substring(3 + 1); // closing the entrance so to mark it as a
																			// non possible solution
			return shortestPath(findPathHelper('R', row, column, entranceRow, entranceColumn, Path));
		} else if (row == length - 1) // bottom -->'B'
		{
			dogMaze.getReference()[row][column] = dogMaze.getReference()[row][column].substring(0, 2) + "1"
					+ dogMaze.getReference()[row][column].substring(2 + 1); // closing the entrance so to mark it as a
																			// non possible solution
			return shortestPath(findPathHelper('B', row, column, entranceRow, entranceColumn, Path));
		}

		return ""; // not implemented yet
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
		if ((row == 0 && column == 0) && (dogMaze.getReference()[row][column].charAt(0) == '0'
				|| dogMaze.getReference()[row][column].charAt(1) == '0')) {
			return true;
		}
		if (row == 0 && dogMaze.getReference()[row][column].charAt(0) == '0') {
			return true;
		} else if (row == length - 1 && dogMaze.getReference()[row][column].charAt(2) == '0') {
			return true;
		} else if (column == 0 && dogMaze.getReference()[row][column].charAt(1) == '0') {
			return true;
		} else if (column == width - 1 && dogMaze.getReference()[row][column].charAt(3) == '0') {
			return true;
		} else
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
	public String findPathHelper(char c, int row, int column, int entranceRow, int entranceColumn, String Path) {

		if (ifGate(row, column) && (row != entranceRow || column != entranceColumn)) // base case // 0
		{
			return toString(row, column);

		}

		// recursive cases
		// Checking if i am coming from Top 'T', including all the possible cases

		if (c == 'T' && dogMaze.getReference()[row][column].charAt(3) == '0') {
			Path = Path + toString(row, column)
					+ findPathHelper('L', row, column + 1, entranceRow, entranceColumn, Path); // --> going right
			return Path;
		}
		if (c == 'T' && dogMaze.getReference()[row][column].charAt(2) == '0') {
			Path = Path + toString(row, column)
					+ findPathHelper('T', row + 1, column, entranceRow, entranceColumn, Path); // --> going to the
																								// bottom
			return Path;
		}
		if (c == 'T' && dogMaze.getReference()[row][column].charAt(1) == '0') {
			Path = Path + toString(row, column)
					+ findPathHelper('R', row, column - 1, entranceRow, entranceColumn, Path); // --> going to the left
			return Path;
		}

		// Checking if i am coming from Bottom 'B', including all the possible cases

		if (c == 'B' && dogMaze.getReference()[row][column].charAt(3) == '0') {
			Path = Path + toString(row, column)
					+ findPathHelper('L', row, column + 1, entranceRow, entranceColumn, Path); // --> going right
			return Path;
		}
		if (c == 'B' && dogMaze.getReference()[row][column].charAt(0) == '0') {
			Path = Path + toString(row, column)
					+ findPathHelper('B', row - 1, column, entranceRow, entranceColumn, Path); // --> going to the Top
			return Path;
		}
		if (c == 'B' && dogMaze.getReference()[row][column].charAt(1) == '0') {
			Path = Path + toString(row, column)
					+ findPathHelper('R', row, column - 1, entranceRow, entranceColumn, Path); // --> going to the left
			return Path;
		}

		// Checking if i am coming from Right 'R', including all the possible cases
		if (c == 'R' && dogMaze.getReference()[row][column].charAt(1) == '0') {
			Path = Path + toString(row, column)
					+ findPathHelper('R', row, column - 1, entranceRow, entranceColumn, Path); // --> going to the left
			return Path;
		}
		if (c == 'R' && dogMaze.getReference()[row][column].charAt(2) == '0') {
			Path = Path + toString(row, column)
					+ findPathHelper('T', row + 1, column, entranceRow, entranceColumn, Path); // --> going Bottom

			return Path;
		}
		if (c == 'R' && dogMaze.getReference()[row][column].charAt(0) == '0') {
			Path = Path + toString(row, column)
					+ findPathHelper('B', row - 1, column, entranceRow, entranceColumn, Path); // --> going to the Top
			return Path;
		}

		// Checking if i am coming from Left 'L', including all the possible cases

		if (c == 'L' && dogMaze.getReference()[row][column].charAt(2) == '0') {
			Path = Path + toString(row, column)
					+ findPathHelper('T', row + 1, column, entranceRow, entranceColumn, Path); // --> going Bottom
			return Path;
		}
		if (c == 'L' && dogMaze.getReference()[row][column].charAt(0) == '0') {
			Path = Path + toString(row, column)
					+ findPathHelper('B', row - 1, column, entranceRow, entranceColumn, Path); // --> going to the Top
			return Path;
		}
		if (c == 'L' && dogMaze.getReference()[row][column].charAt(3) == '0') {
			Path = Path + toString(row, column)
					+ findPathHelper('L', row, column + 1, entranceRow, entranceColumn, Path); // --> going to the Right
			return Path;
		} else {
			if (c == 'T') {

				return Path + toString(row, column)
						+ findPathHelper('B', row, column, entranceRow, entranceColumn, Path);
			} else if (c == 'B') {

				return Path + toString(row, column)
						+ findPathHelper('T', row, column, entranceRow, entranceColumn, Path);
			} else if (c == 'R') {

				return Path + toString(row, column)
						+ findPathHelper('L', row, column, entranceRow, entranceColumn, Path);
			} else if (c == 'L') {

				return Path + toString(row, column)
						+ findPathHelper('R', row, column, entranceRow, entranceColumn, Path);
			}
		}
		return "";
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

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				this.maze[i][j] = maze[i][j];
			}
		}
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

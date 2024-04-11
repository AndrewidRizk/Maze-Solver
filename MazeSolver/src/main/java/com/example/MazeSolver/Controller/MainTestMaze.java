package com.example.mazesolver.controller;

@SuppressWarnings("javadoc")
public class MainTestMaze {

	public static void main(String[] args) {
		String[][] array = {
				{ "1110", "1010", "1010", "1000", "1010", "1011" },
				{ "1110", "1000", "1001", "0101", "1100", "1001" },
				{ "1100", "0011", "0101", "0110", "0011", "0101" },
				{ "0101", "1101", "0110", "1001", "1110", "0001" },
				{ "0110", "0011", "1100", "0010", "1010", "0011" } };

		MazeSolver a = new MazeSolver();
		a.setup(array);
		Maze b = new Maze(array);

		b.getReference().toString();
		String pass = a.findPath(4, 1);
		System.out.println(pass);

	}

}

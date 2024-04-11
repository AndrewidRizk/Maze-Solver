package com.example.mazesolver.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Andro Rizk
 */
@RestController
public class MazeSolverController {

    @PostMapping("/solve")
    public String solveMaze(@RequestBody String[][] mazeArray) {
        System.out.println("reseved");
        for (int i = 0; i < mazeArray.length; i++) {
            for (int j = 0; j < mazeArray[i].length; j++) {
                System.out.print(mazeArray[i][j] + " ");
            }
            System.out.println(); // Move to the next line after printing each row
        }
        MazeSolver mazeSolver = new MazeSolver();
        mazeSolver.setup(mazeArray);
        return mazeSolver.findPath(4, 1);
    }
}

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
        MazeSolver mazeSolver = new MazeSolver();
        mazeSolver.setup(mazeArray);
        String pass = mazeSolver.findPath(0, 0);
        return pass;
    }
}

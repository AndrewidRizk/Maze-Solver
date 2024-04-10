package com.example.MazeSolver.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MazeSolverController {

    @PostMapping("/solve")
    public String solveMaze(@RequestBody String[][] mazeArray) {
        MazeSolver mazeSolver = new MazeSolver();
        mazeSolver.setup(mazeArray);
        return mazeSolver.findPath(4, 1);
    }
}

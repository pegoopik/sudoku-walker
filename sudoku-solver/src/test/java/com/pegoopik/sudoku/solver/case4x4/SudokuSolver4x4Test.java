package com.pegoopik.sudoku.solver.case4x4;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SudokuSolver4x4Test {

    private static final String SUDOKU_TASK = "0230100200000003";
    private static final String SUDOKU_SOLUTION = "4231134231242413";

    @Test
    public void solveByMaskTest() {
        List<Sudoku4x4> solutions = SudokuSolver4x4.solveByMask(new Sudoku4x4(SUDOKU_TASK));
        System.out.println(solutions);
        assertEquals(1, solutions.size());
        assertEquals(SUDOKU_SOLUTION, solutions.get(0).getCells());
    }

    @Test
    public void solveBySolutionListTest() {
        List<Sudoku4x4> solutions = SudokuSolver4x4.solveBySolutionList(new Sudoku4x4(SUDOKU_TASK));
        System.out.println(solutions);
        assertEquals(1, solutions.size());
        assertEquals(SUDOKU_SOLUTION, solutions.get(0).getCells());
    }


}

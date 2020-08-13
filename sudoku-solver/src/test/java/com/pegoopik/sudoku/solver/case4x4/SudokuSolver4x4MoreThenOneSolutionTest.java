package com.pegoopik.sudoku.solver.case4x4;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SudokuSolver4x4MoreThenOneSolutionTest {

    private static final String SUDOKU_EMPTY_TASK = "0000000000000000";
    private static final String SUDOKU_ONE_DIGIT_TASK = "1000000000000000";

    @Test
    public void solveByMaskEmptyTest() {
        List<Sudoku4x4> solutions = SudokuSolver4x4.solveByMask(new Sudoku4x4(SUDOKU_EMPTY_TASK));
        System.out.println(solutions);
        assertEquals(12, solutions.size());
    }

    @Test
    public void solveByMaskOneDigitTest() {
        List<Sudoku4x4> solutions = SudokuSolver4x4.solveByMask(new Sudoku4x4(SUDOKU_ONE_DIGIT_TASK));
        assertEquals(12, solutions.size());
    }

    @Test
    public void solveBySolutionListEmptyTest() {
        List<Sudoku4x4> solutions = SudokuSolver4x4.solveBySolutionList(new Sudoku4x4(SUDOKU_EMPTY_TASK));
        System.out.println(solutions);
        assertEquals(288, solutions.size());
    }

    @Test
    public void solveBySolutionListOneDigitTest() {
        List<Sudoku4x4> solutions = SudokuSolver4x4.solveBySolutionList(new Sudoku4x4(SUDOKU_ONE_DIGIT_TASK));
        System.out.println(solutions);
        assertEquals(288 / 4, solutions.size());
    }

}

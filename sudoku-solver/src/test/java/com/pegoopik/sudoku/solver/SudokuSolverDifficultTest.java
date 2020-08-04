package com.pegoopik.sudoku.solver;

import com.pegoopik.sudoku.solver.pojo.Sudoku;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static com.pegoopik.sudoku.solver.utils.SudokuTestHelper.getCellValuesFromResources;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuSolverDifficultTest {

    private static final String DIFFICULT_SOLVED_SUDOKU =
            "613879425\n" +
            "924635178\n" +
            "587124936\n" +
            "498356217\n" +
            "731982564\n" +
            "256741893\n" +
            "869513742\n" +
            "345297681\n" +
            "172468359";

    @Test
    public void test() throws FileNotFoundException {
        Sudoku sudoku = new Sudoku();
        sudoku.initCellsValues(getCellValuesFromResources("sudoku/difficult.txt"));
        SudokuSolver.solveByRules(sudoku);
        assertEquals(DIFFICULT_SOLVED_SUDOKU.trim(), sudoku.toStringSimple().trim());
    }

}

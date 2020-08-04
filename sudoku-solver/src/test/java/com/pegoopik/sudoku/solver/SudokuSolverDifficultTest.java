package com.pegoopik.sudoku.solver;

import com.pegoopik.sudoku.solver.pojo.Sudoku;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static com.pegoopik.sudoku.solver.utils.SudokuTestHelper.getCellValuesFromResources;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuSolverDifficultTest {

    private static final String DIFFICULT_SOLVED_SUDOKU =
                    "632957841\n" +
                    "491682573\n" +
                    "785341269\n" +
                    "248579316\n" +
                    "319264785\n" +
                    "576813924\n" +
                    "124795638\n" +
                    "967438152\n" +
                    "853126497";

    @Test
    public void test() throws FileNotFoundException {
        Sudoku sudoku = new Sudoku();
        sudoku.initCellsValues(getCellValuesFromResources("sudoku/difficult.txt"));
        SudokuSolver.solveByRules(sudoku);
        assertEquals(DIFFICULT_SOLVED_SUDOKU.trim(), sudoku.toStringSimple().trim());
    }

}

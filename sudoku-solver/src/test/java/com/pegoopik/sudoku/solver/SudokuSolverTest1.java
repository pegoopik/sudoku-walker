package com.pegoopik.sudoku.solver;

import com.pegoopik.sudoku.solver.pojo.Sudoku;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static com.pegoopik.sudoku.solver.utils.SudokuTestHelper.getCellValuesFromResources;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuSolverTest1 {

    private static final String DIFFICULT_SOLVED_SUDOKU =
                    "632957841" + System.lineSeparator() +
                    "491682573" + System.lineSeparator() +
                    "785341269" + System.lineSeparator() +
                    "248579316" + System.lineSeparator() +
                    "319264785" + System.lineSeparator() +
                    "576813924" + System.lineSeparator() +
                    "124795638" + System.lineSeparator() +
                    "967438152" + System.lineSeparator() +
                    "853126497";

    @Test
    public void test() throws FileNotFoundException {
        Sudoku sudoku = new Sudoku();
        sudoku.initCellsValues(getCellValuesFromResources("sudoku/test1.txt"));
        SudokuSolver.solveByRules(sudoku);
        assertEquals(DIFFICULT_SOLVED_SUDOKU.trim(), sudoku.toStringSimple().trim());
    }

}

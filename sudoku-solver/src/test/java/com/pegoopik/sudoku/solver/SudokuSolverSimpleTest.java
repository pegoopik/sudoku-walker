package com.pegoopik.sudoku.solver;

import com.pegoopik.sudoku.solver.pojo.Sudoku;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static com.pegoopik.sudoku.solver.utils.SudokuTestHelper.getCellValuesFromResources;

public class SudokuSolverSimpleTest {

    @Test
    public void test() throws FileNotFoundException {
        Sudoku sudoku = new Sudoku();
        sudoku.initCellsValues(getCellValuesFromResources("sudoku/easy.txt"));
        SudokuSolver.solveByRules(sudoku);
        System.out.println(sudoku.toStringSimple());
        System.out.println();
    }

}

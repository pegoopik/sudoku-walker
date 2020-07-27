package com.pegoopik.sudoku.solver.pojo;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static com.pegoopik.sudoku.solver.utils.SudokuTestHelper.getCellValuesFromResources;

public class FillSudokuTest {

    @Test
    public void test() throws FileNotFoundException {
        Sudoku sudoku = new Sudoku();
        sudoku.initCellsValues(getCellValuesFromResources("sudoku/easy.txt"));
        System.out.println();
    }
}

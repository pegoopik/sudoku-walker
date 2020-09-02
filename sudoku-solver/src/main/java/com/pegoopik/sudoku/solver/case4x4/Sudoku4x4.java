package com.pegoopik.sudoku.solver.case4x4;

import com.pegoopik.sudoku.solver.utils.SudokuStrings;
import lombok.Data;

@Data
public class Sudoku4x4 {

    private String cells;

    public Sudoku4x4(String cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        return cells;
    }

}

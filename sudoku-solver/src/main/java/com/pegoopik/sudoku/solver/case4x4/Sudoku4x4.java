package com.pegoopik.sudoku.solver.case4x4;

import lombok.Data;

@Data
public class Sudoku4x4 {

    private String cells;

    public Sudoku4x4(String cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < cells.length(); index++) {
            if (index != 0 && index % 4 == 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(cells.charAt(index));
        }
        return sb.toString();
    }

}

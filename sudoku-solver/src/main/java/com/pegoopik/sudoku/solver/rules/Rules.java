package com.pegoopik.sudoku.solver.rules;

import com.pegoopik.sudoku.solver.pojo.Cell;
import com.pegoopik.sudoku.solver.pojo.CellStatus;
import com.pegoopik.sudoku.solver.pojo.Sudoku;

public class Rules {

    public static int removeAvailableValues(Sudoku sudoku) {
        int removeAvailableCount = 0;
        for (Cell cell : sudoku.getCells().values()) {
            if (!cell.getValue().equals(0)) {
                removeAvailableCount += sudoku.removeAvailableValuesFromLinkedCells(cell);
            }
        }
        return removeAvailableCount;
    }

    /**
     * Если количество возможных значений = 1, тогда задаём это значение ячейке
     */
    public static int availableCountEqualsOne(Sudoku sudoku) {
        int updatedCellCount = 0;
        for (Cell cell : sudoku.getCells().values()) {
            if (cell.getValue().equals(0) && cell.getAvailableValues().size() == 1) {
                updatedCellCount += sudoku.updateCellValue(
                        cell.getCoordinates(), cell.getAvailableValues().iterator().next(), CellStatus.SOLVE);
            }
        }
        return updatedCellCount;
    }

}

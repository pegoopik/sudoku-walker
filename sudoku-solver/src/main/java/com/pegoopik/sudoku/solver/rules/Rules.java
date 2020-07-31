package com.pegoopik.sudoku.solver.rules;

import com.pegoopik.sudoku.solver.pojo.Cell;
import com.pegoopik.sudoku.solver.pojo.CellStatus;
import com.pegoopik.sudoku.solver.pojo.Group;
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

    /**
     * Выставляем значение ячейке, если некоторое значение может быть только в ней
     * Это нужно сделать для всех групп и для всех возможных значений
     */
    public static int setValueIfOneCandidateInGroup(Sudoku sudoku) {
        int updatedCellCount = 0;
        // Пройдёмся по всем возможным значениям
        // TODO: лучше перегруппировать, стартуя от ячеек, у которых нет решения и пробежаться по их возможных значениях - будет меньше итераций
        for (int candidateValue = 1; candidateValue <= Sudoku.SUDOKU_ROW_COUNT; candidateValue++) {
            for (Cell cell : sudoku.getCells().values()) {
                int findValueCount = 0;
                for (Cell mergeCell : cell.getLine().getCells().values()) {
                    if (mergeCell.getValue().equals(candidateValue)) {
                        findValueCount++;
                    }
                }
                if (findValueCount == 1) {
                    updatedCellCount +=
                            sudoku.updateCellValue(cell.getCoordinates(), candidateValue, CellStatus.SOLVE);
                }
            }
        }
        return updatedCellCount;
    }

}

package com.pegoopik.sudoku.solver.rules;

import com.pegoopik.sudoku.solver.pojo.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        // TODO: лучше перегруппировать, стартуя от ячеек, у которых нет решения и пробежаться по их возможным значениям - будет меньше итераций
        for (int candidateValue = 1; candidateValue <= Sudoku.SUDOKU_ROW_COUNT; candidateValue++) {
            for (Cell cell : sudoku.getCells().values()) {
                if (cell.getStatus().equals(CellStatus.FINAL) || cell.getStatus().equals(CellStatus.SOLVE)) {
                    continue;
                }

                updatedCellCount += setValueIfOneCandidateInGroupPrivate(sudoku, cell, cell.getLine(), candidateValue);
                updatedCellCount += setValueIfOneCandidateInGroupPrivate(sudoku, cell, cell.getColumn(), candidateValue);
                updatedCellCount += setValueIfOneCandidateInGroupPrivate(sudoku, cell, cell.getSquare(), candidateValue);
            }
        }
        return updatedCellCount;
    }

    private static int setValueIfOneCandidateInGroupPrivate(Sudoku sudoku, Cell cell, Group group, int candidateValue) {
        int updatedCellCount = 0;
        int findValueCount = 0;
        for (Cell mergeCell : group.getCells().values()) {
            if (mergeCell.getAvailableValues().contains(candidateValue)
                    && cell.getAvailableValues().contains(candidateValue)) {
                findValueCount++;
            }
        }
        if (findValueCount == 1) {
            updatedCellCount +=
                    sudoku.updateCellValue(cell.getCoordinates(), candidateValue, CellStatus.SOLVE);
        }
        return updatedCellCount;
    }

    /**
     * Если в некоторой группе все ячейки кроме одной заполнены - значит выставляем значение по признаку исключения
     */
    public static int enigmaRule(Sudoku sudoku) {
        int updatedCellCount = 0;
        for (int groupIndex = 0; groupIndex < Sudoku.SUDOKU_ROW_COUNT; groupIndex++) {
            updatedCellCount += enigmaProcessGroup(sudoku, sudoku.getLines().get(groupIndex));
            updatedCellCount += enigmaProcessGroup(sudoku, sudoku.getColumns().get(groupIndex));
            updatedCellCount += enigmaProcessGroup(sudoku, sudoku.getSquares().get(groupIndex));
        }
        return updatedCellCount;
    }

    private static int enigmaProcessGroup(Sudoku sudoku, Group group) {
        for (int candidateValue = 1; candidateValue <= Sudoku.SUDOKU_ROW_COUNT; candidateValue++) {
            int includeCount = 0;
            Cell lastCellWithCandidateValue = null;
            for (Cell currentCell : group.getCells().values()) {
                if (currentCell.getAvailableValues().contains(candidateValue)) {
                    // Ни к чему продолжать, если в группе больше одной ячейки, у которой в допустимых значениях есть наш кандидат
                    if (includeCount > 1) {
                        break;
                    }
                    lastCellWithCandidateValue = currentCell;
                    includeCount++;
                }
            }
            // Если нашли ровно одну ячейку, в которой может быть наш кандидат - обновляем значение ячейки
            if (lastCellWithCandidateValue != null && includeCount == 1) {
                //Сразу возвращаем результат, т.к. данное правило может сработать только для одного значения кандидата, ни к чему дальше продолжать анализ
                return sudoku.updateCellValue(lastCellWithCandidateValue.getCoordinates(), candidateValue, CellStatus.SOLVE);
            }
        }
        // Правило не сработало - возвращаем 0
        return 0;
    }


    public static int only2in2inGroup(Sudoku sudoku) {
        int changeCount = 0;
        for (Group groups : sudoku.getLines()) {
            changeCount += only2in2inGroupPrivate(groups);
        }
        for (Group groups : sudoku.getColumns()) {
            changeCount += only2in2inGroupPrivate(groups);
        }
        for (Group groups : sudoku.getSquares()) {
            changeCount += only2in2inGroupPrivate(groups);
        }
        return changeCount;
    }

    private static int only2in2inGroupPrivate(Group group) {
        int changeCount = 0;
        for (int first = 1; first < Sudoku.SUDOKU_ROW_COUNT; first ++) {
            for (int second = first + 1; second <= Sudoku.SUDOKU_ROW_COUNT; second ++) {
                int coincidenceCount = 0;
                int firstCount = 0;
                int secondCount = 0;
                List<Cell> cellsToUpdate = new ArrayList<>();
                for (Cell cell : group.getCells().values()) {
                    if (cell.getAvailableValues().contains(first)
                            || cell.getAvailableValues().contains(second)) {
                        firstCount += cell.getAvailableValues().contains(first) ? 1 : 0;
                        secondCount += cell.getAvailableValues().contains(second) ? 1 : 0;
                        if (cell.getAvailableValues().contains(first)
                                && cell.getAvailableValues().contains(second)) {
                            coincidenceCount++;
                            cellsToUpdate.add(cell);
                        }
                    }
                }
                if (coincidenceCount == 2 && firstCount == 2 && secondCount == 2) {
                    for (Cell cellToUpdate : cellsToUpdate) {
                        int beforeSize = cellToUpdate.getAvailableValues().size();
                        String logString = "Remove aValues " + cellToUpdate.getCoordinates() + " " + cellToUpdate.getAvailableValues();
                        cellToUpdate.getAvailableValues().clear();
                        cellToUpdate.getAvailableValues().add(first);
                        cellToUpdate.getAvailableValues().add(second);
                        changeCount += beforeSize - 2;
                        if (beforeSize - 2 > 0) {
                            System.out.println(logString + "->" + cellToUpdate.getAvailableValues() + " " + group);
                        }
                    }
                }
            }
        }
        return changeCount;
    }
}

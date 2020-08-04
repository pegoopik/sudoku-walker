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
        // TODO: лучше перегруппировать, стартуя от ячеек, у которых нет решения и пробежаться по их возможным значениям - будет меньше итераций
        for (int candidateValue = 1; candidateValue <= Sudoku.SUDOKU_ROW_COUNT; candidateValue++) {
            for (Cell cell : sudoku.getCells().values()) {
                if (cell.getStatus().equals(CellStatus.FINAL) || cell.getStatus().equals(CellStatus.SOLVE)) {
                    continue;
                }

                updatedCellCount += setValueIfOneCandidateInGroupPrivate(sudoku, cell, cell.getLine(), candidateValue);
                updatedCellCount += setValueIfOneCandidateInGroupPrivate(sudoku, cell, cell.getColumn(), candidateValue);
                updatedCellCount += setValueIfOneCandidateInGroupPrivate(sudoku, cell, cell.getSquare(), candidateValue);
//                int findValueCount = 0;
//                for (Cell mergeCell : cell.getLine().getCells().values()) {
//                    if (mergeCell.getStatus().equals(CellStatus.FINAL) || mergeCell.getStatus().equals(CellStatus.SOLVE)) {
//                        continue;
//                    }
//                    if (mergeCell.getAvailableValues().contains(candidateValue)
//                            && cell.getAvailableValues().contains(candidateValue)) {
//                        findValueCount++;
//                    }
//                }
//                if (findValueCount == 1) {
//                    updatedCellCount +=
//                            sudoku.updateCellValue(cell.getCoordinates(), candidateValue, CellStatus.SOLVE);
//                }
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
//        for (Cell cell : sudoku.getCells().values()) {
//            //TODO: optimize
//            Set<Integer> availableValues = new TreeSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
//
//            if (!cell.getValue().equals(0)) {
//                continue;
//            }
//            boolean imAmReadyToSolve = false;
//            int solvedCount = 0;
//            for (Cell mergeCell : cell.getLine().getCells().values()) {
//                cell.getAvailableValues().remove(mergeCell.getValue())
//                if (!mergeCell.getValue().equals(0)) {
//                    solvedCount++;
//                }
//            }
//        }
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

}

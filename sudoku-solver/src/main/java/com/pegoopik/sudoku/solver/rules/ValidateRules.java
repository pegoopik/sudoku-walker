package com.pegoopik.sudoku.solver.rules;

import com.pegoopik.sudoku.solver.exception.SudokuException;
import com.pegoopik.sudoku.solver.pojo.Cell;
import com.pegoopik.sudoku.solver.pojo.Group;
import com.pegoopik.sudoku.solver.pojo.Sudoku;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

//TODO: добавить информации в сообщениях об ошибках, ну и тесты не помешают
public class ValidateRules {

    private static final Set<Integer> CELL_VALUES = new HashSet<>();

    public static int duplicatedValuesInGroup(Sudoku sudoku) {
        for (int groupIndex = 1; groupIndex < Sudoku.SUDOKU_ROW_COUNT; groupIndex++) {
            checkDuplicatedValuesInGroup(sudoku.getLines().get(groupIndex));
            checkDuplicatedValuesInGroup(sudoku.getColumns().get(groupIndex));
            checkDuplicatedValuesInGroup(sudoku.getSquares().get(groupIndex));
        }
        return 0;
    }

    private static void checkDuplicatedValuesInGroup(Group group) {
        CELL_VALUES.clear();
        try {
            for (Cell cell : group.getCells().values()) {
                if (cell.getValue().equals(0)) {
                    continue;
                }
                if (CELL_VALUES.contains(cell.getValue())) {
                    //TODO: добавить информации в сообщениях об ошибках
                    throw new SudokuException();
                } else {
                    CELL_VALUES.add(cell.getValue());
                }
            }
        } finally {
            CELL_VALUES.clear();
        }
    }

    public static int noAvailableValuesOnEmptyCell(Sudoku sudoku) {
        for (Cell cell : sudoku.getCells().values()) {
            if (cell.getValue().equals(0) && CollectionUtils.isEmpty(cell.getAvailableValues())) {
                //TODO: добавить информации в сообщениях об ошибках
                throw new SudokuException();
            }
        }
        return 0;
    }

}

package com.pegoopik.sudoku.solver.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

//TODO: вероятно класс со временем разрастётся, надо будет перенести часть кода в утилитарный класс, или ещё куда
@Getter
@Setter
public class Sudoku {

    private static int SUDOKU_ROW_COUNT = 9;

    // Список всех ячеек, он в некотором смысле излишен, может быть удобен в некоторых правилах
    private Map<PointXY, Cell> cells = new HashMap<>();
    // Списки из 9 элементов групп по 9 ячеек. строки / столбцы / квадраты
    private List<Group> lines = new ArrayList<>();
    private List<Group> columns = new ArrayList<>();
    private List<Group> squares = new ArrayList<>();

    public Sudoku() {
        // Создадим ячейки (координаты от 1 до 9, а не от 0 до 8 намеренно)
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                PointXY pointXY = PointXY.of(i, j);
                Cell cell = new Cell();
                cell.setCoordinates(pointXY);
                cells.put(pointXY, cell);
            }
        }
        // Заполним строки(группы) ячейками
        for (int groupIndex = 1; groupIndex < 10; groupIndex++) {
            lines.add(createGroupAndUpdateCells(GroupType.LINE, groupIndex, groupIndex, 1, 9));
        }
        // То же самое для столбцов
        for (int groupIndex = 1; groupIndex < 10; groupIndex++) {
            columns.add(createGroupAndUpdateCells(GroupType.COLUMN, 1, 9, groupIndex, groupIndex));
        }
        // И соответственно для квадратов. Тут чуть муторней
        for (int groupIndexX = 1; groupIndexX <= 7; groupIndexX += 3) {
            for (int groupIndexY = 1; groupIndexY <= 7; groupIndexY += 3) {
                squares.add(createGroupAndUpdateCells(GroupType.SQUARE,
                        groupIndexX, groupIndexX + 2, groupIndexY, groupIndexY + 2));
            }
        }
    }

    /**
     * Задаём начальные значения ячеек, предполагается, что порядок строки/столбцы; слева/направо сверху/вниз
     * @param values упорядоченный список значений ячеек. Любое значение, кроме диапазона 1-9 считаем незаданным
     * @return возвращаем количество удалённых латентных значений связанных ячеек, пригодится для некоторых правил алгоритма
     */
    public int initCellsValues(List<Integer> values) {
        int removeAvailableCount = 0;
        for (int i = 1; i <= SUDOKU_ROW_COUNT; i++) {
            for (int j = 1; j <= SUDOKU_ROW_COUNT; j++) {
                int valuesIndex = (i-1) + SUDOKU_ROW_COUNT * (j-1);
                Integer currentValue = values.get(valuesIndex);
                if (currentValue >= 1 && currentValue <= SUDOKU_ROW_COUNT) {
                    removeAvailableCount +=
                            updateCellValue(PointXY.of(i, j), currentValue, CellStatus.FINAL);
                }
            }
        }
        return removeAvailableCount;
    }

    public int updateCellValue(PointXY pointXY, Integer value, CellStatus status) {
        Cell cell = cells.get(pointXY);
        cell.setValue(value);
        cell.setStatus(status);
        cell.getAvailableValues().clear();
        return removeAvailablesValuesFromLinkedCells(cell, value);
    }

    private int removeAvailablesValuesFromLinkedCells(Cell cell, Integer value) {
        int removeAvailableCount = 0;
        for (Cell linkedCell : cell.getSquare().getCells().values()) {
            if (linkedCell.getAvailableValues().contains(value)) {
                removeAvailableCount++;
                linkedCell.getAvailableValues().remove(value);
            }
        }
        return removeAvailableCount;
    }

    private Group createGroupAndUpdateCells(GroupType groupType,
                                            Integer fromX, Integer toX,
                                            Integer fromY, Integer toY) {
        Group group = new Group();
        group.setType(groupType);
        for (int i = fromX; i <= toX; i++) {
            for (int j = fromY; j <= toY; j++) {
                PointXY pointXY = PointXY.of(i, j);
                Cell cell = cells.get(pointXY);
                group.getCells().put(pointXY, cell);
                //не забываем проставить значения групп в ячейки
                switch (groupType) {
                    case LINE: cell.setLine(group); break;
                    case COLUMN: cell.setColumn(group); break;
                    case SQUARE: cell.setSquare(group); break;
                }
            }
        }
        return group;
    }
}

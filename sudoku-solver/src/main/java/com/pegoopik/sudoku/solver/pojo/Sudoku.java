package com.pegoopik.sudoku.solver.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

//TODO: вероятно класс со временем разрастётся, надо будет перенести часть кода в утилитарный класс, или ещё куда
@Getter
@Setter
public class Sudoku {

    public static int SUDOKU_SQUARE_SIZE = 3;
    public static int SUDOKU_ROW_COUNT = SUDOKU_SQUARE_SIZE * SUDOKU_SQUARE_SIZE;

    // Список всех ячеек, он в некотором смысле излишен, может быть удобен в некоторых правилах
    private Map<PointXY, Cell> cells = new TreeMap<>();
    // Списки из 9 элементов групп по 9 ячеек. строки / столбцы / квадраты
    private List<Group> lines = new ArrayList<>();
    private List<Group> columns = new ArrayList<>();
    private List<Group> squares = new ArrayList<>();

    public Sudoku() {
        // Создадим ячейки (координаты от 1 до 9, а не от 0 до 8 намеренно)
        for (int i = 1; i <= SUDOKU_ROW_COUNT; i++) {
            for (int j = 1; j <= SUDOKU_ROW_COUNT; j++) {
                PointXY pointXY = PointXY.of(i, j);
                Cell cell = new Cell();
                cell.setCoordinates(pointXY);
                cells.put(pointXY, cell);
            }
        }
        // Заполним строки(группы) ячейками
        for (int groupIndex = 1; groupIndex <= SUDOKU_ROW_COUNT; groupIndex++) {
            lines.add(createGroupAndUpdateCells(GroupType.LINE, 1, SUDOKU_ROW_COUNT, groupIndex, groupIndex));
        }
        // То же самое для столбцов
        for (int groupIndex = 1; groupIndex <= SUDOKU_ROW_COUNT; groupIndex++) {
            columns.add(createGroupAndUpdateCells(GroupType.COLUMN, groupIndex, groupIndex, 1, SUDOKU_ROW_COUNT));
        }
        // И соответственно для квадратов. Тут чуть муторней
        for (int groupIndexY = 1; groupIndexY < SUDOKU_ROW_COUNT; groupIndexY += SUDOKU_SQUARE_SIZE)
            for (int groupIndexX = 1; groupIndexX < SUDOKU_ROW_COUNT; groupIndexX += SUDOKU_SQUARE_SIZE) {
             {
                squares.add(createGroupAndUpdateCells(GroupType.SQUARE,
                        groupIndexX, groupIndexX + SUDOKU_SQUARE_SIZE - 1,
                        groupIndexY, groupIndexY + SUDOKU_SQUARE_SIZE - 1));
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
                PointXY coordinates = PointXY.of(i, j);
                //System.out.println("" + i + "-" + j + "=" + valuesIndex + "; " + currentValue + "; " + coordinates);
                if (currentValue >= 1 && currentValue <= SUDOKU_ROW_COUNT) {
                    removeAvailableCount +=
                            updateCellValue(coordinates, currentValue, CellStatus.FINAL);
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
        return removeAvailableValuesFromLinkedCells(cell);
    }

    public int removeAvailableValuesFromLinkedCells(Cell cell) {
        int removeAvailableCount = 0;
        removeAvailableCount += removeAvailableValuesFromLinkedCells(cell, cell.getLine());
        removeAvailableCount += removeAvailableValuesFromLinkedCells(cell, cell.getColumn());
        removeAvailableCount += removeAvailableValuesFromLinkedCells(cell, cell.getSquare());
        return removeAvailableCount;
    }

    public String toStringSimple() {
        StringBuilder sb = new StringBuilder();
        for (Group line : getLines()) {
            for (Cell cell : line.getCells().values()) {
                sb.append(cell.getValue())
                        // для отладки можно раскомментировать, чтобы видеть координаты
                        /*.append("(").append(cell.getCoordinates().getX())
                        .append(",").append(cell.getCoordinates().getY())
                        .append(")")*/;
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public String toStringDetails() {
        char[][] cars = new char[SUDOKU_ROW_COUNT][SUDOKU_ROW_COUNT];
        for (int i = 0; i < SUDOKU_ROW_COUNT; i++) {
            for (int j = 0; j < SUDOKU_ROW_COUNT; j++) {

            }
        }
        return "";
    }

    private int removeAvailableValuesFromLinkedCells(Cell cell, Group group) {
        int removeAvailableCount = 0;
        for (Cell linkedCell : group.getCells().values()) {
            if (linkedCell.getAvailableValues().contains(cell.getValue())) {
                removeAvailableCount++;
                linkedCell.getAvailableValues().remove(cell.getValue());
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

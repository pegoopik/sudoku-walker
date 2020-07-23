package com.pegoopik.sudoku.solver.pojo;

import lombok.Data;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

@Data
public class Cell {
    private PointXY coordinates;
    private Integer value = 0;
    private Set<Integer> availableValues = new TreeSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    private Group line;
    private Group column;
    private Group square;
    private CellStatus status = CellStatus.NONE;
}

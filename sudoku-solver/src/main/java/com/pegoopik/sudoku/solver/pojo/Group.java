package com.pegoopik.sudoku.solver.pojo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Group {
    GroupType type;
    Map<PointXY, Cell> cells = new HashMap<>();
}

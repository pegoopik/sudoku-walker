package com.pegoopik.sudoku.solver.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Group {
    GroupType type;
    Map<PointXY, Cell> cells = new HashMap<>();
}

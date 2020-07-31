package com.pegoopik.sudoku.solver.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.TreeMap;

@Getter
@Setter
public class Group {
    GroupType type;
    Map<PointXY, Cell> cells = new TreeMap<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<PointXY, Cell> entry: cells.entrySet()) {
            sb.append(entry.getKey().getX()).append(":")
                    .append(entry.getKey().getY()).append(",");
        }
        sb.append("(");
        for (Map.Entry<PointXY, Cell> entry: cells.entrySet()) {
            sb.append(entry.getValue().getValue());
        }
        sb.append(")");
        return sb.toString();
    }
}

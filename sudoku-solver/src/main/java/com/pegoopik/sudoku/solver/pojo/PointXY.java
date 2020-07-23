package com.pegoopik.sudoku.solver.pojo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class PointXY {
    @NonNull
    private final Integer x;
    @NonNull
    private final Integer y;
}

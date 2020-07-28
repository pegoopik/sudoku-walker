package com.pegoopik.sudoku.solver.rules;

import com.pegoopik.sudoku.solver.pojo.Sudoku;

@FunctionalInterface
public interface RuleFunction {

    int apply(Sudoku sudoku);

}

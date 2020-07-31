package com.pegoopik.sudoku.solver;

import com.pegoopik.sudoku.solver.pojo.Sudoku;
import com.pegoopik.sudoku.solver.rules.RuleCases;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SudokuSolver {

    public static void solveByRules(Sudoku sudoku) {
        System.out.println(sudoku.toStringSimple());
        int priority = 1;
        while (true) {
            int changedCount = 0;
            for(RuleCases ruleCase : RuleCases.values()) {
                if (ruleCase.getPriority() > priority) {
                    continue;
                }
                changedCount += ruleCase.getFunction().apply(sudoku);
                System.out.println(sudoku.toStringSimple());
            }
            if (changedCount == 0) {
                priority++;
            }
            if (priority > RuleCases.MAX_PRIORITY) {
                break;
            }
        }
    }

}

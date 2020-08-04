package com.pegoopik.sudoku.solver;

import com.pegoopik.sudoku.solver.pojo.Sudoku;
import com.pegoopik.sudoku.solver.rules.RuleCases;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SudokuSolver {

    public static void solveByRules(Sudoku sudoku) {
        System.out.println(sudoku.toStringSimple());
        int priority = 0;
        while (true) {
            int changedCount = 0;
            for(RuleCases ruleCase : RuleCases.values()) {
                if (ruleCase.getPriority() > priority) {
                    continue;
                }
                int currentChangeCount = ruleCase.getFunction().apply(sudoku);
                changedCount += currentChangeCount;
                System.out.println("Run " + ruleCase + " currentChangeCount = " + currentChangeCount);
                if (currentChangeCount > 0) {
                    System.out.println(sudoku.toStringDetails());
                }
            }
            if (changedCount == 0) {
                priority++;
                System.out.println("Increment Rules priority value to " + priority);
                for(RuleCases ruleCase : RuleCases.values()) {
                    if (ruleCase.getPriority() == priority) {
                        System.out.println("Add new Rule " + ruleCase);
                        int currentChangeCount = ruleCase.getFunction().apply(sudoku);
                        changedCount += currentChangeCount;
                        System.out.println("Run " + ruleCase + " currentChangeCount = " + currentChangeCount);
                        if (currentChangeCount > 0) {
                            System.out.println(sudoku.toStringDetails());
                        }
                    }
                }
            }
            if (priority > RuleCases.MAX_PRIORITY) {
                break;
            }
        }
        System.out.println("Solve:");
        System.out.println(sudoku.toStringSimple());
    }

}

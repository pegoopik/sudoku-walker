package com.pegoopik.sudoku.web.external;

import org.junit.jupiter.api.Test;

public class ExternalSolverTest {

    private static final String EASY_SOLVED_SUDOKU =
                            "002000041\n" +
                            "000082070\n" +
                            "000040009\n" +
                            "200079300\n" +
                            "010000080\n" +
                            "006810004\n" +
                            "100090000\n" +
                            "060430000\n" +
                            "850000400";

    @Test
    public void solveBySudokuSolverIn () {
        String solution = ExternalSolver.evaluate(EASY_SOLVED_SUDOKU);
        System.out.println(solution);
    }

}

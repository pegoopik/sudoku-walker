package com.pegoopik.sudoku.solver.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuStringsTest {

    @Test
    public void lineToSquareTest() {
        String input = "123456789  9876\n54321 \n 879685743";
        String expectedOutput = "123456789\n987654321\n879685743";
        String actualOutPut = SudokuStrings.lineToSquare(input);
        assertEquals(expectedOutput, actualOutPut);
    }

    @Test
    public void squareToLineTest() {
        String input = "1234 \n56789\n98765  4321\n879685743";
        String expectedOutput = "123456789987654321879685743";
        String actualOutPut = SudokuStrings.squareToLine(input);
        assertEquals(expectedOutput, actualOutPut);
    }

    @Test
    public void strToIntListTest() {
        String input = "\"01QWE23 45 \n 67 89AAA\"\n";
        List<Integer> expectedOutput = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> actualOutPut = SudokuStrings.strToIntList(input);
        assertEquals(expectedOutput.size(), actualOutPut.size());
        assertTrue(expectedOutput.containsAll(actualOutPut));
        assertTrue(actualOutPut.containsAll(expectedOutput));
    }

}

package com.pegoopik.sudoku.solver.utils;

import java.util.ArrayList;
import java.util.List;

import static com.pegoopik.sudoku.solver.pojo.Sudoku.SUDOKU_ROW_COUNT;


/**
 * Небольшой утилитарный класс для удобства форматирования строк,
 * представляющих строковое представление текущего состояния задачи Судоку
 */
//TODO: Посмотреть на все велосипеды по форматированию строк, и переиспользовать данный класс
//TODO: #optimization можно убрать переиспользование strToIntList в пользу StringBuilder итп
public final class SudokuStrings {


    private static int ASCII_INT_CHAR_LAG = 48;

    private SudokuStrings() {}

    public static String lineToSquare(String sudokuLine) {
        return lineToSquare(sudokuLine, SUDOKU_ROW_COUNT);
    }

    public static String lineToSquare(String sudokuLine, int rowSize) {
        List<Integer> values = strToIntList(sudokuLine);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i != 0 && i % rowSize == 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(values.get(i));
        }
        return sb.toString();
    }

    public static String squareToLine(String sudokuSquare) {
        StringBuilder sb = new StringBuilder();
        for (char ch : sudokuSquare.toCharArray()) {
            if (ch >= ASCII_INT_CHAR_LAG && ch <= ASCII_INT_CHAR_LAG + 9) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public static List<Integer> strToIntList(String sudokuString) {
        List<Integer> result = new ArrayList<>();
        for (char ch : sudokuString.toCharArray()) {
            if (ch >= ASCII_INT_CHAR_LAG && ch <= ASCII_INT_CHAR_LAG + 9) {
                result.add(((int) ch) - ASCII_INT_CHAR_LAG);
            }
        }
        return result;
    }

//    public static void main(String[] args) {
//        List<Integer> integers = strToIntList("01QWE23 45  67 89AAA");
//        System.out.println(integers);
//        String lineToSquare = lineToSquare("123456789123456789123456789123456789");
//        System.out.println(lineToSquare);
//    }

}

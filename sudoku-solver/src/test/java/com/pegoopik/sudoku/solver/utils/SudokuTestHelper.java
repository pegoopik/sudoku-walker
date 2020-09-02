package com.pegoopik.sudoku.solver.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.pegoopik.sudoku.solver.pojo.Sudoku.SUDOKU_ROW_COUNT;

public class SudokuTestHelper {

    public static List<Integer> getCellValuesFromResources(String path) throws FileNotFoundException {
        List<Integer> result = new ArrayList<>();
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (stream == null) {
            throw new FileNotFoundException(path);
        }
        List<String> collect = new BufferedReader(new InputStreamReader(stream))
                .lines().collect(Collectors.toList());
        for (String line : collect) {
            for (int i = 1; i <= SUDOKU_ROW_COUNT; i++) {
                result.add(Integer.parseInt(line.substring(i-1, i)));
            }
        }
        return result;
    }

}

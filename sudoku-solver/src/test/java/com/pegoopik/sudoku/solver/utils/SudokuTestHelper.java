package com.pegoopik.sudoku.solver.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SudokuTestHelper {

    private static final int CHAR_COUNT_IN_LINE = 9;

    public static List<Integer> getCellValuesFromResources(String path) throws FileNotFoundException {
        List<Integer> result = new ArrayList<>();
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (stream == null) {
            throw new FileNotFoundException(path);
        }
        List<String> collect = new BufferedReader(new InputStreamReader(stream))
                .lines().collect(Collectors.toList());
        for (String line : collect) {
            for (int i = 1; i <= CHAR_COUNT_IN_LINE; i++) {
                result.add(Integer.parseInt(line.substring(i-1, i)));
            }
        }
        return result;
    }

}

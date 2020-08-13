package com.pegoopik.sudoku.solver.case4x4;

import org.apache.avro.mapred.Pair;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

public class SudokuSolver4x4 {

    private static final List<String> SUDOKU_4X4_MASKS =  Arrays.asList(
            "abcdcdabbadcdcba",
            "abcdcdabbcdadabc",
            "abcdcdabdcbabadc",
            "abcdcdabdabcbcda",
            "abcddcbabadccdab",
            "abcddcbabdaccadb",
            "abcddcbacadbbdac",
            "abcddcbacdabbadc",
            "abcddcabbadccdba",
            "abcddcabcdbabadc",
            "abcdcdbabadcdcab",
            "abcdcdbadcabbadc");

    private static final List<String> PERMUTATIONS_BY_4_DIGITS = Arrays.asList(
            "1234", "1243", "1324", "1342", "1423", "1432", "2134", "2143",
            "2314", "2341", "2413", "2431", "3124", "3142", "3214", "3241",
            "3412", "3421", "4123", "4132", "4213", "4231", "4312", "4321");
    
    private static final List<String> SUDOKU_4X4_SOLUTIONS = new ArrayList<>();

    static {
        for (String sudokuMask : SUDOKU_4X4_MASKS) {
            for (String permutation : PERMUTATIONS_BY_4_DIGITS) {
                SUDOKU_4X4_SOLUTIONS.add(StringUtils.replaceChars(sudokuMask, "abcd", permutation));
            }
        }
    }

    public static List<Sudoku4x4> solveByMask(Sudoku4x4 sudoku) {
        List<Sudoku4x4> solutionList = new ArrayList<>();
        Set<String> availableValues = new TreeSet<>();
        Set<String> availableMasks = new TreeSet<>();
        for (String mask : SUDOKU_4X4_MASKS) {
            availableValues.addAll(Arrays.asList("1", "2", "3", "4"));
            availableMasks.addAll(Arrays.asList("a", "b", "c", "d"));
//            mapping.clear();
            boolean solutionFound = true;
            for (int index = 0; index < mask.length(); index++) {
                String currentSudokuValue = sudoku.getCells().substring(index, index + 1);
                // Если в здаче судоку значение ячейки неизвестно - просто ничего не делаем
                if (currentSudokuValue.equals("0")) {
                    continue;
                }
                String currentMaskValue = mask.substring(index, index + 1);
                // Если маска открыта и цифра не совпадает - решений нет,
                if ("1234".contains(currentMaskValue)) {
                    if (!currentMaskValue.equals(currentSudokuValue)) {
                        solutionFound = false;
                        break;
                    }
                } else if (!availableValues.contains(currentSudokuValue)) {
                    solutionFound = false;
                    break;
                }
                // Если открытая цифра из судоку есть в списке доступных значений
                // Заменяем маскировочное значение в предполгаемом решении на конкретную цифру
                if (availableValues.contains(currentSudokuValue)) {
                    mask = mask.replace(currentMaskValue, currentSudokuValue);
                    availableMasks.remove(currentMaskValue);
                    availableValues.remove(currentSudokuValue);
                    // Если у нас осталась только одна нераскрытая цифра, методом исключения знаем последнюю цифру
                    if (availableMasks.size() == 1) {
                        // Заменяем маскировочное значение в предполгаемом решении на конкретную цифру
                        mask = mask.replace(availableMasks.iterator().next(), availableValues.iterator().next());
                        // Чистим списки доступных масок/значений
                        availableMasks.clear();
                        availableValues.clear();
                    }
                }
            }
            if (solutionFound) {
                solutionList.add(new Sudoku4x4(mask));
            }
        }
        return solutionList;
    }

    public static List<Sudoku4x4> solveBySolutionList(Sudoku4x4 sudoku) {
        List<Sudoku4x4> solutionList = new ArrayList<>();
        for (String solution : SUDOKU_4X4_SOLUTIONS) {
            if (Pattern.compile(sudoku.getCells().replaceAll("0", ".")).matcher(solution).matches()) {
                solutionList.add(new Sudoku4x4(solution));
            }
        }
        return solutionList;
    }

}

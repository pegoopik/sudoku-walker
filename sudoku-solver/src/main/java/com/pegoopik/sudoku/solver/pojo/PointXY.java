package com.pegoopik.sudoku.solver.pojo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import java.util.TreeMap;

import static com.pegoopik.sudoku.solver.pojo.Sudoku.SUDOKU_ROW_COUNT;

@ToString
@Getter
@EqualsAndHashCode
public class PointXY implements Comparable {

    private static final int MIN_X = 1;
    private static final int MIN_Y = 1;
    private static final int MAX_X = SUDOKU_ROW_COUNT;
    private static final int MAX_Y = SUDOKU_ROW_COUNT;
    private static final Map<Integer, PointXY> CACHE = new TreeMap<>();
    static {
        // Наполняем кэш значениями, чтобы память не текла. Нечего сборщик мусора мучить
        for (int x = MIN_X; x <= MAX_X; x++) {
            for (int y = MIN_Y; y <= MAX_Y; y++) {
                CACHE.put(generateKey(x, y), new PointXY(x, y));
            }
        }
    }

    private final int x;
    private final int y;

    /**
     * Конструктор приватный, у нас есть статический кэш
     */
    private PointXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Статический метод получения объекта из кэша по значениям координат
     */
    public static PointXY of(int x, int y) {
        // По идее это лишнее, логичней в тесты перенести, но перестрахуемся
        if (x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y) {
            throw new IllegalArgumentException(String.format("%d:%d", x, y));
        }
        return CACHE.get(generateKey(x, y));
    }

    private static Integer generateKey(int x, int y) {
        return y * MAX_X + x;
    }

    /**
     * Нужен для правильной последовательности в TreeMap, может пригодиться в некоторых правилах
     */
    @Override
    public int compareTo(Object o) {
        PointXY compareObj = (PointXY) o;
        return Integer.compare(PointXY.generateKey(x, y), PointXY.generateKey(compareObj.x, compareObj.y));
    }
}

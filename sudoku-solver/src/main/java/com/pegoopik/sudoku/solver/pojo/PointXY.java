package com.pegoopik.sudoku.solver.pojo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;
import java.util.TreeMap;

@Getter
@EqualsAndHashCode
public class PointXY {

    private static final int MIN_X = 1;
    private static final int MIN_Y = 1;
    private static final int MAX_X = 16;
    private static final int MAX_Y = 16;
    private static final Map<Integer, PointXY> CACHE = new TreeMap<>();
    static {
        for (int x = MIN_X; x <= MAX_X; x++) {
            for (int y = MIN_Y; y <= MAX_Y; y++) {
                CACHE.put(generateKey(x, y), new PointXY(x, y));
            }
        }
    }

    private final int x;
    private final int y;

    private PointXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static PointXY of(int x, int y) {
        if (x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y) {
            throw new IllegalArgumentException(String.format("%d:%d", x, y));
        }
        return CACHE.get(generateKey(x, y));
    }

    private static Integer generateKey(int x, int y) {
        return y * MAX_X + x;
    }

}

package com.pegoopik.sudoku.solver.pojo;

import lombok.Getter;

/**
 * Статус обработки ячейки
 */
@Getter
public enum CellStatus {
    FINAL("F"), // значение ячейки задано в условии судоку
    PROGRESS("P"), // значение ячейки вычисляется алгоритмом (по умолчанию)
    SOLVE("S"), // алгоритм нашёл значение
    ASSUMPTION("A"), // алгоритм предполагает значение ячейки
    ASSUMPTION_SOLVE("X"), // алгоритм вычислил значение с учётом предположений (возможно не пригодится)
    ;

    /**
     * Код статуса для отладки, используется в логах
     */
    private final String code;

    CellStatus(String code) {
        this.code = code;
    }
}

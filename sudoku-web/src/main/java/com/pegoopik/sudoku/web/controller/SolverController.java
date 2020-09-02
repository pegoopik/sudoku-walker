package com.pegoopik.sudoku.web.controller;

import com.pegoopik.sudoku.solver.SudokuSolver;
import com.pegoopik.sudoku.solver.pojo.Sudoku;
import com.pegoopik.sudoku.solver.utils.SudokuStrings;
import com.pegoopik.sudoku.web.external.ExternalSolver;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * По умолчанию решаем 9х9
 */
@Api(tags = {"Standard Solver (9x9)"})
@Slf4j
@RequestMapping("solve9x9/")
@RestController
public class SolverController {

    /**
     * Solve Sudoku 9x9 by Rules
     *
     * @return Solve information
     */
    @ApiOperation("Solve sudoku by rules")
    @PostMapping("v1/by-rules")
    public ResponseEntity<String> solveByRules(@RequestBody String task) {
        log.info("call solve9x9/v1/by-rules\n" + task);
        Sudoku sudokuTask = new Sudoku();
        sudokuTask.initCellsValues(SudokuStrings.strToIntList(task));
        SudokuSolver.solveByRules(sudokuTask);
        log.info("solved result:\n" + sudokuTask.toStringDetails());
        return ResponseEntity.ok(sudokuTask.toStringSimple());
    }

}

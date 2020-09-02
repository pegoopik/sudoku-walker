package com.pegoopik.sudoku.web.controller;

import com.pegoopik.sudoku.solver.SudokuSolver;
import com.pegoopik.sudoku.solver.case4x4.Sudoku4x4;
import com.pegoopik.sudoku.solver.case4x4.SudokuSolver4x4;
import com.pegoopik.sudoku.solver.pojo.Sudoku;
import com.pegoopik.sudoku.solver.utils.SudokuStrings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * По умолчанию решаем 9х9
 */
@Api(tags = {"Solver 4x4"})
@Slf4j
@RequestMapping("solve4x4/")
@RestController
public class Solver4x4Controller {

    /**
     * Solve Sudoku 9x9 by Rules
     *
     * @return Solve information
     */
    @ApiOperation("Solve sudoku by mask")
    @PostMapping("v1/by-mask")
    public ResponseEntity<String> solveByMask(@RequestBody String task) {
        log.info("call solve4x4/v1/by-mask\n" + task);
        List<Sudoku4x4> solutions = SudokuSolver4x4.solveByMask(new Sudoku4x4(task));
        log.info("solutions count = " + solutions.size());
        StringBuilder sb = new StringBuilder();
        for(Sudoku4x4 answer : solutions) {
            sb.append(answer);
            sb.append(System.lineSeparator());
        }
        return ResponseEntity.ok(sb.toString());
    }

    /**
     * Solve Sudoku 9x9 by Solution list
     *
     * @return Solve information
     */
    @ApiOperation("Solve sudoku by solution list")
    @PostMapping("v1/by-solutions")
    public ResponseEntity<String> solveByBruteForce(@RequestBody String task) {
        log.info("call solve4x4/v1/by-mask\n" + task);
        List<Sudoku4x4> solutions = SudokuSolver4x4.solveBySolutionList(new Sudoku4x4(task));
        log.info("solutions count = " + solutions.size());
        StringBuilder sb = new StringBuilder();
        for(Sudoku4x4 answer : solutions) {
            sb.append(answer);
            sb.append(System.lineSeparator());
        }
        return ResponseEntity.ok(sb.toString());
    }

}

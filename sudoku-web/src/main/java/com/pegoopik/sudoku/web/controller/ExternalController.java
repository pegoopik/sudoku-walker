package com.pegoopik.sudoku.web.controller;

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
 * Здесь находятся заимствованные решения с других веб сервисов
 * Тут мы их можем переиспользовать, например, для проверки своих алгоритмов
 *
 * Ссылки на ресурсы:
 *
 * https://sudoku-solver.in.ua/ - находит только одно решение, если оно есть
 * TODO: https://www.sudoku.name/sudoku-solver/ru - рекурсивный, находит первое попавшееся решение
 *
 */
@Api(tags = {"External Solver"})
@Slf4j
@RequestMapping("external/")
@RestController
public class ExternalController {

    /**
     * Solve by using https://sudoku-solver.in.ua/
     *
     * @return Solve information
     */
    @ApiOperation("Solve by using https://sudoku-solver.in.ua/")
    @PostMapping("v1/by-sudoku-solver-in")
    public ResponseEntity<String> bySudokuSolverIn(@RequestBody String task) {
        log.info("call v1/by-sudoku-solver-in\n" + task);
        String responseString = ExternalSolver.evaluate(task);
        return ResponseEntity.ok(responseString);
    }


}

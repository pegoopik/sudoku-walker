package com.pegoopik.sudoku.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"System"})
@Slf4j
@RequestMapping("system/")
@RestController
public class SystemController {

    /**
     * ping
     *
     * @return pong
     */
    @ApiOperation("Ping Test")
    @GetMapping("ping")
    public ResponseEntity<String> ping() {
        log.info("call ping pong");
        return ResponseEntity.ok("pong");
    }

}

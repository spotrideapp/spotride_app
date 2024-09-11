package com.spotride.spotride.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test Controller.
 */
@RestController
public final class TestController {
    private static final String TEST_RESPONSE = "test controller response";

    @GetMapping("/test")
    public String test() {
        return TEST_RESPONSE;
    }
}
